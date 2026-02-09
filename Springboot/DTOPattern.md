# Spring Boot DTO Pattern – Detailed Tutoria

## 1. What is a DTO (Data Transfer Object)?

A **DTO** is a simple Java class used **only to carry data between layers** (Controller ↔ Service ↔ Client).

Key idea:
**DTO ≠ Entity**

| Entity                       | DTO                         |
| ---------------------------- | --------------------------- |
| Mapped to DB table           | Not mapped to DB            |
| Contains JPA annotations     | No JPA annotations          |
| Represents persistence model | Represents API contract     |
| May contain lazy relations   | Flat / controlled structure |
| Not safe to expose directly  | Safe to expose              |

---

## 2. Why DTO Pattern is IMPORTANT in Spring Boot

###  Problem if you expose Entity directly

```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userRepository.findById(id).get();
}
```

### Issues:

1. **Security risk** – exposes internal fields (passwords, audit fields)
2. **LazyInitializationException**
3. **Tight coupling** between DB schema & API
4. **Breaking changes** when entity changes
5. Poor control over response shape

---

## 3. Typical Spring Boot Layered Architecture (with DTO)

```
Controller  →  DTO
Service     →  Business Logic
Repository  →  Entity
Database
```

DTO is used:

* **Request DTO** → incoming API data
* **Response DTO** → outgoing API data

---

## 4. Entity vs DTO – Real Example

### Entity (Persistence Model)

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
```

### Why this is BAD to expose?

* `password` exposed
* `orders` may cause lazy loading issues
* Circular references

---

## 5. Creating DTOs (Best Practice)

### Request DTO

```java
public class UserRequestDto {

    private String name;
    private String email;
    private String password;
}
```

### Response DTO

```java
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
}
```

No password
No JPA annotations
Clean API contract

---

## 6. Controller Using DTOs (Correct Way)

```java
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
```

Controller **never deals with Entity**

---

## 7. Service Layer – DTO ↔ Entity Mapping

### Manual Mapping (Beginner-friendly)

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        User saved = userRepository.save(user);

        UserResponseDto response = new UserResponseDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());

        return response;
    }
}
```


---

## 8. Using Mapper Layer (Best Practice)

### Why Mapper Layer?

* Keeps service clean
* Centralizes mapping logic
* Easier to maintain

```
Controller → DTO
Service → Mapper
Mapper → Entity
```

---

## 9. Manual Mapper Example

```java
@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
```

### Service becomes clean

```java
public UserResponseDto createUser(UserRequestDto dto) {
    User user = mapper.toEntity(dto);
    User saved = userRepository.save(user);
    return mapper.toDto(saved);
}
```

---

## 10. DTO for Nested / Complex Responses

### Entity

```java
class Order {
    Long id;
    String product;
    Double price;
}
```

### Order DTO

```java
public class OrderDto {
    private Long id;
    private String product;
    private Double price;
}
```

### User Response DTO

```java
public class UserResponseDto {
    private Long id;
    private String name;
    private List<OrderDto> orders;
}
```

### Mapping Nested Objects

```java
dto.setOrders(
    user.getOrders()
        .stream()
        .map(order -> new OrderDto(
            order.getId(),
            order.getProduct(),
            order.getPrice()
        ))
        .toList()
);
```

---

## 11. Validation with DTO (Very Important)

### Request DTO with Validation

```java
public class UserRequestDto {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    private String password;
}
```

### Controller

```java
@PostMapping
public UserResponseDto createUser(
        @Valid @RequestBody UserRequestDto dto) {
    return userService.createUser(dto);
}
```

✔ Validation belongs in DTO
✔ Entity stays clean

---

## 12. DTOs for Different Use Cases

### Multiple DTOs for same Entity

✔ This is **GOOD design**

```text
UserCreateRequestDto
UserUpdateRequestDto
UserResponseDto
UserSummaryDto
```

Example:

* `UserSummaryDto` → list page
* `UserResponseDto` → detailed view

---

## 13. DTO vs Projection vs Record

### DTO

* Full control
* Best for APIs

### JPA Projection

* Read-only
* DB-level optimization

### Java Record DTO (Spring Boot 3+)

```java
public record UserResponseDto(Long id, String name, String email) {}
```

✔ Immutable
✔ Less boilerplate
 Not ideal for complex mapping logic

---

## 14. Common DTO Anti-Patterns 

1. Exposing Entity directly
2. Adding business logic in DTO
3. Using same DTO for request & response
4. Putting JPA annotations in DTO
5. Massive “God DTO” with 20+ fields

---

## 15. Interview Questions 

**Q: Why not return Entity directly?**
A: Security, lazy loading, tight coupling, poor API control.

**Q: Where should validation go?**
A: Request DTO.

**Q: Can one entity have multiple DTOs?**
A: Yes, and it should.

**Q: DTO vs Entity?**
A: Entity = persistence model, DTO = API contract.

---

## 16. Recommended DTO Pattern (Industry Standard)

```
Controller → Request DTO
Service → Entity
Mapper → DTO ↔ Entity
Controller → Response DTO
```
