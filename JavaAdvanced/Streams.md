# Java Streams 
## 1. What is a Java Stream?

A **Stream** is a sequence of elements that supports **functional-style operations** on collections of data.

> A stream **does not store data**.
> It **processes data from a source** (Collection, Array, I/O, etc.).

### Key Characteristics

* **Not a data structure**
* **Does not modify the original source**
* **Lazy evaluation**
* **Can be processed only once**
* **Supports functional programming**

---

## 2. Why Streams Were Introduced (Java 8)

Before Java 8:

* Verbose loops
* Imperative style
* Harder to parallelize

Streams provide:

* Declarative coding style
* Less boilerplate
* Better readability
* Easy parallel processing

---

## 3. Stream Pipeline Concept

A stream pipeline has **three parts**:

```
Source → Intermediate Operations → Terminal Operation
```

Example:

```java
list.stream()
    .filter(x -> x > 10)
    .map(x -> x * 2)
    .collect(Collectors.toList());
```

---

## 4. Stream Source

### Common Sources

```java
collection.stream()
collection.parallelStream()
Arrays.stream(array)
Stream.of(1, 2, 3)
```

Example:

```java
List<Integer> numbers = List.of(10, 20, 30);
Stream<Integer> stream = numbers.stream();
```

---

## 5. Intermediate Operations

Intermediate operations:

* Return a **Stream**
* Are **lazy**
* Can be chained

### Common Intermediate Operations

| Operation | Purpose             |
| --------- | ------------------- |
| map       | Transform elements  |
| filter    | Select elements     |
| distinct  | Remove duplicates   |
| sorted    | Sort elements       |
| flatMap   | Flatten nested data |
| peek      | Debugging           |

---

## 6. `map()` – Transformation

Used to **convert one form to another**.

### Example

```java
List<String> names = List.of("java", "spring");

List<String> result = names.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
```

Output:

```
["JAVA", "SPRING"]
```

---

## 7. `filter()` – Conditional Selection

Used to **select elements based on a condition**.

### Example

```java
List<Integer> numbers = List.of(10, 25, 30, 5);

List<Integer> result = numbers.stream()
        .filter(n -> n > 20)
        .collect(Collectors.toList());
```

Output:

```
[25, 30]
```

---

## 8. `distinct()` – Removing Duplicates

Uses `equals()` and `hashCode()` internally.

### Example

```java
List<Integer> numbers = List.of(1, 2, 2, 3, 3);

List<Integer> result = numbers.stream()
        .distinct()
        .collect(Collectors.toList());
```

Output:

```
[1, 2, 3]
```

---

## 9. `sorted()` – Sorting Elements

### Natural Sorting

```java
numbers.stream().sorted()
```

### Custom Sorting

```java
numbers.stream()
       .sorted(Comparator.reverseOrder())
```

---

## 10. `flatMap()` – Flattening Nested Structures

Used when **each element itself is a collection or stream**.

### Example

```java
List<List<Integer>> data = List.of(
    List.of(1, 2),
    List.of(3, 4)
);

List<Integer> result = data.stream()
        .flatMap(List::stream)
        .collect(Collectors.toList());
```

Output:

```
[1, 2, 3, 4]
```

---

## 11. Terminal Operations

Terminal operations:

* Produce a **result**
* Trigger execution
* End the stream

### Common Terminal Operations

| Operation | Result       |
| --------- | ------------ |
| collect   | Collection   |
| forEach   | void         |
| count     | long         |
| findFirst | Optional     |
| anyMatch  | boolean      |
| reduce    | single value |

---

## 12. `collect()` – Collecting Results

### To List

```java
List<Integer> list = stream.collect(Collectors.toList());
```

### To Set

```java
Set<Integer> set = stream.collect(Collectors.toSet());
```

---

## 13. `count()` – Counting Elements

```java
long count = numbers.stream()
        .filter(n -> n > 10)
        .count();
```

---

## 14. Stream Laziness (Very Important)

Streams execute **only when a terminal operation is called**.

```java
numbers.stream()
    .filter(n -> {
        System.out.println(n);
        return n > 10;
    });
```

Nothing prints until a terminal operation is added.

---

## 15. Stream vs Collection (Interview Favorite)

| Feature       | Collection | Stream   |
| ------------- | ---------- | -------- |
| Stores data   | Yes        | No       |
| Can be reused | Yes        | No       |
| Lazy          | No         | Yes      |
| Iteration     | External   | Internal |

---

## 16. Streams with Custom Objects

```java
class Employee {
    String name;
    int salary;
}
```

```java
employees.stream()
    .filter(e -> e.salary > 50000)
    .map(e -> e.name)
    .collect(Collectors.toList());
```

---

## 17. Common Mistakes

* Reusing a stream
* Forgetting terminal operation
* Heavy logic inside `map()`
* Using streams for very simple loops

---

## 18. When NOT to Use Streams

* Very simple loops
* Performance-critical low-level code
* When debugging step-by-step logic

---

## 19. Parallel Streams (Brief)

```java
list.parallelStream()
    .filter(...)
    .map(...)
```

⚠ Use only when:

* Large data
* Stateless operations

---

## 20. Interview Tips

* Explain **pipeline**
* Know **lazy execution**
* Difference between `map` vs `flatMap`
* Stream vs Collection
* Avoid overusing streams

---
