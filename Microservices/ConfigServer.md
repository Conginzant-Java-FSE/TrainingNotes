# Spring Cloud Config Server 
# 1. What is a Config Server?

![Image](https://miro.medium.com/1%2AcTPRDoBveeNbz9QAi5XJJg.png)

![Image](https://miro.medium.com/1%2AuPvPOfa3PRkaPQJe8KQCww.jpeg)

![Image](https://tech.asimio.net/images/config-server-spring-cloud-bus-rabbitmq-git-workflow.png)

![Image](https://dz2cdn1.dzone.com/storage/temp/14248032-architecture.jpg)

In microservices architecture, each service requires configuration such as:

* Database URLs
* Credentials
* API keys
* Feature flags
* Environment properties

If every service maintains its own configuration:

* Changes require redeployment
* Configuration duplication occurs
* Environment management becomes complex

A **Config Server** centralizes configuration management.

---

# 2. What is Spring Cloud Config?

**Spring Cloud Config** provides:

* Centralized configuration
* Externalized properties
* Environment-based configuration
* Git-backed configuration storage
* Dynamic refresh capability

It consists of:

1. Config Server
2. Config Client (microservices)
3. Remote configuration repository (usually Git)

---

# 3. Why Do We Need Config Server?

## Problems Without Config Server

* Each microservice has its own `application.yml`
* Updating database URL requires redeploying all services
* Hard to manage multiple environments (dev, qa, prod)
* Secrets exposed in codebase

## Benefits of Config Server

* Centralized configuration
* Environment-based property management
* Version control using Git
* No need to rebuild services for config changes
* Supports encryption of sensitive values

---

# 4. How Spring Cloud Config Works

Flow:

```
Microservice → Config Server → Git Repository
```

Step-by-step:

1. Microservice starts.
2. It contacts Config Server.
3. Config Server fetches properties from Git.
4. Properties are returned to service.
5. Service initializes with external configuration.

---

# 5. Architecture Overview

Components:

* Config Server
* Git Repository
* Eureka (optional)
* Microservices

Typical startup order:

1. Config Server
2. Eureka Server
3. Microservices

---

# 6. Practical Tutorial – Step-by-Step

We will build:

* Config Server
* Product Service (Config Client)
* Git configuration repository

---

# STEP 1 – Create Git Repository for Configuration

Create a Git repository:

Example files:

```
product-service.yml
product-service-dev.yml
product-service-prod.yml
```

Example `product-service.yml`:

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/productdb
    username: root
    password: root

custom:
  message: Default Configuration
```

---

# STEP 2 – Create Config Server

## 2.1 Add Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

---

## 2.2 Enable Config Server

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

---

## 2.3 application.yml

```yaml
server:
  port: 8888

spring:
  application:
    name: CONFIG-SERVER

  cloud:
    config:
      server:
        git:
          uri: https://github.com/your-repo/config-repo
          clone-on-start: true
```

---

## Run Config Server

Test in browser:

```
http://localhost:8888/product-service/default
```

Response: JSON with configuration properties.

---

# STEP 3 – Create Product Service (Config Client)

## 3.1 Add Dependencies

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

Optional (if using Eureka):

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

## 3.2 bootstrap.yml (Important)

```yaml
spring:
  application:
    name: product-service

  config:
    import: "optional:configserver:http://localhost:8888"
```

Note:

* `bootstrap.yml` is used to load configuration before application context starts.
* In Spring Boot 3.x, use `spring.config.import`.

---

## 3.3 Remove Local application.yml

No need for:

* server.port
* datasource
* custom properties

They come from Config Server.

---

# STEP 4 – Access Config Properties in Service

```java
@RestController
@RequestMapping("/products")
public class ProductController {

    @Value("${custom.message}")
    private String message;

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }
}
```

Now call:

```
http://localhost:8081/products/message
```

Value comes from Git repository.

---

# 7. Profile-Based Configuration

You can maintain:

```
product-service-dev.yml
product-service-qa.yml
product-service-prod.yml
```

Activate profile:

```yaml
spring:
  profiles:
    active: dev
```

Config Server automatically loads:

```
product-service-dev.yml
```

---

# 8. Dynamic Refresh of Configuration

To refresh configuration without restart:

Add dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Enable refresh endpoint:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: refresh
```

Add annotation:

```java
@RefreshScope
@RestController
public class ProductController {
}
```

Trigger refresh:

```
POST http://localhost:8081/actuator/refresh
```

Now updated properties are applied without restart.

---

# 9. Secure Sensitive Properties

Config Server supports encryption:

Example encrypted value:

```
{cipher}AJSKDSJASJDSA==
```

Enable encryption:

```yaml
encrypt:
  key: my-secret-key
```

---

# 10. Config Server with Eureka

Instead of hardcoding:

```yaml
spring:
  config:
    import: configserver:http://localhost:8888
```

You can register Config Server in Eureka and use service discovery.

---

# 11. Production Best Practices

1. Use private Git repository
2. Use SSH instead of HTTPS
3. Enable encryption
4. Separate environment branches
5. Secure Config Server with authentication
6. Do not expose Config Server publicly
7. Use Vault for secret management in highly secure systems

---

# 12. Common Issues

### Config Not Loading

* Wrong spring.application.name
* Git repo not accessible
* Wrong profile name
* Config Server not started

### 404 on Config URL

* File naming mismatch
* Incorrect branch

---

# 13. Advantages of Config Server

* Centralized configuration
* Version controlled
* Profile-based environment separation
* Dynamic refresh
* Supports multiple backends (Git, SVN, Vault, filesystem)

---

# 14. Interview Questions

1. Why do we need Config Server?
2. What is bootstrap.yml?
3. Difference between application.yml and bootstrap.yml?
4. How does dynamic refresh work?
5. How is security handled?
6. What happens if Config Server is down?
7. How to manage multiple environments?

---

# 15. Summary

Spring Cloud Config:

* Externalizes configuration
* Centralizes property management
* Supports environment-based configuration
* Integrates with Git
* Supports runtime refresh
* Essential for scalable microservices architecture

---

