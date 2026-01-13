## ✅ What is an Iterator?

An **Iterator** in Java is an object used to **iterate (loop)** through elements of a collection (List, Set, etc.) **one element at a time**.

It provides **safe** access and allows element removal **during iteration**.

Think:

> Cursor that moves inside a Collection.

---

## ✅ Why Iterator?

| Loop Type           | Can Remove Elements Safely? | Works for     | Notes              |
| ------------------- | --------------------------- | ------------- | ------------------ |
| `for loop`          | ❌                           | List          | Index based        |
| `enhanced for-each` | ❌                           | List, Set     | Convenient         |
| `Iterator`          | ✅                           | ✅ List, ✅ Set | Safest for removal |
| `ListIterator`      | ✅                           | Only List     | Bidirectional      |

---

## ✅ Iterator Methods

| Method              | Meaning                       |
| ------------------- | ----------------------------- |
| `boolean hasNext()` | Checks if next element exists |
| `E next()`          | Returns next element          |
| `void remove()`     | Removes current element       |

---

## ✅ Basic Iterator Example (List)

```java
import java.util.*;

public class IteratorDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("C++");

        Iterator<String> it = list.iterator();

        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
```

---

## ✅ Removing Elements While Iterating (Iterator)

```java
import java.util.*;

public class RemoveDemo {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>(Arrays.asList(10, 20, 30, 40));

        Iterator<Integer> it = nums.iterator();

        while(it.hasNext()) {
            if(it.next() == 30) {
                it.remove(); // safe delete
            }
        }

        System.out.println(nums); // [10, 20, 40]
    }
}
```

> ❌ `list.remove()` inside list loop causes `ConcurrentModificationException`
> ✅ `iterator.remove()` prevents it

---

## ✅ Iterator with Set

```java
Set<String> set = new HashSet<>(Set.of("India", "USA", "UK"));

Iterator<String> it = set.iterator();

while(it.hasNext()) {
    System.out.println(it.next());
}
```

---

## ✅ ListIterator (Advanced Iterator – List Only)

| Feature     | Iterator | ListIterator |
| ----------- | -------- | ------------ |
| Forward     | ✅        | ✅            |
| Backward    | ❌        | ✅            |
| Add element | ❌        | ✅            |
| Get index   | ❌        | ✅            |

```java
List<String> names = new ArrayList<>(List.of("A", "B", "C"));
ListIterator<String> lit = names.listIterator();

while(lit.hasNext()) {
    System.out.println(lit.next());
}

System.out.println("Reverse:");
while(lit.hasPrevious()) {
    System.out.println(lit.previous());
}
```

---

## ✅ Iterator vs Enhanced For Loop

| Feature       | for-each | Iterator |
| ------------- | -------- | -------- |
| Read elements | ✅        | ✅        |
| Remove safely | ❌        | ✅        |
| Control       | Less     | More     |

---

## ✅ Iterator vs Enumeration (Old Legacy)

| Feature          | Enumeration       | Iterator               |
| ---------------- | ----------------- | ---------------------- |
| Modern?          | ❌                 | ✅                      |
| Remove elements? | ❌                 | ✅                      |
| Used in          | Vector, Hashtable | All modern collections |

---

## 🔥 Real World Use Cases

| Use Case                       | Why Iterator                 |
| ------------------------------ | ---------------------------- |
| Streaming objects / DB records | Slow consumption             |
| Filtering & removing bad data  | Safe removal                 |
| Iterating Set                  | No index, so Iterator needed |
| Processing UI & message queues | Controlled traversal         |

---

## 🧠 Interview Nuggets

⚡ `ConcurrentModificationException` occurs when modifying list *without iterator*
⚡ `ListIterator` supports **add, remove, update**
⚡ Sets need Iterator since **no index**
⚡ `Iterator` is **fail-fast** (Concurrent modification errors)
⚡ `Enumeration` is **fail-safe** (legacy collections)

---
