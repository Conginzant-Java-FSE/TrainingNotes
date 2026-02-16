# Eureka Service Discovery 

# 1. What is Service Discovery?

In a **microservices architecture**, services communicate with each other. Since services are:

* Dynamically deployed
* Scaled up/down
* Running on different ports or containers

We need a mechanism to **locate service instances dynamically**.

This mechanism is called **Service Discovery**.

There are two types:

1. Client-Side Discovery
2. Server-Side Discovery

**Eureka** implements **client-side service discovery**.

---

# 2. What is Eureka?

![Image](https://www.todaysoftmag.com/images/articles/tsm34/large/a23.png)

![Image](https://miro.medium.com/1%2AjNwMvNFAd3rixSszJZBcsA.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AopwoTH3vt54NZ2RUuU5Www.png)

![Image](https://miro.medium.com/1%2A43NgBoAW6h-vZTgyknM8xw.png)

**Netflix Eureka** is a REST-based service registry developed by **Netflix**.

It is part of the Netflix OSS stack and integrated into Spring via **Spring Cloud Netflix**.

---

## Role of Eureka

Eureka acts as:

* Service Registry
* Service Directory
* Naming Server

It stores:

* Service Name
* Host
* Port
* Health status
* Instance metadata

---

# 3. How Eureka Works

## Step-by-Step Flow

1. Microservice starts.
2. It registers itself with Eureka Server.
3. Eureka stores instance metadata.
4. Other services query Eureka for service locations.
5. Client performs load balancing.

---

## Architecture Flow

```
User → API Gateway → User Service → (calls) Product Service
                                  ↓
                             Eureka Server
```

---

# 4. Eureka Architecture Components

### 1. Eureka Server

* Central registry
* Stores service instances

### 2. Eureka Client

* Registers service
* Fetches registry
* Sends heartbeat

### 3. Service Instances

* Microservices (UserService, ProductService)

---

# 5. Important Concepts

## 1. Self-Registration

Each service registers itself on startup.

## 2. Heartbeat Mechanism

* Default: every 30 seconds
* Prevents removal from registry

## 3. Self-Preservation Mode

* Prevents mass eviction during network issues

## 4. Service ID

Logical name used instead of IP address.

Example:

```
http://PRODUCT-SERVICE/products
```

---

# 6. Practical Tutorial – Spring Boot with Eureka

We will create:

* Eureka Server
* User Service
* Product Service
* Inter-service communication

---

# STEP 1 – Create Eureka Server

## 1.1 Add Dependencies (pom.xml)

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

---

## 1.2 Main Class

```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
```

---

## 1.3 application.yml

```yaml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

---

## Run the Server

Access:

```
http://localhost:8761
```

You will see Eureka Dashboard.

---

# STEP 2 – Create Product Service

## 2.1 Add Dependencies

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

## 2.2 application.yml

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

---

## 2.3 Main Class

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
```

---

## 2.4 Sample Controller

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

---

# STEP 3 – Create User Service

Same dependency as Product Service.

---

## application.yml

```yaml
server:
  port: 8082

spring:
  application:
    name: USER-SERVICE

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

---

# STEP 4 – Inter-Service Communication

Without Eureka:

```
http://localhost:8081/products
```

With Eureka:

```
http://PRODUCT-SERVICE/products
```

---

## Using RestTemplate with Load Balancing

### Configuration

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

### Service Call

```java
@Autowired
private RestTemplate restTemplate;

@GetMapping("/user-products")
public List<?> getProducts() {
    return restTemplate.getForObject(
        "http://PRODUCT-SERVICE/products",
        List.class
    );
}
```

Now:

* User Service asks Eureka
* Eureka returns Product Service instance
* Call is made dynamically

---

# 7. Client-Side Load Balancing

Spring Cloud integrates with:

* Ribbon (legacy)
* Spring Cloud LoadBalancer (modern)

If multiple Product Service instances run:

* 8081
* 8083
* 8085

Requests are load-balanced automatically.

---

# 8. Important Configuration Properties

```yaml
eureka:
  instance:
    prefer-ip-address: true
  client:
    fetch-registry: true
    register-with-eureka: true
    registry-fetch-interval-seconds: 30
```

---

# 9. High Availability Eureka

In production:

* Run multiple Eureka servers
* Register them with each other
* Prevent single point of failure

Example:

* Eureka1: 8761
* Eureka2: 8762

---

# 10. Advantages of Eureka

1. Eliminates hard-coded URLs
2. Supports dynamic scaling
3. Enables fault tolerance
4. Works well in containerized environments
5. Integrates seamlessly with Spring Boot

---

# 11. Limitations

1. Netflix no longer actively develops Eureka
2. Not ideal for cloud-native Kubernetes environments
3. Requires self-hosting
4. Consistency model is eventual consistency

Modern alternatives:

* Consul
* Zookeeper
* Kubernetes Service Discovery

---

# 12. Common Interview Questions

1. What is service discovery?
2. How does Eureka self-preservation work?
3. Difference between Eureka and Consul?
4. What happens if Eureka server goes down?
5. How does load balancing work in Eureka?
6. Difference between @EnableEurekaServer and @EnableDiscoveryClient?
7. What is heartbeat and lease renewal?

---

# 13. Real-World Production Flow

In enterprise systems:

User → API Gateway → Microservices
Microservices → Eureka
Gateway → Uses service discovery + load balancing
Monitoring → Tracks instance health

---

# 14. Summary

Eureka provides:

* Dynamic service registration
* Client-side discovery
* Load balancing
* Health tracking

It is a foundational component in traditional Spring Cloud microservices architecture.

---
