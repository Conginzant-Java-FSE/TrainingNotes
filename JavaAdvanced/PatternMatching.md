# Java Pattern Matching
---

## 1. Why Pattern Matching?

Before:

```java
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
```

With pattern matching:

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

✔ No explicit cast
✔ Less boilerplate
✔ Safer code

---

## 2. Pattern Matching for `instanceof`

Syntax:

```java
if (obj instanceof Type variable) {
    // use variable
}
```

Example:

```java
if (obj instanceof Integer n && n > 10) {
    System.out.println(n * 2);
}
```

---

## 3. Scope of Pattern Variable

Pattern variable is valid only when condition is **true**.

```java
if (obj instanceof String s) {
    System.out.println(s);
}
// s not accessible here
```

---

## 4. Pattern Matching in `switch` (Java 17+)

Allows matching **types and values**.

```java
switch (obj) {
    case String s -> System.out.println("String: " + s);
    case Integer i -> System.out.println("Integer: " + i);
    default -> System.out.println("Unknown");
}
```

---

## 5. Pattern Matching + Sealed Classes

Enables **exhaustive switch**.

```java
sealed interface Shape permits Circle, Rectangle { }

final class Circle implements Shape { }
final class Rectangle implements Shape { }
```

```java
switch (shape) {
    case Circle c -> System.out.println("Circle");
    case Rectangle r -> System.out.println("Rectangle");
}
```

✔ No `default` required
✔ Compiler ensures completeness

---

## 6. Guarded Patterns (Conditions)

```java
switch (obj) {
    case String s && s.length() > 5 -> System.out.println("Long string");
    case String s -> System.out.println("Short string");
    default -> System.out.println("Other");
}
```

---

## 7. Pattern Matching with `null`

```java
switch (obj) {
    case null -> System.out.println("Null value");
    case String s -> System.out.println(s);
    default -> System.out.println("Other");
}
```

---

## 8. Pattern Matching with Records

```java
record Point(int x, int y) { }
```

```java
if (obj instanceof Point(int x, int y)) {
    System.out.println(x + ", " + y);
}
```

✔ Automatic data extraction
✔ Very concise

---

## 9. Pattern Matching vs Traditional Code

| Feature     | Traditional | Pattern Matching |
| ----------- | ----------- | ---------------- |
| Casting     | Manual      | Automatic        |
| Readability | Lower       | Higher           |
| Safety      | Medium      | High             |
| Boilerplate | More        | Less             |

---

## 10. Common Mistakes

* Using pattern variable outside scope
* Forgetting `default` in non-sealed switches
* Assuming pattern matching works in all Java versions

---

## 11. When to Use Pattern Matching

✔ Type-based logic
✔ Sealed class hierarchies
✔ Cleaner switch statements
✔ Record deconstruction


---

## 12. Interview One-Liners

* "Pattern matching removes explicit casting."
* "Pattern variables are scope-limited."
* "Sealed classes enable exhaustive switches."
* "Pattern matching improves readability and safety."

---

## 13. Final Summary

> **Pattern Matching modernizes Java by making type checks concise, safe, and expressive.**

---
