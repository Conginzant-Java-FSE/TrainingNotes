# Monitoring and Distributed Tracing
# 1. Why Monitoring is Critical in Microservices

![Image](https://signoz.io/img/blog/2022/03/ds_microservices_architecture.webp)

![Image](https://cdn.sanity.io/images/rdn92ihu/production/ef70ddb381e3af2ee9d86aa8380d20015aa1b097-1263x774.png?auto=format\&fit=max)

![Image](https://dz2cdn1.dzone.com/storage/temp/15246979-1633629046983.png)

![Image](https://raw.githubusercontent.com/spring-cloud/spring-cloud-sleuth/2.2.x/docs/src/main/asciidoc/images/trace-id.png)

In microservices architecture:

* A single user request passes through multiple services.
* Failures may occur in any service.
* Performance bottlenecks are hard to identify.
* Logs are distributed across services.

Example:

```
Client → API Gateway → User Service → Product Service → Inventory Service
```

If the request takes 5 seconds:

* Which service is slow?
* Where did failure occur?
* What is the request path?

This problem is solved using **Distributed Tracing**.

---

# 2. What is Zipkin?

**Zipkin** is a distributed tracing system used to:

* Track request flow across services
* Measure latency
* Identify bottlenecks
* Visualize trace timelines

Originally developed at Twitter.

---

# 3. Core Concepts of Distributed Tracing

## 1. Trace

A complete request journey across services.

## 2. Span

A single unit of work within a trace.

Example:

Trace:

```
User Request
```

Spans:

* Gateway processing
* User Service processing
* Product Service call
* Database query

## 3. Trace ID

Unique identifier for entire request.

## 4. Span ID

Identifier for each step in trace.

---

# 4. How Zipkin Works with Spring Boot

Spring Boot integrates tracing via:

* **Spring Cloud Sleuth** (older approach)
* Micrometer Tracing (modern Spring Boot 3 approach)

Flow:

```
Microservice → Sleuth/Micrometer → Zipkin Server → Zipkin UI
```

Each request generates:

* Trace ID
* Span ID
* Timing information

---

# 5. Architecture Setup

We will build:

* Zipkin Server
* Product Service
* User Service
* Inter-service call
* Trace visualization

---

# STEP 1 – Run Zipkin Server

### Option 1: Run via Docker

```bash
docker run -d -p 9411:9411 openzipkin/zipkin
```

Access UI:

```
http://localhost:9411
```

You will see Zipkin Dashboard.

---

# STEP 2 – Add Dependencies (Spring Boot 3+)

For modern Spring Boot (3.x):

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>

<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

---

# STEP 3 – Configure application.yml

Add in both User Service and Product Service:

```yaml
management:
  tracing:
    sampling:
      probability: 1.0   # Trace all requests

  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans
```

Explanation:

* `probability: 1.0` means trace 100% requests.
* Zipkin endpoint receives trace data.

---

# STEP 4 – Create Product Service

```java
@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<String> getProducts() throws InterruptedException {

        Thread.sleep(1000);  // Simulate delay

        return List.of("Laptop", "Phone");
    }
}
```

---

# STEP 5 – Create User Service Calling Product Service

Using RestTemplate:

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/products")
    public List<?> getProducts() {

        return restTemplate.getForObject(
                "http://localhost:8081/products",
                List.class
        );
    }
}
```

Make sure RestTemplate bean is defined.

---

# STEP 6 – Test the Flow

Call:

```
http://localhost:8082/users/products
```

Then open:

```
http://localhost:9411
```

Click:

* Run Query
* Select service name
* View trace

---

# 6. Understanding Zipkin UI

Zipkin shows:

* Trace timeline
* Service name
* Duration per span
* Parent-child relationships
* Error status

You can visually see:

```
User Service → Product Service
```

And identify:

* Which service took longer
* Where error occurred

---

# 7. Logs with Trace ID

With tracing enabled, logs include:

```
[traceId-spanId]
```

Example:

```
[abcd1234efgh5678-ijkl9012] Processing request
```

This allows correlating logs across services.

---

# 8. Integration with Eureka and Gateway

In real microservices:

```
Client
   ↓
API Gateway
   ↓
User Service
   ↓
Product Service
   ↓
Database
```

All services automatically propagate:

* Trace ID
* Span ID

No manual coding required.

---

# 9. Monitoring vs Tracing vs Metrics

| Concept    | Purpose                                     |
| ---------- | ------------------------------------------- |
| Logging    | Record events                               |
| Metrics    | Numeric measurements (CPU, memory, latency) |
| Tracing    | Track request journey                       |
| Monitoring | Overall system health                       |

Zipkin focuses on **distributed tracing**.

---

# 10. Production Best Practices

1. Do not use sampling probability = 1.0 in production.
   Example:

   ```yaml
   probability: 0.1
   ```

   (Trace 10% requests)

2. Integrate with:

   * Prometheus
   * Grafana

3. Use centralized logging (ELK stack).

4. Monitor:

   * Slow endpoints
   * Error rates
   * Service dependencies

---

# 11. Common Problems

### No Traces in Zipkin

* Zipkin server not running
* Wrong endpoint URL
* Sampling set to 0
* Firewall blocking port 9411

### Trace Not Propagated

* Custom HTTP client not forwarding headers
* Missing dependencies

---

# 12. Interview Questions

1. What is distributed tracing?
2. What is difference between trace and span?
3. How does trace ID propagate?
4. What is sampling probability?
5. Difference between Zipkin and Prometheus?
6. What is Micrometer?
7. How to monitor microservices in production?
8. Can Zipkin be used with WebClient?

---

# 13. Summary

Zipkin provides:

* Distributed tracing
* Performance visualization
* Latency breakdown
* Failure analysis

With Spring Boot 3:

* Use Micrometer Tracing
* Use Zipkin reporter
* Enable Actuator
* Configure sampling

It is essential for diagnosing complex microservices systems.

---
