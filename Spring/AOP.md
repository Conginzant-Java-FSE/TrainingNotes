# Spring AOP – Tutorial

---

## 1. Why AOP? (The Problem It Solves)

In real applications, some logic is **not part of business rules** but still needs to run **across many modules**:

| Cross-Cutting Concern | Examples                |
| --------------------- | ----------------------- |
| Logging               | Method entry/exit logs  |
| Security              | Authorization checks    |
| Transactions          | Commit / rollback       |
| Performance           | Execution time tracking |
| Auditing              | Who changed what        |

### Without AOP

* Code duplication
* Tight coupling
* Hard to maintain

### With AOP

* Concerns are **separated**
* Business logic stays clean
* Cross-cutting logic is centralized

---

## 2. What is AOP?

**Aspect-Oriented Programming (AOP)** is a programming paradigm that:

> Allows you to add behavior to existing code **without modifying the code itself**.

Spring AOP works using **proxies**.

---

## 3. Core AOP Terminology (Must-Know)

### 3.1 Aspect

A **module** that contains cross-cutting logic.

```java
@Aspect
@Component
public class LoggingAspect {
}
```

---

### 3.2 Join Point

A **point during execution** of a program.

In Spring AOP:

* Always a **method execution**

---

### 3.3 Advice

The **actual code** that runs at a join point.

Types:

* `@Before`
* `@After`
* `@AfterReturning`
* `@AfterThrowing`
* `@Around`

---

### 3.4 Pointcut

An **expression** that selects **which methods** the advice should run on.

```java
@Pointcut("execution(* com.example.service.*.*(..))")
```

---

### 3.5 Target Object

The **actual bean** whose method is being advised.

---

### 3.6 Proxy

Spring creates a **proxy object** that wraps the target object and applies advice.

---

## 4. Spring AOP Architecture

```
Client
  ↓
Proxy (created by Spring)
  ↓
Target Bean
```

Spring intercepts method calls via proxy → applies advice → calls real method.

---

## 5. Spring AOP vs AspectJ

| Feature     | Spring AOP            | AspectJ                     |
| ----------- | --------------------- | --------------------------- |
| Weaving     | Runtime               | Compile / Load / Runtime    |
| Join points | Method execution only | Method, field, constructor  |
| Complexity  | Simple                | Advanced                    |
| Usage       | Most Spring apps      | Low-level or framework work |

➡️ **Spring AOP uses AspectJ annotations but not full AspectJ power.**

---

## 6. Setting Up Spring AOP (Spring Boot)

### Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

---

## 7. A Simple Working Example

### 7.1 Business Service

```java
@Service
public class PaymentService {

    public void makePayment() {
        System.out.println("Processing payment...");
    }
}
```

---

### 7.2 Aspect with `@Before`

```java
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.service.PaymentService.makePayment(..))")
    public void logBefore() {
        System.out.println("Payment started");
    }
}
```

### Execution Flow

```
Payment started
Processing payment...
```

---

## 8. Advice Types (With Examples)

---

### 8.1 `@Before`

Runs **before method execution**

```java
@Before("execution(* com.example.service.*.*(..))")
public void beforeAdvice() {
    System.out.println("Before method");
}
```

---

### 8.2 `@After`

Runs **after method completes** (success or exception)

```java
@After("execution(* com.example.service.*.*(..))")
public void afterAdvice() {
    System.out.println("After method");
}
```

---

### 8.3 `@AfterReturning`

Runs **only if method returns successfully**

```java
@AfterReturning(
    pointcut = "execution(* com.example.service.*.*(..))",
    returning = "result"
)
public void afterReturning(Object result) {
    System.out.println("Returned: " + result);
}
```

---

### 8.4 `@AfterThrowing`

Runs **only if exception is thrown**

```java
@AfterThrowing(
    pointcut = "execution(* com.example.service.*.*(..))",
    throwing = "ex"
)
public void afterThrowing(Exception ex) {
    System.out.println("Exception: " + ex.getMessage());
}
```

---

### 8.5 `@Around` (Most Powerful)

Controls **before + after + exception handling**

```java
@Around("execution(* com.example.service.*.*(..))")
public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

    System.out.println("Before execution");

    Object result = pjp.proceed();

    System.out.println("After execution");

    return result;
}
```

➡️ Used for **transactions, performance monitoring**

---

## 9. Pointcut Expressions (Very Important)

### 9.1 Execution Syntax

```
execution(modifiers? return-type declaring-type method-name(..))
```

Examples:

```java
execution(* com.example.service.*.*(..))
execution(public * *(..))
execution(* save*(..))
```

---

### 9.2 Using `@Pointcut`

```java
@Pointcut("execution(* com.example.service.*.*(..))")
public void serviceMethods() {}
```

Reuse:

```java
@Before("serviceMethods()")
```

---

## 10. Accessing Method Details

### 10.1 Method Name & Arguments

```java
@Around("execution(* com.example.service.*.*(..))")
public Object log(ProceedingJoinPoint pjp) throws Throwable {

    String method = pjp.getSignature().getName();
    Object[] args = pjp.getArgs();

    System.out.println("Method: " + method);

    return pjp.proceed();
}
```

---

## 11. AOP + Annotations (Real-World Pattern)

### Custom Annotation

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
}
```

### Aspect

```java
@Around("@annotation(LogExecutionTime)")
public Object logTime(ProceedingJoinPoint pjp) throws Throwable {

    long start = System.currentTimeMillis();
    Object result = pjp.proceed();
    long end = System.currentTimeMillis();

    System.out.println("Time: " + (end - start));
    return result;
}
```

### Usage

```java
@LogExecutionTime
public void processOrder() {}
```

---

## 12. Spring AOP Limitations (Interview Favorite)

1. **Only method-level interception**
2. **Self-invocation doesn’t work**

```java
this.method(); // AOP NOT applied
```

3. Works only on **Spring-managed beans**
4. Final methods/classes cannot be proxied (CGLIB limitations)

---

## 13. JDK vs CGLIB Proxies

| Proxy Type | When Used             |
| ---------- | --------------------- |
| JDK Proxy  | Interface-based beans |
| CGLIB      | Class-based beans     |

Force CGLIB:

```properties
spring.aop.proxy-target-class=true
```

---

## 14. Common Use Cases in Real Projects

* `@Transactional` → AOP
* Spring Security → AOP
* Logging frameworks
* Auditing
* Rate limiting
* Metrics collection

---

## 15. Spring AOP Summary

> Spring AOP uses proxy-based interception to apply cross-cutting concerns at runtime, limited to method execution join points, using AspectJ annotations.

---
