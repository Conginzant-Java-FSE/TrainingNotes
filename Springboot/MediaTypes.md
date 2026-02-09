# Spring Boot Content Negotiation and Media Types

## 1. What is Content Negotiation?

**Content Negotiation** is the mechanism by which a client and server agree on:

* **Response format** (JSON, XML, etc.)
* **Request format** (JSON, XML, form data, etc.)

This agreement is based on **media types** sent in HTTP headers.

### Key Headers Involved

* `Accept` → what response formats the client can accept
* `Content-Type` → format of the request body sent by the client

---

## 2. What Are Media Types?

A **Media Type (MIME type)** identifies the format of data exchanged between client and server.

### Common Media Types

| Media Type            | Description  |
| --------------------- | ------------ |
| `application/json`    | JSON data    |
| `application/xml`     | XML data     |
| `text/plain`          | Plain text   |
| `application/pdf`     | PDF file     |
| `multipart/form-data` | File uploads |

### Media Type Structure

```
type/subtype
```

Example:

```
application/json
application/vnd.company.v1+json
```

---

## 3. Why Content Negotiation Is Important

* Enables multiple response formats for the same API
* Supports backward compatibility
* Improves API flexibility
* Avoids URL-based versioning
* Aligns with REST standards

---

## 4. Default Behavior in Spring Boot

Spring Boot uses:

* **Jackson** for JSON
* **Jackson XML** (if dependency added) for XML
* `Accept` header for response negotiation

Default:

```http
Accept: application/json
```

Spring Boot automatically selects the best `HttpMessageConverter`.

---

## 5. Dependencies

### JSON (Default)

Already included:

```xml
spring-boot-starter-web
```

### XML Support (Optional)

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

---

## 6. Simple REST Controller

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return new User(1L, "Arun", "arun@test.com");
    }
}
```

### Entity

```java
public class User {
    private Long id;
    private String name;
    private String email;
}
```

---

## 7. Response Based on Accept Header

### Request (JSON)

```http
GET /users/1
Accept: application/json
```

### Response

```json
{
  "id": 1,
  "name": "Arun",
  "email": "arun@test.com"
}
```

### Request (XML)

```http
GET /users/1
Accept: application/xml
```

### Response

```xml
<User>
    <id>1</id>
    <name>Arun</name>
    <email>arun@test.com</email>
</User>
```

Spring Boot automatically performs content negotiation.

---

## 8. Using `produces` Attribute

```java
@GetMapping(
    value = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public User getUserJson(@PathVariable Long id) {
    return new User(1L, "Arun", "arun@test.com");
}
```

### Multiple Media Types

```java
@GetMapping(
    value = "/{id}",
    produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
    }
)
```

---

## 9. Consuming Different Request Formats

### Using `consumes`

```java
@PostMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE
)
public User createUser(@RequestBody User user) {
    return user;
}
```

### XML Request

```http
Content-Type: application/xml
```

---

## 10. `consumes` vs `produces`

| Attribute  | Purpose              |
| ---------- | -------------------- |
| `consumes` | Request body format  |
| `produces` | Response body format |

---

## 11. MediaType Constants

```java
MediaType.APPLICATION_JSON_VALUE
MediaType.APPLICATION_XML_VALUE
MediaType.TEXT_PLAIN_VALUE
```

Avoid hardcoding strings.

---

## 12. Content Negotiation Configuration

### Java Configuration

```java
@Configuration
public class ContentNegotiationConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {

        configurer
            .favorHeader(true)
            .favorPathExtension(false)
            .favorParameter(false)
            .defaultContentType(MediaType.APPLICATION_JSON);
    }
}
```

---

## 13. Path Extension Based Negotiation (Not Recommended)

```http
/users/1.json
/users/1.xml
```

Configuration:

```java
configurer.favorPathExtension(true);
```

Reason to avoid:

* Deprecated
* Security issues
* Breaks REST principles

---

## 14. Query Parameter Based Negotiation

```http
/users/1?format=json
```

Configuration:

```java
configurer.favorParameter(true)
         .parameterName("format");
```

Use only when headers cannot be controlled.

---

## 15. Custom Vendor Media Types (API Versioning)

```http
Accept: application/vnd.company.v1+json
```

### Controller

```java
@GetMapping(
    value = "/users/{id}",
    produces = "application/vnd.company.v1+json"
)
```

### Benefits

* Clean versioning
* No URL changes
* Strong REST compliance

---

## 16. Handling Unsupported Media Types

### 415 – Unsupported Media Type

```http
Content-Type: application/pdf
```

### 406 – Not Acceptable

```http
Accept: application/pdf
```

Spring Boot handles these automatically.

---

## 17. HttpMessageConverters (Internal Mechanism)

Spring uses:

* `MappingJackson2HttpMessageConverter` (JSON)
* `MappingJackson2XmlHttpMessageConverter` (XML)

They:

* Convert Java objects to response format
* Convert request body to Java objects

---

## 18. Content Negotiation with DTOs

Recommended approach:

```java
public class UserResponseDto {
    private Long id;
    private String name;
}
```

Controller returns DTO, not entity.

---

## 19. Common Mistakes

* Mixing `produces` and `consumes` incorrectly
* Hardcoding media type strings
* Using path extension negotiation
* Exposing entities directly

---

## 20. Interview Questions

**Q: Difference between Accept and Content-Type?**
Accept defines response format, Content-Type defines request format.

**Q: Which content negotiation strategy is preferred?**
Header-based using Accept.

**Q: Can Spring Boot support multiple formats automatically?**
Yes, based on available message converters.

---

## 21. Best Practices

1. Use header-based content negotiation
2. Always return DTOs
3. Use vendor media types for versioning
4. Keep default response as JSON
5. Avoid path extension negotiation

---

## 22. Summary

* Content negotiation allows flexible response formats
* Media types define data formats
* Spring Boot provides built-in support
* Proper configuration improves API design and evolution

---
