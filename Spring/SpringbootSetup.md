# Spring Boot 
---

## 1. What is Spring Boot?

**Spring Boot** is an opinionated framework built on top of Spring that:

* Removes **boilerplate configuration**
* Provides **embedded servers**
* Enables **auto-configuration**
* Supports **production-ready defaults**

> Goal: *Get a Spring application running with minimum configuration.*

---

## 2. Problems with Traditional Spring (Why Boot Exists)

| Traditional Spring    | Spring Boot                   |
| --------------------- | ----------------------------- |
| XML-heavy config      | Java/Annotation-based         |
| External server setup | Embedded server               |
| Manual bean wiring    | Auto-configuration            |
| Multiple config files | Convention over configuration |

---

## 3. Spring Boot Architecture (High Level)

```
@SpringBootApplication
        ↓
Auto Configuration
        ↓
Embedded Server (Tomcat/Jetty/Undertow)
        ↓
Spring Application Context
```

---

## 4. Spring Boot Server Setup

### 4.1 Embedded Server Concept

Spring Boot ships with an **embedded web server**, so:

* No WAR
* No external Tomcat setup
* Run as a standalone JAR

### Default Server

| Starter                     | Server |
| --------------------------- | ------ |
| spring-boot-starter-web     | Tomcat |
| spring-boot-starter-webflux | Netty  |

---

### 4.2 Running the Application

```bash
mvn spring-boot:run
```

or

```bash
java -jar app.jar
```

---

### 4.3 Changing Server Port

```properties
server.port=9090
```

---

### 4.4 Switching Embedded Server

Exclude Tomcat and include Jetty:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

---

## 5. Spring Boot Startup Flow (Important)

### Main Class

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

---

### What Happens Internally?

1. Create `SpringApplication`
2. Detect application type (Servlet / Reactive)
3. Load `ApplicationContext`
4. Apply auto-configurations
5. Start embedded server
6. Initialize beans
7. Application ready

---

## 6. Startup Dependencies (Starters)

### What is a Starter?

A **starter** is a **predefined dependency bundle** for a use case.

| Starter                      | Purpose         |
| ---------------------------- | --------------- |
| spring-boot-starter-web      | REST, MVC       |
| spring-boot-starter-data-jpa | JPA + Hibernate |
| spring-boot-starter-security | Security        |
| spring-boot-starter-test     | Testing         |

---

### Why Starters?

* No version conflicts
* Opinionated defaults
* Faster setup

---

## 7. Dependency Management in Spring Boot

Spring Boot uses:

* **Spring Boot BOM** (Bill of Materials)

You don’t specify versions:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Boot manages:

* Spring
* Jackson
* Hibernate
* Tomcat

---

## 8. Auto-Configuration (Core Concept)

### What is Auto-Configuration?

> Spring Boot automatically configures beans **based on classpath, properties, and environment**.

---

### Example

If these are present:

* `spring-webmvc`
* `DispatcherServlet`
* `Tomcat`

➡️ Spring Boot auto-configures:

* `DispatcherServlet`
* `ViewResolver`
* `HandlerMappings`

---

### Key Annotation

```java
@EnableAutoConfiguration
```

Part of:

```java
@SpringBootApplication
```

---

### How Auto-Configuration Works Internally

1. Reads `spring.factories`
2. Loads `AutoConfiguration` classes
3. Applies conditions using:

   * `@ConditionalOnClass`
   * `@ConditionalOnMissingBean`
   * `@ConditionalOnProperty`

---

### Example Auto-Config Class

```java
@Configuration
@ConditionalOnClass(DataSource.class)
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}
```

---

## 9. Disabling Auto-Configuration

### Disable Specific Class

```java
@SpringBootApplication(
  exclude = DataSourceAutoConfiguration.class
)
```

---

### Disable via Properties

```properties
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
```

---

## 10. Spring Boot CLI

### What is Spring Boot CLI?

A **command-line tool** to:

* Run Spring apps quickly
* Use Groovy scripts
* Avoid boilerplate

---

### Installing CLI

```bash
sdk install springboot
```

or via manual zip.

---

### Creating App Using CLI

```groovy
@RestController
class HelloController {

    @GetMapping("/")
    String hello() {
        "Hello Spring Boot CLI"
    }
}
```

Run:

```bash
spring run app.groovy
```

---

### CLI Features

* Dependency auto-resolution
* Embedded server
* Groovy-based scripting

---

## 11. Application Properties & Profiles

### application.properties

```properties
spring.application.name=demo-app
server.port=8081
```

---

### Profiles

```properties
spring.profiles.active=dev
```

Files:

* `application-dev.properties`
* `application-prod.properties`

---

## 12. Production-Ready Features (Actuator)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Endpoints:

* `/actuator/health`
* `/actuator/metrics`
* `/actuator/env`

---

## 13. Common Interview Questions (Quick Hits)

**Q: How does Spring Boot differ from Spring?**
Boot provides auto-configuration, embedded servers, starters, and production-ready features.

**Q: How does Boot decide what to auto-configure?**
By checking classpath, properties, and existing beans.

**Q: Can we disable auto-config?**
Yes, selectively or fully.

**Q: Is Spring Boot suitable for microservices?**
Yes, it’s the de-facto choice.

---

## 14. Summary

* Spring Boot simplifies Spring
* Embedded servers remove deployment pain
* Starters manage dependencies
* Auto-configuration reduces config
* CLI helps rapid prototyping

---
