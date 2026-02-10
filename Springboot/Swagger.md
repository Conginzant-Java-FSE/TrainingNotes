# Swagger (OpenAPI) in Spring Boot 

---

## 1. What is Swagger?

**Swagger** is a set of tools built around the **OpenAPI Specification (OAS)** used to:

* Document REST APIs
* Visualize available endpoints
* Test APIs interactively
* Generate client/server code

In modern Spring Boot applications:

* **Swagger UI** is the UI
* **OpenAPI** is the specification
* **springdoc-openapi** is the integration library

---

## 2. Swagger vs OpenAPI

| Term              | Meaning                    |
| ----------------- | -------------------------- |
| Swagger           | Tooling ecosystem          |
| OpenAPI           | API specification standard |
| Swagger UI        | Web UI to visualize APIs   |
| springdoc-openapi | Spring Boot integration    |

**Swagger 2.x is deprecated**
**OpenAPI 3.x is the current standard**

---

## 3. Why Swagger is Important

Swagger solves real problems:

* API consumers don’t need Postman collections
* Frontend teams can explore APIs instantly
* QA teams can test without extra tools
* API contracts are clearly documented
* Reduces miscommunication

---

## 4. Spring Boot Swagger Options

###  Deprecated

* Springfox Swagger 2 / 3

###  Recommended

* **springdoc-openapi**

Reason:

* Actively maintained
* Native OpenAPI 3.x support
* Works with Spring Boot 3.x
* Supports Spring Security

---

## 5. Dependency Setup (Spring Boot 3.x)

### Maven Dependency

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

That’s it. No configuration needed to get started.

---

## 6. Default Swagger URLs

Once application starts:

* Swagger UI

```
http://localhost:8080/swagger-ui.html
```

* OpenAPI JSON

```
http://localhost:8080/v3/api-docs
```

* OpenAPI YAML

```
http://localhost:8080/v3/api-docs.yaml
```

---

## 7. How Swagger Works Internally

1. Spring scans controllers
2. springdoc generates OpenAPI spec
3. Spec is exposed as JSON/YAML
4. Swagger UI reads the spec
5. UI renders endpoints dynamically

No manual spec writing is required initially.

---

## 8. Basic REST Controller Example

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return new User(id, "Arun", "USER");
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return user;
    }
}
```

Swagger automatically documents:

* Paths
* HTTP methods
* Request bodies
* Response schemas

---

## 9. OpenAPI Annotations Overview

Springdoc uses **OpenAPI 3 annotations**.

### Common Annotations

| Annotation     | Purpose                  |
| -------------- | ------------------------ |
| `@Operation`   | Describe API             |
| `@ApiResponse` | Response documentation   |
| `@Parameter`   | Parameter metadata       |
| `@RequestBody` | Request body description |
| `@Schema`      | Model documentation      |
| `@Tag`         | Group APIs               |

---

## 10. Customizing API Documentation

### 10.1 @Operation

```java
@Operation(
    summary = "Get user by ID",
    description = "Fetch user details using user ID"
)
@GetMapping("/{id}")
public User getUser(@PathVariable Long id) {
    return new User(id, "Arun", "USER");
}
```

---

### 10.2 @ApiResponses

```java
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "User found"),
    @ApiResponse(responseCode = "404", description = "User not found")
})
```

---

## 11. Documenting Request Parameters

```java
@GetMapping
public List<User> getUsers(
    @Parameter(description = "Filter by role")
    @RequestParam String role) {
    return List.of();
}
```

---

## 12. Documenting Request Body

```java
@PostMapping
public User createUser(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "User object to create"
    )
    @RequestBody User user) {
    return user;
}
```

---

## 13. Documenting Model Objects

```java
@Schema(description = "User entity")
public class User {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "arun")
    private String username;

    @Schema(example = "ADMIN")
    private String role;
}
```

---

## 14. Grouping APIs using Tags

```java
@Tag(name = "User APIs", description = "Operations related to users")
@RestController
@RequestMapping("/api/users")
public class UserController {
}
```

Swagger UI shows APIs grouped logically.

---

## 15. Global OpenAPI Configuration

### Custom API Metadata

```java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .version("1.0")
                        .description("API documentation for User service"));
    }
}
```

---

## 16. Swagger with Spring Security

### Common Issue

Swagger UI endpoints get blocked by Spring Security.

### Solution

Allow these endpoints:

```java
.requestMatchers(
    "/swagger-ui/**",
    "/v3/api-docs/**"
).permitAll()
```

---

## 17. Swagger with JWT Authentication

### Configure Security Scheme

```java
@Bean
public OpenAPI secureOpenAPI() {
    return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                    .addSecuritySchemes("bearerAuth",
                            new SecurityScheme()
                                    .name("bearerAuth")
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")));
}
```

Swagger UI now shows **Authorize** button.

---

## 18. Testing APIs from Swagger UI

Swagger UI allows:

* Sending headers
* Sending request bodies
* JWT token authentication
* Viewing response codes

It acts like an interactive API client.

---

## 19. Disabling Swagger in Production

Recommended to disable in prod.

```properties
springdoc.api-docs.enabled=false
springdoc.swagger-ui.enabled=false
```

Enable only in lower environments.

---

## 20. Swagger vs Postman

| Swagger         | Postman           |
| --------------- | ----------------- |
| Auto-generated  | Manual            |
| API-first       | Client-first      |
| Contract driven | Collection driven |
| Best for docs   | Best for testing  |

They complement each other.

---

## 21. Common Mistakes

* Using Springfox with Boot 3.x
* Exposing Swagger in production
* Missing security config
* Over-documenting trivial APIs
* Ignoring versioning

---

## 22. Best Practices

* Use tags to group APIs
* Document error responses
* Keep examples realistic
* Version your APIs
* Disable Swagger in production
* Treat OpenAPI as API contract

---

## 23. Interview-Oriented Summary

* Swagger is based on OpenAPI
* springdoc-openapi is the recommended library
* Swagger UI reads OpenAPI spec
* Supports JWT and OAuth2
* Essential for API documentation
* Must be secured in production

---
