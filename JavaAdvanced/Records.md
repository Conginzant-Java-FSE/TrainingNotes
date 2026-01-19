# Java Records 

## 1. Why Records?

Before records, simple data classes required:

* Fields
* Constructor
* Getters
* `equals()`, `hashCode()`
* `toString()`

Records generate all of these automatically.

---

## 2. Basic Syntax

```java
public record Person(String name, int age) { }
```

This single line creates:

* `private final` fields
* Canonical constructor
* Accessor methods (`name()`, `age()`)
* `equals()`, `hashCode()`, `toString()`

---

## 3. Creating and Using Records

```java
Person p = new Person("Arun", 25);

System.out.println(p.name());
System.out.println(p.age());
```

---

## 4. Immutability

* All fields are **final**
* Records are **implicitly immutable**
* No setters allowed

```java
// p.age = 30;  Not allowed
```

---

## 5. Custom Constructor (Validation)

```java
public record Person(String name, int age) {
    public Person {
        if (age < 0)
            throw new IllegalArgumentException("Age cannot be negative");
    }
}
```

---

## 6. Adding Methods

Records can have methods.

```java
public record Person(String name, int age) {
    public boolean isAdult() {
        return age >= 18;
    }
}
```

---

## 7. Records vs Classes

| Feature      | Class          | Record        |
| ------------ | -------------- | ------------- |
| Boilerplate  | High           | Very low      |
| Immutability | Optional       | Default       |
| Inheritance  | Can extend     | Cannot extend |
| Interfaces   | ✔              | ✔             |
| Best for     | Business logic | Data carrier  |

---

## 8. Records with Interfaces

```java
interface Employee { }

record Developer(String name) implements Employee { }
```

---

## 9. Records + Pattern Matching

```java
if (obj instanceof Person(String name, int age)) {
    System.out.println(name + " " + age);
}
```

✔ Automatic field extraction
✔ Clean syntax

---

## 10. When NOT to Use Records

* Mutable objects
* Entities with identity (JPA entities)
* Heavy business logic
* Frameworks requiring no-arg constructor

---

## 11. Common Use Cases

* DTOs
* API request/response models
* Configuration holders
* Immutable value objects

---

## 12. Interview One-Liners

* "Records are immutable data carriers."
* "Records reduce boilerplate code."
* "Accessors are methods, not getters."
* "Records cannot extend classes."

---

## 13. Limitations

* Cannot extend another class
* Fields are final
* Not suitable for JPA entities
* No setters

---

## 14. Final Summary

> **Use records when your class is mainly meant to carry data, not behavior.**

---
