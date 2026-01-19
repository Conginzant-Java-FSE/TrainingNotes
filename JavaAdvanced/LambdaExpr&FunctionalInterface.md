# Java Lambda Expressions & Functional Interfaces 

# PART 1: Lambda Expressions

## 1. What is a Lambda Expression?

A **lambda expression** is an **anonymous function** (method without a name) used to represent **behavior as data**.

Introduced in **Java 8** to support **functional programming**.

### Simple Definition

> A lambda expression provides a **short and clear way** to implement a functional interface.

---

## 2. Why Lambda Expressions?

Before Java 8 (anonymous class):

```java
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};
```

With lambda:

```java
Runnable r = () -> System.out.println("Running");
```

### Benefits

* Less boilerplate
* More readable
* Better for Streams and APIs
* Enables functional style

---

## 3. Lambda Expression Syntax

### General Syntax

```
(parameters) -> { body }
```

### Variations

**No parameters**

```java
() -> System.out.println("Hello")
```

**One parameter**

```java
x -> x * 2
```

**Multiple parameters**

```java
(a, b) -> a + b
```

**Multiple statements**

```java
(a, b) -> {
    int sum = a + b;
    return sum;
}
```

---

## 4. Lambda vs Method

| Feature          | Lambda                    |
| ---------------- | ------------------------- |
| Name             | No                        |
| Return type      | Inferred                  |
| Access modifiers | Not allowed               |
| `this` keyword   | Refers to enclosing class |

---

## 5. Lambda with Functional Interface (Basic Example)

```java
@FunctionalInterface
interface Calculator {
    int add(int a, int b);
}
```

Implementation using lambda:

```java
Calculator calc = (a, b) -> a + b;
System.out.println(calc.add(10, 20));
```

---

## 6. Where Lambdas Are Used

* Streams API
* Collections sorting
* Event handling
* Thread creation
* Functional interfaces

Example:

```java
list.stream()
    .filter(n -> n > 10)
    .forEach(System.out::println);
```

---

## 7. Lambda Limitations

* Works only with **functional interfaces**
* Cannot throw checked exceptions directly
* Harder to debug when overused
* Not good for complex logic

---

# PART 2: Functional Interfaces

## 8. What is a Functional Interface?

A **functional interface** is an interface that has **exactly one abstract method**.

> Lambda expressions **can only be used with functional interfaces**.

---

## 9. `@FunctionalInterface` Annotation

```java
@FunctionalInterface
interface Printer {
    void print(String msg);
}
```

✔ Ensures only one abstract method
✔ Compile-time safety
✔ Optional but recommended

---

## 10. Valid Functional Interface Example

```java
@FunctionalInterface
interface Greeting {
    void sayHello();
}
```

Default methods are allowed:

```java
default void welcome() {
    System.out.println("Welcome");
}
```

Static methods are allowed:

```java
static void info() {
    System.out.println("Info");
}
```

---

## 11. Invalid Functional Interface Example

```java
@FunctionalInterface
interface Test {
    void m1();
    void m2(); //  Compilation error
}
```

---

## 12. Built-in Functional Interfaces (Very Important)

### Common Ones from `java.util.function`

| Interface     | Method          | Use       |
| ------------- | --------------- | --------- |
| Predicate<T>  | boolean test(T) | Filtering |
| Function<T,R> | R apply(T)      | Mapping   |
| Consumer<T>   | void accept(T)  | Action    |
| Supplier<T>   | T get()         | Creation  |

---

### Examples

**Predicate**

```java
Predicate<Integer> p = n -> n > 10;
```

**Function**

```java
Function<String, Integer> f = s -> s.length();
```

**Consumer**

```java
Consumer<String> c = s -> System.out.println(s);
```

**Supplier**

```java
Supplier<Double> s = () -> Math.random();
```

---

## 13. Functional Interface + Lambda + Stream (Interview Combo)

```java
list.stream()
    .filter(n -> n % 2 == 0)   // Predicate
    .map(n -> n * 2)           // Function
    .forEach(System.out::println); // Consumer
```

---

## 14. Lambda vs Anonymous Class (Interview Table)

| Feature     | Lambda          | Anonymous Class |
| ----------- | --------------- | --------------- |
| Code size   | Small           | Large           |
| Readability | High            | Low             |
| `this`      | Enclosing class | Anonymous class |
| Performance | Better          | Slightly slower |

---

## 15. Common Interview Pitfalls

* Lambda works only with functional interfaces
* `@FunctionalInterface` allows default/static methods
* Lambdas cannot access non-final local variables
* Lambdas don’t create a new scope

---

## 16. When NOT to Use Lambdas

* Very complex logic
* Need detailed debugging
* Reusable business logic
* Checked exception-heavy code

---

## 17. One-Line Summaries (Interview Gold)

* “Lambda expressions implement functional interfaces.”
* “A functional interface has exactly one abstract method.”
* “Lambda expressions reduce boilerplate code.”
* “Streams heavily rely on functional interfaces.”

---

## 18. Final Takeaway

> **Lambda Expressions = Behavior**
> **Functional Interfaces = Contract**

They always work **together**.

---
