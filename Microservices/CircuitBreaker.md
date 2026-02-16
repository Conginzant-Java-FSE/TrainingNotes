# Circuit Breaker using Resilience4j 

# 1. Why Circuit Breaker is Needed in Microservices

![Image](https://klotzandrew.com/static/035be96d5c326a5c9d91e2e3be2bb13a/45d84/cascading-failure.jpg)

![Image](https://miro.medium.com/0%2ARwQ1gn_I0imGKmiY.png)

![Image](https://www.researchgate.net/publication/249602035/figure/fig1/AS%3A298292563988503%401448129897902/Fault-propagation-in-a-distributed-system.png)

![Image](https://substackcdn.com/image/fetch/%24s_%21WM-z%21%2Cf_auto%2Cq_auto%3Agood%2Cfl_progressive%3Asteep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F92ff127d-de11-43eb-bdf7-9e924f897ef9_1232x1000.png)

In microservices architecture:

* Services depend on other services.
* Network failures are common.
* A slow or failing service can cause **cascading failures**.

Example:

```
User Service → Product Service → Inventory Service
```

If Inventory Service is down:

* Product Service waits
* Threads get blocked
* User Service becomes slow
* Entire system degrades

This is called **cascading failure**.

To prevent this, we use the **Circuit Breaker Pattern**.

---

# 2. What is Circuit Breaker Pattern?

The Circuit Breaker Pattern:

* Monitors service failures
* Stops calling a failing service
* Returns fallback response
* Recovers automatically after timeout

It behaves like an electrical circuit breaker.

---

# 3. What is Resilience4j?

**Resilience4j** is a lightweight fault tolerance library designed for Java 8 and functional programming.

It provides:

* Circuit Breaker
* Retry
* Rate Limiter
* Bulkhead
* Time Limiter
* Cache

Spring Boot integrates it via:

* `resilience4j-spring-boot3`
* `spring-cloud-starter-circuitbreaker-resilience4j`

---

# 4. Circuit Breaker States

## 1. CLOSED

* Normal state
* Requests flow normally
* Failures are monitored

## 2. OPEN

* Failure threshold exceeded
* All requests blocked
* Fallback executed immediately

## 3. HALF-OPEN

* After wait duration
* Limited test requests allowed
* If successful → CLOSED
* If failed → OPEN again

---

# 5. Practical Architecture

We will build:

* Product Service
* User Service
* Circuit Breaker in User Service
* Product Service simulated failure

Flow:

```
Client → User Service → Product Service
                 ↓
         Circuit Breaker
```

---

# STEP 1 – Create Product Service

Controller:

```java
@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<String> getProducts() {

        if (true) {
            throw new RuntimeException("Product Service Down");
        }

        return List.of("Laptop", "Phone");
    }
}
```

This simulates failure.

---

# STEP 2 – Create User Service

Add Dependency:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
```

If using Eureka:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

# STEP 3 – Enable Circuit Breaker

No special annotation required in Spring Boot 3.

---

# STEP 4 – Configure RestTemplate

```java
@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

---

# STEP 5 – Add Circuit Breaker

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/products")
    @CircuitBreaker(name = "productServiceCB", fallbackMethod = "fallbackProducts")
    public List<?> getProducts() {

        return restTemplate.getForObject(
                "http://PRODUCT-SERVICE/products",
                List.class
        );
    }

    public List<String> fallbackProducts(Exception ex) {
        return List.of("Fallback Product 1", "Fallback Product 2");
    }
}
```

---

# STEP 6 – Configure Resilience4j

application.yml:

```yaml
resilience4j:
  circuitbreaker:
    instances:
      productServiceCB:
        register-health-indicator: true
        sliding-window-size: 5
        minimum-number-of-calls: 3
        failure-rate-threshold: 50
        wait-duration-in-open-state: 10s
        permitted-number-of-calls-in-half-open-state: 2
```

---

# 6. Understanding Configuration Parameters

| Property                                     | Meaning                         |
| -------------------------------------------- | ------------------------------- |
| sliding-window-size                          | Number of calls tracked         |
| minimum-number-of-calls                      | Minimum calls before evaluation |
| failure-rate-threshold                       | % of failures to open circuit   |
| wait-duration-in-open-state                  | Time before HALF-OPEN           |
| permitted-number-of-calls-in-half-open-state | Test calls allowed              |

Example:

* 5 calls
* 3 failures
* Failure rate = 60%
* Circuit opens

---

# 7. Testing Circuit Breaker

Step-by-step:

1. Product Service throws exception.
2. First few calls → fail.
3. Failure rate exceeds threshold.
4. Circuit becomes OPEN.
5. Fallback method executed immediately.
6. After 10 seconds → HALF-OPEN.
7. If success → CLOSED.

---

# 8. Monitoring Circuit Breaker

Add Actuator:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Expose endpoints:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health
```

Check:

```
http://localhost:8082/actuator/health
```

Circuit breaker status visible.

---

# 9. Circuit Breaker with WebClient (Reactive)

```java
@CircuitBreaker(name = "productServiceCB", fallbackMethod = "fallback")
public Mono<List> getProducts() {

    return webClient.get()
            .uri("http://PRODUCT-SERVICE/products")
            .retrieve()
            .bodyToMono(List.class);
}
```

---

# 10. Production Best Practices

1. Always configure timeout with TimeLimiter.
2. Use Retry for transient failures.
3. Combine with Bulkhead to prevent thread exhaustion.
4. Monitor metrics with Prometheus + Grafana.
5. Avoid very small sliding windows.

---

# 11. Common Mistakes

* Fallback method signature mismatch
* Exception type not matching
* Not enabling Actuator
* Circuit not opening because minimum calls not reached
* Using blocking calls in reactive flow

---

# 12. Circuit Breaker vs Retry

| Circuit Breaker               | Retry                                 |
| ----------------------------- | ------------------------------------- |
| Stops calling failing service | Re-attempts failed call               |
| Prevents cascading failures   | Useful for temporary network glitches |
| Opens circuit                 | Retries limited times                 |

Often used together.

---

# 13. Real-World Flow

```
Client
   ↓
API Gateway
   ↓
User Service
   ↓
Circuit Breaker
   ↓
Product Service
```

If Product fails:

* Circuit opens
* Fallback response returned
* System remains responsive

---

# 14. Interview Questions

1. What problem does Circuit Breaker solve?
2. Explain CLOSED, OPEN, HALF-OPEN states.
3. Difference between Retry and Circuit Breaker?
4. What is sliding window?
5. How does failure rate threshold work?
6. Can we use Circuit Breaker with WebFlux?
7. What is Bulkhead pattern?
8. How to monitor Resilience4j metrics?

---

# 15. Summary

Circuit Breaker:

* Prevents cascading failures
* Improves resilience
* Protects system resources
* Enables graceful degradation

Resilience4j:

* Lightweight
* Production-ready
* Integrated with Spring Boot
* Supports multiple resilience patterns

It is a mandatory component in robust microservices architecture.

---
