## ✅ **What is the List Interface in Java?**

`List<E>` is an **ordered collection** in Java that:

* Allows **duplicate elements**
* Preserves **insertion order**
* Supports **index-based access**
* Allows **null values** (depending on implementation)

Belongs to:
`java.util` package
Hierarchy:

```
Collection → List → (ArrayList, LinkedList, Vector, Stack)
```

---

## ✅ **When to Use List?**

Use List when:

| Requirement              | Why List?                    |
| ------------------------ | ---------------------------- |
| Preserve insertion order | List keeps order             |
| Access by index          | `list.get(index)`            |
| Allow duplicates         | List permits repetition      |
| Dynamic resizing         | Handles growth automatically |

---

## ✅ **Common List Implementations**

| Type       | Internal Structure     | Best Use Case                     |
| ---------- | ---------------------- | --------------------------------- |
| ArrayList  | Dynamic array          | Fast reads, random access         |
| LinkedList | Doubly linked list     | Fast insert/delete in middle/head |
| Vector     | Synchronized ArrayList | Legacy, thread safety             |
| Stack      | LIFO (extends Vector)  | Stack operations (push/pop)       |

---

## ✅ **Key List Methods**

| Category        | Methods                                               |
| --------------- | ----------------------------------------------------- |
| Add elements    | `add()`, `add(index, element)`                        |
| Access elements | `get(index)`, `set(index, value)`                     |
| Remove elements | `remove(index)`, `remove(Object)`, `clear()`          |
| Search/find     | `contains()`, `indexOf()`, `lastIndexOf()`            |
| Utility         | `size()`, `isEmpty()`                                 |
| Iteration       | `Iterator`, `ListIterator`, `forEach`, enhanced `for` |

---

## ✅ **List Example (ArrayList)**

```java
import java.util.*;

public class ListDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add("Java");
        list.add("Spring");
        list.add("Microservices");
        list.add("Java");  // duplicate allowed

        System.out.println(list);             // [Java, Spring, Microservices, Java]
        System.out.println(list.get(2));      // Microservices
        
        list.remove("Spring");
        list.set(1, "Spring Boot");
        
        System.out.println(list);             // [Java, Spring Boot, Java]
    }
}
```

---

## ✅ **Iteration Options**

### 1️⃣ For-each Loop

```java
for (String tech : list) {
    System.out.println(tech);
}
```

### 2️⃣ Iterator

```java
Iterator<String> it = list.iterator();
while(it.hasNext()) {
    System.out.println(it.next());
}
```

### 3️⃣ ListIterator (Bidirectional)

```java
ListIterator<String> lt = list.listIterator();
while(lt.hasNext()) {
    System.out.println(lt.next());
}
while(lt.hasPrevious()) {
    System.out.println(lt.previous());
}
```

### 4️⃣ Stream API

```java
list.stream().forEach(System.out::println);
```

---

## ✅ **ArrayList vs LinkedList**

| Feature                   | ArrayList     | LinkedList           |
| ------------------------- | ------------- | -------------------- |
| Access by index           | ✅ Fast (O(1)) | ❌ Slow (O(n))        |
| Insertion/Deletion middle | ❌ Slow        | ✅ Fast               |
| Memory                    | Compact       | More (node pointers) |

---

## ✅ **Synchronization**

List is **not synchronized** by default (except Vector & Stack).

To synchronize:

```java
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
```

---

## ✅ **Immutable List**

### Java 9+

```java
List<String> list = List.of("A", "B", "C");
```

### Collections.unmodifiableList()

```java
List<String> list = new ArrayList<>(Arrays.asList("X", "Y"));
List<String> unmodifiable = Collections.unmodifiableList(list);
```

---

## ✅ **List vs Set**

| Feature      | List            | Set                 |
| ------------ | --------------- | ------------------- |
| Order        | Maintains order | No guaranteed order |
| Duplicates   | ✅ Allowed       | ❌ Not allowed       |
| Index Access | ✅ Yes           | ❌ No                |

---

## ✅ **Common Interview Questions**

### 🧠 Explain List in Java

> An ordered collection that allows duplicates & supports index-based access.

### 🧠 Difference between ArrayList & LinkedList

* ArrayList: Fast read, slow insert/delete
* LinkedList: Slow read, fast insert/delete

### 🧠 List vs Set

Duplicates allowed? Order maintained? Index access?

### 🧠 Thread-safe alternative to ArrayList

* `Vector` (legacy)
* `Collections.synchronizedList()`
* `CopyOnWriteArrayList` (modern concurrency)

---

## ✅ **Coding Exercise (for Students)**

### Problem:

Create a student attendance tracking system:

**Requirements**

* Add student names in order of attendance
* Allow duplicate names (same student attends multiple days)
* Display:

    * First student attended
    * All attendances
    * Reverse order display
    * Count of each student attended

**Hint:** Use `List`, `LinkedList`, `ListIterator`, and `Collections.frequency()`

---

## ⭐ **Best Practices**

| Recommendation                                           |
| -------------------------------------------------------- |
| Prefer `ArrayList` unless needing fast middle insertions |
| Use `List` interface type, not implementation            |
| Use streams for functional operations                    |
| Avoid Vector unless working with legacy systems          |

---
Below is a **complete, crisp yet deep** tutorial on **LinkedList, Stack & Vector in Java** under the List interface. Useful for training sessions, interviews, and assignments.

---

Hierarchy:

```
Collection → List → 
    ├── ArrayList
    ├── LinkedList
    ├── Vector → Stack
```

---

## 🚀 **LinkedList**

### 📌 What It Is

A **doubly-linked list** implementation of `List` & `Deque`.
Great for **frequent insertions/deletions**, poor for random access.

### ⭐ Key Features

| Feature       | Behavior                                     |
| ------------- | -------------------------------------------- |
| Structure     | Doubly Linked Nodes                          |
| Access        | Slow (O(n))                                  |
| Insert/Delete | Fast at head/middle (O(1) if position known) |
| Order         | Maintains insertion order                    |
| Nulls         | Allowed                                      |
| Implements    | `List`, `Deque` ⇒ works like queue & stack   |

### ✅ Use Cases

* Queue / Deque operations
* Frequent add/remove operations
* Playlist, browser history, task scheduling

### 🧪 Example

```java
import java.util.LinkedList;

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> queue = new LinkedList<>();

        queue.add("Java");
        queue.add("Spring");
        queue.addFirst("Database");
        queue.addLast("Microservices");

        System.out.println(queue); // [Database, Java, Spring, Microservices]

        System.out.println(queue.get(1)); // Java

        queue.removeFirst();
        queue.removeLast();

        System.out.println(queue); // [Java, Spring]
    }
}
```

---

## 🚀 ** Stack**

### 📌 What It Is

Legacy **LIFO (Last-In-First-Out)** data structure.
Extends **Vector**.

> ✅ Use only when specifically asked; prefer `Deque` in modern Java.

### ⭐ Key Features

| Feature     | Behavior                |
| ----------- | ----------------------- |
| Order       | LIFO                    |
| Thread-safe | Yes (synchronized)      |
| Performance | Slower than Deque       |
| Implements  | Legacy stack via Vector |

### ✅ Use Cases

* Undo / Redo
* Parsing (compilers)
* Browser back button
* Recursion simulation

### 🧪 Example

```java
import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();

        stack.push("HTML");
        stack.push("CSS");
        stack.push("JavaScript");

        System.out.println(stack); // [HTML, CSS, JavaScript]

        System.out.println(stack.pop()); // JavaScript
        System.out.println(stack.peek()); // CSS
    }
}
```

---

## 🚀 ** Vector**

### 📌 What It Is

Legacy **synchronized**, growable array like ArrayList.

### ⭐ Key Features

| Feature       | Behavior                        |
| ------------- | ------------------------------- |
| Thread-safe   | ✅ Yes                           |
| Performance   | ❌ Slower due to synchronization |
| Order         | Maintains insertion order       |
| Duplicates    | Allowed                         |
| Grow Capacity | Doubles (ArrayList grows 50%)   |

### ✅ Use Cases

* Legacy enterprise code
* Multi-threaded environments where synchronization is needed (rare now)

### 🧪 Example

```java
import java.util.Vector;

public class VectorDemo {
    public static void main(String[] args) {
        Vector<Integer> numbers = new Vector<>();

        numbers.add(10);
        numbers.add(20);
        numbers.add(30);

        System.out.println(numbers); // [10, 20, 30]

        numbers.remove(1);
        System.out.println(numbers); // [10, 30]
    }
}
```

---

## ⚔️ **Comparison Table**

| Feature            | LinkedList             | Vector                 | Stack             |
| ------------------ | ---------------------- | ---------------------- | ----------------- |
| Type               | Doubly-Linked List     | Synchronized ArrayList | Synchronized LIFO |
| Thread-safe        | ❌ No                   | ✅ Yes                  | ✅ Yes             |
| Best For           | Frequent insert/delete | Legacy concurrency     | LIFO operations   |
| Access Speed       | Slow O(n)              | Fast O(1)              | Fast O(1)         |
| Modern Alternative | `Deque` / Queue        | `ArrayList`            | `ArrayDeque`      |

---

## 🎓 **Interview Questions**

| Question                       | Expected Answer                                                                       |
| ------------------------------ | ------------------------------------------------------------------------------------- |
| Why LinkedList over ArrayList? | Faster insert/delete, slower access                                                   |
| Why Vector is outdated?        | Synchronized + slow; replaced by `Collections.synchronizedList()` or concurrent lists |
| Why Stack discouraged?         | Legacy; use `ArrayDeque`                                                              |
| LinkedList implements?         | List + Deque ⇒ supports queue & stack                                                 |

---

## 🧠 **Trainer Tip**

> Ask students to **simulate Browser Tabs** using LinkedList,
> **Undo feature** with Stack,
> and convert legacy Vector to `ArrayList`.

---

## 🎯 **Mini Assignment**

### Problem:

Design a **Train Ticket Queue System**:

* Passengers join queue (LinkedList)
* VIP can join at start
* Cancel first passenger
* Show queue forward & reverse (use ListIterator)
* Add undo feature using `Stack`


