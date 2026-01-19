# Java `Optional` Class 
The `Optional` class (introduced in **Java 8**) is a **container object** used to represent a value that **may or may not be present**, helping to **avoid `NullPointerException`**.

---

## 1. Why `Optional`?

Traditional approach:

```java
if (obj != null) {
    obj.doSomething();
}
```

Problems:

* Repetitive null checks
* Error-prone
* Harder to read

`Optional` provides a **clean, expressive alternative**.

---

## 2. Creating Optional Objects

### `Optional.of()`

Use when value is **guaranteed non-null**.

```java
Optional<String> opt = Optional.of("Java");
```

❌ Throws `NullPointerException` if value is null.

---

### `Optional.ofNullable()`

Use when value **may be null**.

```java
Optional<String> opt = Optional.ofNullable(value);
```

---

### `Optional.empty()`

Represents no value.

```java
Optional<String> opt = Optional.empty();
```

---

## 3. Checking Value Presence

### `isPresent()`

```java
if (opt.isPresent()) {
    System.out.println(opt.get());
}
```

### `ifPresent()`

```java
opt.ifPresent(System.out::println);
```

---

## 4. Retrieving Values

### `get()`

```java
String val = opt.get();
```

⚠ Unsafe without `isPresent()` check.

---

### `orElse()`

Returns default value.

```java
String val = opt.orElse("Default");
```

---

### `orElseGet()`

Lazy default value (preferred).

```java
String val = opt.orElseGet(() -> "Default");
```

---

### `orElseThrow()`

Throws exception if empty.

```java
String val = opt.orElseThrow();
```

Custom exception:

```java
opt.orElseThrow(() -> new RuntimeException("Value missing"));
```

---

## 5. Transforming Optional Values

### `map()`

Transforms value if present.

```java
Optional<Integer> length =
        opt.map(String::length);
```

---

### `filter()`

Keeps value only if condition passes.

```java
opt.filter(s -> s.length() > 3);
```

---

## 6. Chaining Optional Operations

```java
String result = opt
        .filter(s -> s.startsWith("J"))
        .map(String::toUpperCase)
        .orElse("INVALID");
```

---

## 7. Optional with Methods

Bad:

```java
public String getName() { }
```

Better:

```java
public Optional<String> getName() { }
```

Usage:

```java
user.getName().ifPresent(System.out::println);
```

---

## 8. Optional vs Null (Interview)

| Feature                | Optional | null |
| ---------------------- | -------- | ---- |
| NPE safe               | Yes      | No   |
| Readability            | High     | Low  |
| Functional ops         | Yes      | No   |
| Recommended for fields | ❌        | ✔    |

---

## 9. Common Mistakes

* Using `Optional.get()` blindly
* Using `Optional` as class fields
* Wrapping already non-null values unnecessarily
* Using `Optional` for serialization entities

---

## 10. When to Use Optional

✔ Method return values
✔ Stream terminal results
✔ Avoiding null checks

❌ Fields
❌ Method parameters

---

## 11. Interview One-Liners

* "`Optional` is a container to avoid null-related issues."
* "`orElseGet()` is lazy, `orElse()` is eager."
* "`Optional` is not a replacement for every null."

---

## 12. Quick Example (Realistic)

```java
Optional<User> user = findUserById(id);

String email = user
        .map(User::getEmail)
        .orElse("no-email@example.com");
```

---

### **Summary**

> Use `Optional` to **express absence clearly**, not to eliminate null everywhere.

---
