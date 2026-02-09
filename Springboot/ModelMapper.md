# Spring Boot ModelMapper

## 1. What is ModelMapper?

**ModelMapper** is a Java library that simplifies **object-to-object mapping**, typically used for converting:

* Entity → DTO
* DTO → Entity
* Request objects → Domain models

It automatically maps fields **by name and type**, reducing boilerplate code.

---

## 2. Why Use ModelMapper in Spring Boot?

### Without ModelMapper

```java
UserDto dto = new UserDto();
dto.setId(user.getId());
dto.setName(user.getName());
dto.setEmail(user.getEmail());
```

### With ModelMapper

```java
UserDto dto = modelMapper.map(user, UserDto.class);
```

### Benefits

* Reduces repetitive mapping code
* Improves readability
* Centralized mapping logic
* Easy to refactor
* Cleaner service layer

---

## 3. When Should You Use ModelMapper?

Recommended:

* Small to medium projects
* Simple DTO ↔ Entity mapping
* Rapid development

Avoid:

* Performance-critical systems
* Complex nested mapping logic
* Large enterprise systems (MapStruct preferred)

---

## 4. Project Structure (Recommended)

```
controller
dto
entity
repository
service
config
```

---

## 5. Dependency Configuration

### Maven

```xml
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>3.2.0</version>
</dependency>
```

---

## 6. ModelMapper Bean Configuration

```java
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
              .setMatchingStrategy(MatchingStrategies.STRICT)
              .setSkipNullEnabled(true);
        return mapper;
    }
}
```

### Configuration Explanation

* `STRICT` – prevents accidental mappings
* `skipNullEnabled` – useful for update operations

---

## 7. Sample Use Case

User Management API:

* Create User
* Get User
* Update User

---

## 8. Entity

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

    // getters and setters
}
```

---

## 9. DTOs

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

---

## 10. Repository

```java
public interface UserRepository extends JpaRepository<User, Long> {
}
```

---

## 11. Service Layer with ModelMapper

```java
@Service
public class UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto createUser(UserRequestDto requestDto) {

        User user = modelMapper.map(requestDto, User.class);
        User savedUser = repository.save(user);

        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    public UserResponseDto getUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserResponseDto.class);
    }
}
```

---

## 12. Controller

```java
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto dto) {
        return service.createUser(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return service.getUser(id);
    }
}
```

---

## 13. Handling Field Name Mismatch

### Entity

```java
private String email;
```

### DTO

```java
private String userEmail;
```

### Custom Mapping

```java
@Bean
public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();

    mapper.typeMap(User.class, UserResponseDto.class)
          .addMapping(User::getEmail, UserResponseDto::setUserEmail);

    return mapper;
}
```

---

## 14. Nested Object Mapping

### Entity

```java
class Order {
    Long id;
    Double amount;
}
```

### User Entity

```java
@OneToMany
private List<Order> orders;
```

### DTO

```java
class OrderDto {
    Long id;
    Double amount;
}
```

### Automatic Mapping

ModelMapper maps nested objects automatically if field names match.

---

## 15. Update (PATCH) Use Case

```java
public UserResponseDto updateUser(Long id, UserRequestDto dto) {

    User existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    modelMapper.map(dto, existing);

    User updated = repository.save(existing);
    return modelMapper.map(updated, UserResponseDto.class);
}
```

### Key Point

`setSkipNullEnabled(true)` prevents overwriting existing values with null.

---

## 16. Validation with DTOs

```java
public class UserRequestDto {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Size(min = 8)
    private String password;
}
```

Controller:

```java
public UserResponseDto createUser(@Valid @RequestBody UserRequestDto dto) {
    return service.createUser(dto);
}
```

---

## 17. ModelMapper vs Manual Mapping

| Aspect          | ModelMapper | Manual |
| --------------- | ----------- | ------ |
| Code size       | Low         | High   |
| Performance     | Medium      | High   |
| Debugging       | Harder      | Easy   |
| Complex mapping | Difficult   | Easy   |

---

## 18. Common Mistakes

* Using ModelMapper inside Controller
* Not setting matching strategy
* Using it for highly complex transformations
* Blindly mapping entities with lazy relations

---

## 19. ModelMapper vs MapStruct (High Level)

| Feature     | ModelMapper    | MapStruct          |
| ----------- | -------------- | ------------------ |
| Mapping     | Runtime        | Compile-time       |
| Performance | Slower         | Faster             |
| Debugging   | Hard           | Easy               |
| Boilerplate | Very low       | Medium             |
| Best for    | Small projects | Enterprise systems |

---

## 20. Best Practices

1. Use DTOs always
2. Configure ModelMapper centrally
3. Prefer STRICT matching
4. Avoid mapping large graphs
5. Use MapStruct for large systems

---

## 21. Interview Questions

**Q: Why ModelMapper is not preferred in large systems?**
A: Runtime reflection and performance overhead.

**Q: Can ModelMapper map nested objects?**
A: Yes, if field names match.

**Q: Where should ModelMapper be configured?**
A: In a dedicated `@Configuration` class.

---

## 22. Summary

* ModelMapper reduces boilerplate mapping code
* Best for simple DTO ↔ Entity conversion
* Easy to integrate with Spring Boot
* Not ideal for complex or high-performance systems

---
