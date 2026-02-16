# RestTemplate, WebClient and FeignClient in Spring Boot Microservices


# 1. Why Do We Need HTTP Clients in Microservices?

![Image](https://learn.microsoft.com/en-us/dotnet/architecture/microservices/architect-microservice-container-applications/media/communication-in-microservice-architecture/sync-vs-async-patterns-across-microservices.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1200/1%2Av-5o8Wb2ANBBXIKU1yEAKQ.png)

![Image](https://www.researchgate.net/publication/385686967/figure/fig1/AS%3A11431281289420869%401731185740700/Spring-Boot-Microservice-Architecture.ppm)

![Image](https://dz2cdn1.dzone.com/storage/temp/13596955-microservice-architecture.png)

In microservices architecture:

* Services communicate over HTTP (REST APIs)
* Service A calls Service B
* Calls may be synchronous or asynchronous
* We need an HTTP client library inside Spring Boot applications

Spring Boot provides multiple approaches:

1. RestTemplate (Traditional, blocking)
2. WebClient (Reactive, non-blocking)
3. FeignClient (Declarative REST client)

---

# 2. RestTemplate

## What is RestTemplate?

`RestTemplate` is a synchronous, blocking HTTP client introduced in early Spring versions.

It is part of:

**Spring Framework**

It is simple but now considered legacy (not deprecated, but WebClient is preferred).

---

## Characteristics

* Blocking I/O
* Thread-per-request model
* Simple API
* Works well for traditional MVC applications
* Integrated with LoadBalancer via `@LoadBalanced`

---

## Architecture Flow

```
User Service → RestTemplate → Product Service
```

---

## Step-by-Step Example (RestTemplate)

### Step 1 – Add Dependency

For microservices with discovery:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

### Step 2 – Configure RestTemplate Bean

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

`@LoadBalanced` enables service discovery.

---

### Step 3 – Call Another Service

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/products")
    public List<?> getProducts() {

        return restTemplate.getForObject(
                "http://PRODUCT-SERVICE/products",
                List.class
        );
    }
}
```

---

## RestTemplate Methods

| Method        | Purpose          |
| ------------- | ---------------- |
| getForObject  | GET request      |
| postForObject | POST request     |
| exchange      | Advanced control |
| put           | PUT request      |
| delete        | DELETE request   |

---

## Limitations

* Blocking calls
* Thread exhaustion under heavy load
* Not suitable for high concurrency systems
* Boilerplate code

---

# 3. WebClient

## What is WebClient?

`WebClient` is a reactive, non-blocking HTTP client.

It is part of:

**Spring WebFlux**

Designed for high-performance reactive applications.

---

## Blocking vs Non-Blocking

Blocking:

* Thread waits for response

Non-blocking:

* Thread released while waiting
* Event-driven model

---

## Characteristics

* Non-blocking I/O
* Reactive Streams support
* Uses Mono and Flux
* Suitable for high concurrency

---

## Step-by-Step Example (WebClient)

### Step 1 – Add Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

---

### Step 2 – Configure WebClient

```java
@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
```

---

### Step 3 – Call Product Service

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/products")
    public Mono<List> getProducts() {

        return webClientBuilder.build()
                .get()
                .uri("http://PRODUCT-SERVICE/products")
                .retrieve()
                .bodyToMono(List.class);
    }
}
```

---

## Reactive Return Types

| Type | Description        |
| ---- | ------------------ |
| Mono | 0 or 1 element     |
| Flux | 0 to many elements |

---

## When to Use WebClient

* High throughput systems
* Streaming APIs
* Reactive microservices
* Event-driven systems

---

# 4. FeignClient

## What is Feign?

**OpenFeign** is a declarative REST client.

Spring integrates it as:

`spring-cloud-starter-openfeign`

You define an interface, and Spring generates implementation automatically.

---

## Why Feign is Popular

* Clean code
* Declarative style
* Less boilerplate
* Built-in load balancing
* Easy integration with Resilience4j

---

## Step-by-Step Example (Feign)

### Step 1 – Add Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

---

### Step 2 – Enable Feign

```java
@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {
}
```

---

### Step 3 – Create Feign Interface

```java
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/products")
    List<String> getProducts();
}
```

---

### Step 4 – Use Feign Client

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/products")
    public List<String> getProducts() {
        return productClient.getProducts();
    }
}
```

That’s it. No manual HTTP handling required.

---

# 5. Comparison – RestTemplate vs WebClient vs Feign

| Feature        | RestTemplate | WebClient       | FeignClient                |
| -------------- | ------------ | --------------- | -------------------------- |
| Blocking       | Yes          | No              | Blocking by default        |
| Reactive       | No           | Yes             | No (unless reactive Feign) |
| Boilerplate    | Medium       | Medium          | Very Low                   |
| Learning Curve | Low          | Medium          | Very Low                   |
| Performance    | Moderate     | High            | Moderate                   |
| Declarative    | No           | No              | Yes                        |
| Recommended    | Legacy       | Modern Reactive | Modern Declarative         |

---

# 6. Which One Should You Use?

### Use RestTemplate When:

* Legacy MVC application
* Small internal tool
* No high concurrency needs

### Use WebClient When:

* Reactive architecture
* High scalability required
* Non-blocking design needed

### Use Feign When:

* Clean declarative style preferred
* Most common enterprise choice
* Working with Spring Cloud stack

---

# 7. Production Best Practices

1. Always use service discovery (Eureka or Kubernetes).
2. Combine with Circuit Breaker (Resilience4j).
3. Configure timeouts.
4. Enable logging for debugging.
5. Avoid blocking calls inside reactive flows.

---

# 8. Common Interview Questions

1. Why is RestTemplate considered legacy?
2. Difference between blocking and non-blocking?
3. What is Mono and Flux?
4. How does Feign implement HTTP calls?
5. How to add fallback in Feign?
6. Which client is best for high-performance systems?
7. Can we use WebClient in non-reactive app?
8. Difference between Feign and WebClient?

---

# 9. Summary

RestTemplate:

* Traditional
* Blocking
* Simple

WebClient:

* Reactive
* Non-blocking
* High performance

FeignClient:

* Declarative
* Clean and simple
* Most widely used in enterprise Spring Cloud microservices

In modern Spring Boot microservices:

* Use Feign for simplicity
* Use WebClient for reactive systems
* Avoid new development with RestTemplate unless required

---
