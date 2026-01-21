# Mockito Mock Testing 

---

## 1. Introduction to Mockito

**Mockito** is a popular **Java mocking framework** used for **unit testing**. It allows developers to create **mock objects** for dependencies so that a class can be tested in isolation.

Mockito is commonly used with:

* JUnit 4
* JUnit 5 (Jupiter)

---

## 2. Why Mockito Is Needed

In real-world applications, classes often depend on:

* Databases
* External REST APIs
* Message queues
* File systems

Unit testing should focus on testing **one class at a time**. Mockito helps by replacing real dependencies with **mock objects** that:

* Do not execute real logic
* Return controlled responses
* Improve test speed and reliability

---

## 3. What Is Mocking?

**Mocking** is the process of creating a fake object that simulates the behavior of a real dependency.

### Example Dependency Flow

```
UserService → UserRepository → Database
```

When unit testing `UserService`, `UserRepository` is mocked so that no real database call occurs.

---

## 4. Mockito vs Real Objects

| Aspect          | Real Object | Mockito Mock  |
| --------------- | ----------- | ------------- |
| Logic execution | Real logic  | No real logic |
| External calls  | Yes         | No            |
| Speed           | Slower      | Faster        |
| Control         | Limited     | Full control  |

---

## 5. Maven Dependencies

### Mockito with JUnit 5

```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.8.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.8.0</version>
    <scope>test</scope>
</dependency>
```

---

## 6. Sample Business Classes

### User Model

```java
public class User {
    private int id;
    private String name;

    // constructors, getters, setters
}
```

### UserRepository

```java
public interface UserRepository {
    User findById(int id);
}
```

### UserService

```java
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String getUserName(int id) {
        User user = repository.findById(id);
        return user.getName();
    }
}
```

---

## 7. Basic Mockito Test Without Annotations

```java
@Test
void testGetUserName() {

    UserRepository repository = Mockito.mock(UserRepository.class);

    Mockito.when(repository.findById(1))
           .thenReturn(new User(1, "Arun"));

    UserService service = new UserService(repository);

    assertEquals("Arun", service.getUserName(1));
}
```

---

## 8. Mockito Annotations

### Using @Mock and @InjectMocks

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    void testGetUserName() {
        Mockito.when(repository.findById(1))
               .thenReturn(new User(1, "Arun"));

        assertEquals("Arun", service.getUserName(1));
    }
}
```

### Explanation

* `@Mock` creates a mock object
* `@InjectMocks` injects mocks via constructor, setter, or field injection
* `@ExtendWith(MockitoExtension.class)` initializes Mockito annotations

---

## 9. Stubbing Methods

### Returning Values

```java
when(repository.findById(1)).thenReturn(user);
```

### Multiple Return Values

```java
when(repository.findById(1))
    .thenReturn(user1)
    .thenReturn(user2);
```

### Throwing Exceptions

```java
when(repository.findById(99))
    .thenThrow(new RuntimeException("User not found"));
```

---

## 10. Verifying Interactions

Mockito allows verification of method calls.

```java
verify(repository).findById(1);
```

### Verify Call Count

```java
verify(repository, times(1)).findById(1);
verify(repository, never()).findById(2);
```

### Verify No Extra Interactions

```java
verifyNoMoreInteractions(repository);
```

---

## 11. Argument Matchers

Mockito provides argument matchers to generalize method inputs.

```java
when(repository.findById(anyInt()))
    .thenReturn(user);
```

### Common Matchers

| Matcher     | Description         |
| ----------- | ------------------- |
| any()       | Matches any object  |
| anyInt()    | Matches any integer |
| anyString() | Matches any string  |
| eq(value)   | Matches exact value |

**Important Rule**: Either all arguments use matchers or none.

---

## 12. doReturn vs when

`doReturn()` is preferred when:

* Working with spies
* Mocking void methods

```java
doReturn(user).when(repository).findById(1);
```

---

## 13. Mocking Void Methods

```java
doNothing().when(repository).deleteById(1);
```

### Throwing Exception from Void Method

```java
doThrow(new RuntimeException())
    .when(repository).deleteById(1);
```

---

## 14. Mockito Spy

A **spy** is a partial mock that calls real methods unless stubbed.

### Example

```java
List<String> list = new ArrayList<>();
List<String> spyList = Mockito.spy(list);

spyList.add("A");
spyList.add("B");

doReturn(10).when(spyList).size();
```

---

## 15. Mock vs Spy Comparison

| Feature               | Mock                | Spy             |
| --------------------- | ------------------- | --------------- |
| Real method execution | No                  | Yes             |
| Default behavior      | Null / zero         | Real values     |
| Use case              | External dependency | Partial testing |

---

## 16. Exception Testing with Mockito

```java
assertThrows(RuntimeException.class, () -> {
    service.getUserName(99);
});
```

---

## 17. Resetting Mocks

```java
@BeforeEach
void setup() {
    Mockito.reset(repository);
}
```

---

## 18. Best Practices

* Mock only external dependencies
* Avoid mocking DTOs and value objects
* Write behavior-focused tests
* Prefer mocks over spies

---

## 19. Common Mistakes

* Missing `@ExtendWith(MockitoExtension.class)`
* Mixing matchers and real values
* Using `when()` instead of `doReturn()` with spies
* Mocking internal methods of the same class

---

## 20. Mockito Limitations

* Cannot mock private methods
* Cannot mock constructors
* Static methods require `mockito-inline`

---

## 21. Mockito vs Stub vs Fake

| Type | Description                        |
| ---- | ---------------------------------- |
| Stub | Returns predefined data            |
| Mock | Verifies interactions              |
| Fake | Lightweight working implementation |

Mockito supports both stubbing and mocking.

---

## 22. Where Mockito Should Not Be Used

* Integration testing
* End-to-end testing
* Database and API behavior validation

Use **Testcontainers** or real environments for such cases.

---

## 23. Typical Usage in Layered Architecture

```
Controller Test → Mock Service
Service Test → Mock Repository
```

---

## 24. Summary

Mockito enables isolated unit testing by mocking dependencies.
It improves test reliability, speed, and maintainability when used correctly and within its scope.
