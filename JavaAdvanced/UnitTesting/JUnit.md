# JUnit 5 

## 1. What is JUnit?

**JUnit** is a Java unit testing framework used to:

* Validate application logic automatically
* Catch bugs early in development
* Enable continuous integration (CI/CD)
* Improve maintainability and refactoring safety

JUnit follows the principle of **test-first development (TDD)** but is equally useful in traditional workflows.

---

## 2. Why JUnit is Important in Enterprise Projects

* Mandatory in most Java projects
* Integrates with Maven, Gradle, Jenkins, GitHub Actions
* Supported by Spring Boot, Quarkus, Micronaut
* Required knowledge for Java interviews

---

## 3. JUnit Versions Overview

| Version     | Notes                         |
| ----------- | ----------------------------- |
| JUnit 3     | XML-based, obsolete           |
| JUnit 4     | Annotation-based, widely used |
| **JUnit 5** | Modular, modern, extensible   |

### JUnit 5 Architecture

* **JUnit Platform** – Launches tests
* **JUnit Jupiter** – Writing tests (annotations + assertions)
* **JUnit Vintage** – Backward compatibility (JUnit 3/4)

---

## 4. Maven Project Setup

### 4.1 Standard Maven Structure

```text
project-root
 ├── src
 │   ├── main/java   (application code)
 │   └── test/java   (test code)
 └── pom.xml
```

---

### 4.2 pom.xml Configuration

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.2.5</version>
        </plugin>
    </plugins>
</build>
```

### Why Surefire Plugin?

* Discovers and runs tests
* Generates test reports
* Integrates with CI tools

---

## 5. Writing Your First JUnit Test

### Production Code

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

### Test Class

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void add_twoNumbers_returnsSum() {
        Calculator calc = new Calculator();
        int result = calc.add(5, 10);
        assertEquals(15, result);
    }
}
```

### Key Points

* Tests live under `src/test/java`
* Test methods must be `public` or package-private
* `@Test` marks executable test methods

---

## 6. JUnit Annotations (Detailed)

| Annotation    | Purpose                    |
| ------------- | -------------------------- |
| `@Test`       | Marks a test method        |
| `@BeforeEach` | Runs before each test      |
| `@AfterEach`  | Runs after each test       |
| `@BeforeAll`  | Runs once before all tests |
| `@AfterAll`   | Runs once after all tests  |
| `@Disabled`   | Skips execution            |

---

## 7. Test Lifecycle in Detail

```java
import org.junit.jupiter.api.*;

class LifecycleTest {

    @BeforeAll
    static void initAll() {
        System.out.println("Before all tests");
    }

    @BeforeEach
    void init() {
        System.out.println("Before each test");
    }

    @Test
    void testA() {
        System.out.println("Executing test A");
    }

    @Test
    void testB() {
        System.out.println("Executing test B");
    }

    @AfterEach
    void cleanup() {
        System.out.println("After each test");
    }

    @AfterAll
    static void cleanupAll() {
        System.out.println("After all tests");
    }
}
```

### Execution Order

1. `@BeforeAll`
2. `@BeforeEach`
3. `@Test`
4. `@AfterEach`
5. `@AfterAll`

---

## 8. Assertions in JUnit (In-depth)

### Common Assertions

```java
assertEquals(expected, actual);
assertNotEquals(a, b);
assertTrue(condition);
assertFalse(condition);
assertNull(obj);
assertNotNull(obj);
```

### Assertion with Message

```java
assertEquals(10, result, "Result should be 10");
```

### Grouped Assertions

```java
assertAll(
    () -> assertEquals(10, result),
    () -> assertTrue(result > 0),
    () -> assertNotNull(result)
);
```

### Why Assertions Matter

* Fail fast
* Provide readable failure reasons
* Improve debugging speed

---

## 9. Exception Handling Tests (Detailed)

### Production Code

```java
public int divide(int a, int b) {
    if (b == 0) {
        throw new IllegalArgumentException("Division by zero");
    }
    return a / b;
}
```

### Test Case

```java
@Test
void divide_byZero_throwsException() {
    Calculator calc = new Calculator();

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class,
            () -> calc.divide(10, 0));

    assertEquals("Division by zero", exception.getMessage());
}
```

### Best Practice

* Always verify exception type
* Validate exception message when relevant

---

## 10. Timeout Testing (Performance Safety)

```java
import java.time.Duration;

@Test
void operation_completesWithinTime() {
    assertTimeout(Duration.ofMillis(100), () -> {
        Thread.sleep(50);
    });
}
```

### Use Cases

* Detect infinite loops
* Validate performance constraints
* Avoid hanging test suites

---

## 11. Disabling Tests

```java
@Disabled("Blocked due to pending requirement")
@Test
void temporarilyDisabledTest() {
    fail("This test should not run");
}
```

### When to Use

* Known bugs
* Incomplete features
* External dependency issues

---

## 12. Running Tests Using Maven

### Run All Tests

```bash
mvn test
```

### Clean and Test

```bash
mvn clean test
```

### Run Single Test

```bash
mvn -Dtest=CalculatorTest test
```

---

## 13. Test Naming Conventions

Good test names describe **behavior**:

```text
methodName_condition_expectedResult
```

Example:

```java
withdraw_insufficientBalance_throwsException()
```

---

## 14. Unit Testing Best Practices (Expanded)

* One test = one behavior
* Tests must be deterministic
* No DB, file system, or network access
* Avoid shared state between tests
* Keep tests fast (<100ms)
* Prefer readability over cleverness

---

## 15. JUnit and CI/CD

* Maven Surefire generates reports
* Jenkins reads test results automatically
* Failed tests block deployment
* Essential for DevOps pipelines

---

## 16. Common Interview Questions

* Why is `@BeforeAll` static?
* Difference between `@BeforeEach` and `@BeforeAll`
* How does Maven discover tests?
* What happens when an assertion fails?
* JUnit 4 vs JUnit 5 differences

---

## 17. Summary

* JUnit 5 is the modern Java testing standard
* Maven simplifies setup and execution
* Strong lifecycle management and assertions
* Essential skill for Java developers and trainers

---
