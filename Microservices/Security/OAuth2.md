# OAuth2

# 1. OAuth2 Architecture in Microservices

In a secure microservices ecosystem, authentication and authorization are separated:

* **Authorization Server** → Issues access tokens (JWT)
* **Resource Server** → Validates tokens and protects APIs
* **API Gateway** → Routes traffic and optionally validates tokens
* **Client (Postman/Web/App)** → Requests token and calls APIs

---

## High-Level Architecture

![Image](https://miro.medium.com/1%2AeaEjaer6x8QeWBmmRFqA_A.png)

![Image](https://www.marcobehler.com/images/guides/oauth2/oauth2_flow_v2-8c394ca4.png)

![Image](https://miro.medium.com/1%2AmewM0i0vCUTgCaM2Gqcnwg.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AEaJSbegCk8dPzChHVomQlw.jpeg)

### Flow

1. Client sends credentials to **Authorization Server**
2. Authorization Server validates and issues **JWT Access Token**
3. Client sends token in `Authorization: Bearer <token>` header
4. API Gateway forwards request
5. Resource Server validates JWT signature and claims
6. If valid → Access granted

---

# 2. Core Components

| Component            | Responsibility                     |
| -------------------- | ---------------------------------- |
| Authorization Server | Authenticates users, issues tokens |
| Resource Server      | Protects APIs and validates JWT    |
| API Gateway          | Central entry point                |
| Eureka               | Service discovery                  |
| Config Server        | Central configuration              |

---

# 3. Authorization Server Setup

We will use:

**Spring Authorization Server**

Dependency:

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-authorization-server</artifactId>
    <version>1.2.1</version>
</dependency>
```

---

## 3.1 Authorization Server Configuration

### Security Configuration

```java
@Configuration
@EnableWebSecurity
public class AuthServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("microservice-client")
                .clientSecret("{noop}secret")
                .scope("read")
                .scope("write")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();

        return new InMemoryRegisteredClientRepository(client);
    }
}
```

---

## 3.2 application.yml

```yaml
server:
  port: 9000

spring:
  application:
    name: auth-server
```

---

## 3.3 Getting Access Token

Request:

```
POST http://localhost:9000/oauth2/token
```

Body (x-www-form-urlencoded):

```
grant_type=client_credentials
client_id=microservice-client
client_secret=secret
```

Response:

```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "token_type": "Bearer",
  "expires_in": 300
}
```

---

# 4. Resource Server (User Service)

Your `user-service` POM already contains:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Add:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

---

## 4.1 Resource Server Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/public/**").permitAll()
                    .requestMatchers("/users/**").hasAuthority("SCOPE_read")
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(Customizer.withDefaults())
            );

        return http.build();
    }
}
```

---

## 4.2 application.yml

```yaml
server:
  port: 8070

spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:9000
```

This automatically fetches:

```
http://localhost:9000/.well-known/openid-configuration
```

And obtains public key for JWT validation.

---

# 5. API Gateway Configuration

Add dependency:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

---

## Gateway application.yml

```yaml
server:
  port: 8080

spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://USER-SERVICE
          predicates:
            - Path=/users/**
```

Optionally enable JWT validation in gateway as well.

---

# 6. End-to-End Flow

## Step 1: Get Token

```
POST http://localhost:9000/oauth2/token
```

## Step 2: Call API via Gateway

```
GET http://localhost:8080/users/profile
```

Header:

```
Authorization: Bearer eyJhbGciOi...
```

---

# 7. Token Validation Internals

When request hits Resource Server:

1. Extracts JWT
2. Validates signature using public key
3. Validates expiration
4. Extracts scopes
5. Converts scopes to authorities:

   ```
   SCOPE_read
   SCOPE_write
   ```

---

# 8. JWT Structure

![Image](https://fusionauth.io/img/shared/json-web-token.png)

![Image](https://media2.dev.to/dynamic/image/width%3D1600%2Cheight%3D900%2Cfit%3Dcover%2Cgravity%3Dauto%2Cformat%3Dauto/https%3A%2F%2Fdev-to-uploads.s3.amazonaws.com%2Fuploads%2Farticles%2F8wiw2dbjerzq6br66qv8.png)

![Image](https://substackcdn.com/image/fetch/%24s_%21aYnF%21%2Cf_auto%2Cq_auto%3Agood%2Cfl_progressive%3Asteep/https%3A%2F%2Fbucketeer-e05bbc84-baa3-437e-9518-adb32be77984.s3.amazonaws.com%2Fpublic%2Fimages%2Fc909ca73-eb36-4681-b3f1-c534f041f566_752x501.png)

![Image](https://i.sstatic.net/E2O9t.png)

A JWT has:

```
HEADER.PAYLOAD.SIGNATURE
```

Example payload:

```json
{
  "sub": "microservice-client",
  "scope": "read write",
  "iss": "http://localhost:9000",
  "exp": 1700000000
}
```

---

# 9. Method-Level Authorization

Enable:

```java
@EnableMethodSecurity
```

Use:

```java
@PreAuthorize("hasAuthority('SCOPE_read')")
@GetMapping("/users")
public List<User> getUsers() {
    return service.findAll();
}
```

---

# 10. Production Best Practices

### 1. Use RSA keys (not symmetric keys)

### 2. Use Refresh Tokens

### 3. Store RegisteredClient in DB

### 4. Use HTTPS

### 5. Configure token expiry carefully

### 6. Use API Gateway token relay

### 7. Integrate with Keycloak if enterprise scale

---

# 11. OAuth2 Grant Types (Modern)

| Grant Type         | Use Case              |
| ------------------ | --------------------- |
| Client Credentials | Service-to-service    |
| Authorization Code | User login (web apps) |
| Refresh Token      | Renew access token    |

Password grant is deprecated.

---

# 12. Comparison: Authorization Server vs Resource Server

| Feature          | Authorization Server | Resource Server |
| ---------------- | -------------------- | --------------- |
| Issues tokens    | Yes                  | No              |
| Validates tokens | No                   | Yes             |
| Stores clients   | Yes                  | No              |
| Protects APIs    | No                   | Yes             |

---

# 13. Microservices Security Flow (Final View)

![Image](https://edwardthienhoang.files.wordpress.com/2018/08/0.jpg)

![Image](https://docs.oracle.com/cd/E55956_01/doc.11123/oauth_guide/content/images/oauth/oauth_overview.png)

![Image](https://media2.dev.to/dynamic/image/width%3D1000%2Cheight%3D420%2Cfit%3Dcover%2Cgravity%3Dauto%2Cformat%3Dauto/https%3A%2F%2Fdev-to-uploads.s3.amazonaws.com%2Fuploads%2Farticles%2Fv6ifxnrzf0wl9hmx7um5.png)

![Image](https://dz2cdn1.dzone.com/storage/temp/16172727-figure-1-1.png)

---

# 14. How This Fits Your Current Setup

Your `user-service`:

* Already uses Spring Security
* Uses Eureka
* Uses Config Server
* Has JWT dependency

Recommended:

* Move JWT generation to Authorization Server
* Convert user-service to pure Resource Server
* Let API Gateway handle routing
* Remove custom JWT logic if using standard OAuth2

---

# 15. Summary

In a secure microservices architecture:

* Authorization Server handles authentication
* Resource Servers validate JWT
* API Gateway routes traffic
* JWT enables stateless security
* Scopes control access

This model provides:

* Centralized security
* Scalability
* Loose coupling
* Industry-standard compliance

---
