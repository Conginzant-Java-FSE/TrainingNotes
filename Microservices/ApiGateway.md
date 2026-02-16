# API Gateway and Load Balancing in Spring Boot Microservices

# 1. What is an API Gateway?

![Image](https://learn.microsoft.com/en-us/dotnet/architecture/microservices/architect-microservice-container-applications/media/direct-client-to-microservice-communication-versus-the-api-gateway-pattern/api-gateway-azure-api-management.png)

![Image](https://i.postimg.cc/kDP2Dj23/Spring-Cloud-Architecture.png)

![Image](https://miro.medium.com/1%2AgW4JrHTr86HnTrouQYLgJQ.png)

![Image](https://microservices.io/i/apigateway.jpg)

An **API Gateway** is a single entry point for all client requests in a microservices architecture.

Instead of clients directly calling individual services:

```
Client → User Service
Client → Product Service
Client → Order Service
```

Clients call:

```
Client → API Gateway → Microservices
```

---

## Why API Gateway is Required

In microservices:

* Multiple services expose endpoints
* Each service runs on different ports
* Services scale dynamically
* Security must be centralized

The API Gateway solves:

* Centralized routing
* Security enforcement
* Rate limiting
* Request/Response transformation
* Load balancing
* Monitoring

---

# 2. What is Load Balancing?

![Image](https://www.researchgate.net/publication/347137588/figure/fig1/AS%3A1023823721807872%401621110005104/Microservice-load-balancing-architecture-diagram.png)

![Image](https://cdn.hashnode.com/res/hashnode/image/upload/v1615696959723/QWKZWTqv8.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AaylXEVd-yInc1xrEWuZ2xw.png)

![Image](https://i.sstatic.net/juYJs.png)

**Load balancing** distributes incoming traffic across multiple instances of a service.

Example:

If `PRODUCT-SERVICE` runs on:

* 8081
* 8082
* 8083

Load balancer distributes requests evenly.

---

## Types of Load Balancing

### 1. Client-Side Load Balancing

* Client chooses instance
* Example: Spring Cloud LoadBalancer
* Works with service discovery

### 2. Server-Side Load Balancing

* Reverse proxy (NGINX, HAProxy)
* Gateway decides instance

Spring Cloud Gateway + Eureka uses **client-side load balancing internally**.

---

# 3. API Gateway Pattern in Microservices

Components:

* API Gateway
* Service Discovery (Eureka)
* Microservices
* Load Balancer

Flow:

```
Client → Gateway → Eureka → Target Service Instance
```

---

# 4. Spring Cloud Gateway

**Spring Cloud Gateway** is the recommended API Gateway for modern Spring Boot applications.

It is:

* Reactive (built on Spring WebFlux)
* Non-blocking
* High performance
* Production-ready

---

# 5. Architecture Setup for Tutorial

We will create:

* Eureka Server
* Product Service
* User Service
* API Gateway

---

# STEP 1 – Eureka Server

Add dependency:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

Main class:

```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
```

application.yml:

```yaml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

Run and verify at:

```
http://localhost:8761
```

---

# STEP 2 – Product Service

Dependency:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

application.yml:

```yaml
server:
  port: 8081

spring:
  application:
    name: PRODUCT-SERVICE

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

Controller:

```java
@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<String> getProducts() {
        return List.of("Laptop", "Phone", "Tablet");
    }
}
```

Run multiple instances:

* 8081
* 8082
* 8083

(Use different ports)

---

# STEP 3 – Create API Gateway

Dependency:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

## application.yml for Gateway

```yaml
server:
  port: 8080

spring:
  application:
    name: API-GATEWAY

  cloud:
    gateway:
      routes:
        - id: product-route
          uri: lb://PRODUCT-SERVICE
          predicates:
            - Path=/products/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

---

## Important Keyword

```
uri: lb://PRODUCT-SERVICE
```

* `lb://` enables load balancing
* Gateway asks Eureka
* Chooses instance
* Routes request

---

# 6. How Load Balancing Works Internally

Spring Cloud Gateway uses:

* **Spring Cloud LoadBalancer**

Default strategy:

* Round Robin

Request 1 → 8081
Request 2 → 8082
Request 3 → 8083
Request 4 → 8081

---

# 7. Testing the Setup

Start:

1. Eureka Server
2. Multiple Product Service instances
3. API Gateway

Call:

```
http://localhost:8080/products
```

Observe logs:

Requests hit different instances.

---

# 8. Advanced Gateway Features

## 1. Filters

Add logging filter:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: product-route
          uri: lb://PRODUCT-SERVICE
          predicates:
            - Path=/products/**
          filters:
            - AddRequestHeader=X-Gateway, SpringCloud
```

---

## 2. Authentication at Gateway

* Validate JWT
* Forward token to services
* Centralized security

---

## 3. Rate Limiting

Prevent abuse by limiting:

* Requests per second
* Per IP
* Per user

---

## 4. Circuit Breaker

Integrate with Resilience4j:

```yaml
filters:
  - name: CircuitBreaker
    args:
      name: productCB
      fallbackUri: forward:/fallback
```

---

# 9. Comparison – With vs Without Gateway

| Without Gateway                    | With Gateway         |
| ---------------------------------- | -------------------- |
| Client calls each service directly | Single entry point   |
| Security duplicated                | Centralized security |
| Hard to manage routing             | Central routing      |
| Harder to monitor                  | Easier monitoring    |
| Hardcoded URLs                     | Service discovery    |

---

# 10. Production Architecture

Typical setup:

```
Client
   ↓
API Gateway
   ↓
Load Balancer
   ↓
Microservices
   ↓
Database per service
```

Add:

* Centralized Logging
* Distributed Tracing
* Monitoring
* Config Server

---

# 11. Interview Questions

1. What is API Gateway pattern?
2. Why not expose microservices directly?
3. Difference between client-side and server-side load balancing?
4. What does lb:// mean?
5. How does Gateway integrate with Eureka?
6. How is load balancing done in Spring Cloud?
7. Can we use Gateway without Eureka?
8. Difference between Zuul and Spring Cloud Gateway?

---

# 12. Common Real-World Problems

### 404 from Gateway

* Service name mismatch
* Wrong Path predicate
* Service not registered

### Load Balancing Not Working

* Only one instance running
* Missing `lb://`
* Eureka not connected

---

# 13. Summary

API Gateway:

* Central entry point
* Routing
* Security
* Monitoring
* Rate limiting

Load Balancing:

* Distributes traffic
* Prevents overload
* Improves availability
* Works with service discovery

Together, they form the backbone of scalable Spring Boot microservices architecture.

---
