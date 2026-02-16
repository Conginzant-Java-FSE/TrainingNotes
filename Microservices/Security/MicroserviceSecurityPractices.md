# API Security in Spring Boot Microservices


# 1. Security in Microservices – Big Picture

In microservices, security must be layered:

* API Gateway layer
* Resource Server layer
* Network layer
* Infrastructure layer
* Secrets & configuration layer

---

## Microservices Security Layers

![Image](https://cheatsheetseries.owasp.org/assets/Netflix_AC.png)

![Image](https://docs.aws.amazon.com/images/whitepapers/latest/security-overview-amazon-api-gateway/images/holistic-security-layers.png)

![Image](https://miro.medium.com/1%2AZa9SqezEnyKIzYk_WheJWw.png)

![Image](https://substackcdn.com/image/fetch/%24s_%21hnmN%21%2Cf_auto%2Cq_auto%3Agood%2Cfl_progressive%3Asteep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F610c2bdd-9f59-4239-85cf-c4aa40434c46_1200x1487.png)

Security is not a single feature — it is a combination of:

* CORS control
* CSRF protection
* Rate limiting
* Token validation
* Secrets management
* HTTPS
* Input validation
* Logging & monitoring

---

# 2. CORS (Cross-Origin Resource Sharing)

## What is CORS?

CORS allows or blocks cross-domain browser requests.

Example:

Frontend → `http://localhost:3000`
Backend → `http://localhost:8080`

Without CORS configuration → browser blocks request.

Important:

* CORS is a browser security feature.
* It does NOT protect APIs from server-to-server attacks.

---

## CORS Flow

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AzCXcC1VkBB16BDXUxkWoew.png)

![Image](https://cdn.hashnode.com/res/hashnode/image/upload/v1599591569159/Ylc2deIBk.png)

![Image](https://i.sstatic.net/6jsKY.png)

![Image](https://docs.aws.amazon.com/images/sdk-for-javascript/v2/developer-guide/images/cors-overview.png)

1. Browser sends `OPTIONS` preflight
2. Server responds with allowed origins/methods
3. Browser decides whether to proceed

---

## CORS Configuration in Spring Boot (Global)

```java
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
```

Enable in security:

```java
http.cors(Customizer.withDefaults());
```

---

## Best Practices

* Never use `"*"` in production
* Restrict origins explicitly
* Restrict allowed headers
* Enable CORS only at API Gateway ideally

---

# 3. CSRF (Cross-Site Request Forgery)

## What is CSRF?

CSRF attacks trick a logged-in user’s browser into performing unwanted actions.

Example:
User logged into bank → malicious site submits transfer request.

---

## CSRF Attack Flow

![Image](https://supertokens.com/static/921e703a29cdd46983749a195f4a811e/fe238/csrf-diagram.png)

![Image](https://cdn.prod.website-files.com/5ff66329429d880392f6cba2/67b43187492a7a4167d618cb_61f251ef69dd0a65b7b4d726_how%2520csrf%2520work.jpeg)

![Image](https://cdn.prod.website-files.com/5ff66329429d880392f6cba2/6447a042dbde2b3f1abe8855_647%20Preview.jpg)

![Image](https://terasolunaorg.github.io/guideline/5.3.0.RELEASE/en/_images/Csrf.png)

---

## Important Concept

CSRF is relevant only when:

* Using session-based authentication
* Using cookies automatically sent by browser

If using JWT in Authorization header:

```
Authorization: Bearer eyJhbGci...
```

CSRF is NOT required.

---

## Disable CSRF for JWT APIs

```java
http.csrf(csrf -> csrf.disable());
```

---

## Enable CSRF for Form Login Apps

```java
http.csrf(Customizer.withDefaults());
```

Spring automatically generates CSRF tokens.

---

# 4. DDoS Protection in Microservices

## What is DDoS?

Distributed Denial of Service floods server with requests.

Goal:

* Exhaust CPU
* Exhaust threads
* Exhaust memory
* Make service unavailable

---

## DDoS Protection Architecture

![Image](https://developers.cloudflare.com/_astro/ddos-diagram.DygBAs9m_Z1krDYl.webp)

![Image](https://miro.medium.com/1%2A3DUoq3XoknSKms9fbZJXLQ.gif)

![Image](https://bytebytego.com/images/courses/system-design-interview/design-a-rate-limiter/figure-4-13-G2VF2RCQ.png)

![Image](https://learn.microsoft.com/en-us/azure/architecture/patterns/_images/rate-limiting-pattern-04.png)

---

## Protection Layers

### 1. Cloud Layer

* AWS Shield
* Azure DDoS Protection
* Cloudflare

### 2. API Gateway Rate Limiting

Using Spring Cloud Gateway + Redis:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://USER-SERVICE
          predicates:
            - Path=/users/**
          filters:
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 10
                redis-rate-limiter.burstCapacity: 20
```

This allows:

* 10 requests per second
* Burst of 20

---

### 3. Resilience4j Rate Limiter (Service-Level)

```java
@RateLimiter(name = "userServiceLimiter")
public String getUser() {
    return "OK";
}
```

---

### 4. Thread Pool Protection

Configure:

```yaml
server:
  tomcat:
    max-threads: 200
```

---

# 5. Secrets Management

Hardcoding secrets is a critical security vulnerability.

Example (WRONG):

```yaml
spring:
  datasource:
    password: root123
```

---

## Secrets Management Architecture

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2A_pGqB2PTGD4cEtBNFwIJbQ.png)

![Image](https://www.opcito.com/sites/default/files/2023-05/Configuration-management-using-HashiCorp-Vault.jpg)

![Image](https://www.datadisk.org.uk/images/spring_cloud/spring_cloud_42.jpg)

![Image](https://piotrminkowski.files.wordpress.com/2019/12/secure-spring-cloud-config-architecture.png?resize=623%2C437)

---

## Secure Approaches

### 1. Environment Variables

```
export DB_PASSWORD=StrongPassword
```

```yaml
spring:
  datasource:
    password: ${DB_PASSWORD}
```

---

### 2. Spring Cloud Config + Encrypted Properties

Encrypt:

```
{cipher}AHSJHSAJKHSKJHSKJH
```

Enable encryption key in Config Server.

---

### 3. HashiCorp Vault Integration

Dependency:

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-vault-config</artifactId>
</dependency>
```

---

### 4. Kubernetes Secrets

```yaml
env:
  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: db-secret
        key: password
```

---

## Best Practices

* Rotate secrets periodically
* Never commit secrets to Git
* Use vault in production
* Restrict secret access via IAM

---

# 6. API Security Best Practices

---

## 6.1 HTTPS Enforcement

```yaml
server:
  ssl:
    enabled: true
```

Or enforce at load balancer level.

---

## 6.2 Input Validation

```java
@PostMapping("/users")
public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO dto)
```

DTO:

```java
@NotBlank
@Email
private String email;
```

---

## 6.3 Global Exception Handling

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex) {
        return ResponseEntity.status(500).body("Error");
    }
}
```

Avoid exposing stack traces.

---

## 6.4 Security Headers

```java
http.headers(headers -> headers
    .contentSecurityPolicy(csp -> 
        csp.policyDirectives("default-src 'self'"))
);
```

---

## 6.5 OAuth2 + JWT

* Stateless authentication
* Short token expiry
* Refresh tokens
* Scope-based authorization

---

## 6.6 Logging & Monitoring

Enable:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,metrics
```

Monitor suspicious activity.

---

# 7. Secure Microservices Reference Architecture

![Image](https://cheatsheetseries.owasp.org/assets/Netflix_AC.png)

![Image](https://media.licdn.com/dms/image/v2/D4D12AQH6WhKE_oaSYw/article-cover_image-shrink_423_752/B4DZZ4N43OG4AY-/0/1745773643212?e=2147483647\&t=glz0vZ31cPv4ZcF5yWFiZh9aW7n-RZnLMVyhsFN_FR4\&v=beta)

![Image](https://substackcdn.com/image/fetch/%24s_%21hnmN%21%2Cf_auto%2Cq_auto%3Agood%2Cfl_progressive%3Asteep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F610c2bdd-9f59-4239-85cf-c4aa40434c46_1200x1487.png)

![Image](https://www.researchgate.net/publication/385686967/figure/fig1/AS%3A11431281289420869%401731185740700/Spring-Boot-Microservice-Architecture.ppm)

---

# 8. Summary Table

| Concern       | Solution                         |
| ------------- | -------------------------------- |
| CORS          | Restrict allowed origins         |
| CSRF          | Disable for JWT APIs             |
| DDoS          | Rate limiting + Cloud protection |
| Secrets       | Vault / Env variables            |
| API Auth      | OAuth2 + JWT                     |
| Encryption    | HTTPS                            |
| Input Attacks | Validation + Sanitization        |
| Exposure      | Global exception handling        |

---

# 9. Production Checklist

* HTTPS enforced
* JWT validation enabled
* Rate limiting configured
* Secrets externalized
* CORS restricted
* CSRF configured appropriately
* Logs monitored
* Dependency vulnerabilities scanned

---
