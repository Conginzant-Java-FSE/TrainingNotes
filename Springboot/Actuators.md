# Spring Boot Actuator 

---

## 1. What is Spring Boot Actuator?

**Spring Boot Actuator** is a sub-project of Spring Boot that provides **production-ready features** to help you **monitor, manage, and inspect** your application at runtime.

It exposes **operational information** such as:

* Application health
* Metrics
* Environment properties
* Thread dumps
* HTTP request traces
* Custom application info

Actuator endpoints are mainly used by:

* DevOps teams
* Monitoring tools
* Cloud platforms (Kubernetes, AWS, Azure)

---

## 2. Why Actuator is Important

In real-world systems, once an application is deployed, you need visibility into:

* Is the app running?
* Is the database connected?
* How much memory is used?
* How many requests are coming in?
* Are threads blocked?

**Actuator answers all these questions without writing custom code.**

---

## 3. Key Characteristics

* Based on **HTTP endpoints**
* Mostly **read-only**
* Can be **secured**
* Highly **configurable**
* Integrates with **Micrometer** for metrics
* Cloud-native friendly

---

## 4. Dependency Setup

### Maven Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Once added, Actuator endpoints become available automatically.

---

## 5. Actuator Endpoints

### What is an Endpoint?

An **endpoint** exposes application information via:

* HTTP
* JMX
* WebFlux

Default base path:

```
/actuator
```

Example:

```
/actuator/health
```

---

## 6. Common Built-in Endpoints

| Endpoint      | Purpose                   |
| ------------- | ------------------------- |
| `/health`     | Application health status |
| `/info`       | Custom application info   |
| `/metrics`    | Performance metrics       |
| `/env`        | Environment properties    |
| `/beans`      | Spring beans              |
| `/mappings`   | URL mappings              |
| `/threaddump` | Thread dump               |
| `/heapdump`   | Heap memory dump          |
| `/loggers`    | Logging configuration     |
| `/conditions` | Auto-configuration report |

---

## 7. Health Endpoint

### Purpose

The `/health` endpoint shows whether the application is **UP or DOWN**.

Example response:

```json
{
  "status": "UP"
}
```

### Health Indicators

Spring Boot automatically checks:

* Database
* Disk space
* Message brokers
* Custom health indicators

Each component contributes to the final health status.

---

## 8. Info Endpoint

### Purpose

The `/info` endpoint exposes **custom application metadata**.

### Configuration

```properties
management.info.env.enabled=true

info.app.name=Order Service
info.app.version=1.0.0
info.app.owner=Backend Team
```

Response:

```json
{
  "app": {
    "name": "Order Service",
    "version": "1.0.0",
    "owner": "Backend Team"
  }
}
```

---

## 9. Metrics Endpoint

### Purpose

Provides **quantitative data** about application performance.

Examples:

* JVM memory usage
* HTTP request count
* CPU usage
* Thread count

Endpoint:

```
/actuator/metrics
```

Specific metric:

```
/actuator/metrics/jvm.memory.used
```

Metrics are powered by **Micrometer**.

---

## 10. Environment Endpoint

### `/env`

Exposes:

* System properties
* Environment variables
* Application properties

Useful for:

* Debugging configuration issues
* Verifying runtime values

⚠️ Sensitive values are masked by default.

---

## 11. Beans Endpoint

### `/beans`

Shows:

* All Spring beans
* Bean types
* Bean dependencies

Useful for:

* Debugging auto-configuration
* Understanding application context

---

## 12. Mappings Endpoint

### `/mappings`

Displays:

* Controller request mappings
* HTTP methods
* Handler methods

Very useful during:

* API debugging
* Endpoint verification

---

## 13. Logging Endpoint

### `/loggers`

Allows:

* Viewing current log levels
* Changing log levels at runtime

Example use cases:

* Enable DEBUG temporarily in production
* Reduce log noise without redeploying

---

## 14. Securing Actuator Endpoints

### Why Security is Mandatory

Actuator can expose:

* Internal configurations
* Memory dumps
* Thread states

These must **never be publicly accessible**.

---

### Basic Security Strategy

* Expose only required endpoints
* Restrict access using Spring Security
* Use different port if needed

---

## 15. Exposing Actuator Endpoints

### Default Behavior

Only these endpoints are exposed by default:

* `/health`
* `/info`

### Expose All Endpoints (Not Recommended for Prod)

```properties
management.endpoints.web.exposure.include=*
```

### Expose Selected Endpoints

```properties
management.endpoints.web.exposure.include=health,info,metrics
```

---

## 16. Changing Actuator Base Path

```properties
management.endpoints.web.base-path=/manage
```

Access:

```
/manage/health
```

---

## 17. Actuator Port Separation

You can run Actuator on a **different port**.

```properties
management.server.port=9090
```

This improves security and monitoring isolation.

---

## 18. Custom Health Indicator

You can define your own health checks.

Use cases:

* External API availability
* Cache health
* Feature flags

Custom indicators contribute to `/health`.

---

## 19. Actuator and Microservices

In microservices architecture, Actuator is used for:

* Service liveness checks
* Readiness probes
* Auto-scaling decisions
* Monitoring dashboards

Especially important in:

* Kubernetes
* Docker
* Cloud platforms

---

## 20. Actuator vs Logging

| Actuator              | Logging           |
| --------------------- | ----------------- |
| Runtime introspection | Historical record |
| Real-time status      | Past events       |
| Structured data       | Text data         |

Both are complementary.

---

## 21. Actuator in Kubernetes

Common probes:

* **Liveness probe** → `/health`
* **Readiness probe** → `/health`

Helps Kubernetes:

* Restart unhealthy pods
* Stop traffic to unhealthy services

---

## 22. Best Practices

* Never expose all endpoints in production
* Secure endpoints with authentication
* Use role-based access
* Separate actuator port if possible
* Mask sensitive data
* Monitor metrics externally

---

## 23. Interview-Oriented Summary

* Actuator provides **production-ready monitoring**
* Endpoints expose runtime information
* Health and metrics are most used
* Powered by Micrometer
* Must always be secured
* Essential for cloud-native apps

---
