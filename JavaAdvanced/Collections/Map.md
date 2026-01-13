

# **What is `Map`?**

`Map<K, V>` in Java stores **key-value pairs**.
Example: EmployeeID → EmployeeName

| Property      | Description                                                |
| ------------- | ---------------------------------------------------------- |
| Keys          | Unique                                                     |
| Values        | Can be duplicate                                           |
| Null allowed? | `HashMap` — Yes; `TreeMap` — No for keys; `Hashtable` — No |

---

## **Popular Implementations**

| Type                | Ordering                  | Thread-Safe | Null Allowed | Use Case                        |
| ------------------- | ------------------------- | ----------- | ------------ | ------------------------------- |
| `HashMap`           | No                        | ❌           | Yes          | Fast lookup, general use        |
| `LinkedHashMap`     | Maintains insertion order | ❌           | Yes          | Access in insertion order       |
| `TreeMap`           | Sorted keys (ascending)   | ❌           | No           | Sorted map use cases            |
| `Hashtable`         | No order                  | ✅           | No           | Legacy thread-safe map          |
| `ConcurrentHashMap` | No                        | ✅           | Yes          | High-performance multi-threaded |

---

## **Common `Map` Methods**

| Method                               | Description                   |
| ------------------------------------ | ----------------------------- |
| `put(k, v)`                          | Add / update                  |
| `get(k)`                             | Retrieve                      |
| `containsKey(k)`, `containsValue(v)` | Check                         |
| `remove(k)`                          | Remove entry                  |
| `size()`, `isEmpty()`                | Info                          |
| `keySet()`, `values()`, `entrySet()` | View                          |
| `putIfAbsent(k, v)`                  | Adds only if key not present  |
| `compute()`, `merge()`               | Modify in stream-friendly way |

---

## **Basic HashMap Demo**

```java
import java.util.*;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();

        map.put(101, "Alice");
        map.put(102, "Bob");
        map.put(103, "Charlie");
        map.put(101, "Alex"); // Updates existing

        System.out.println("Map: " + map);

        System.out.println("Get value for key 102: " + map.get(102));
        System.out.println("Contains key 103? " + map.containsKey(103));

        map.remove(103);
        System.out.println("After remove key 103: " + map);

        System.out.println("Keys: " + map.keySet());
        System.out.println("Values: " + map.values());
        System.out.println("Entries: " + map.entrySet());
    }
}
```

---

## **Iterating Over Map**

```java
Map<String, Integer> marks = Map.of("Tom", 88, "Jerry", 92, "Bob", 77);

// 1. For-each on entrySet()
for (Map.Entry<String, Integer> entry : marks.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}

// 2. keySet()
for (String name : marks.keySet()) {
    System.out.println(name + " -> " + marks.get(name));
}

// 3. lambda
marks.forEach((k,v) -> System.out.println(k + ": " + v));
```

---

## **LinkedHashMap Demo**

Maintains **insertion order**

```java
import java.util.*;

public class LinkedHashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Banana", 40);
        map.put("Apple", 60);
        map.put("Orange", 30);

        System.out.println(map);
    }
}
```

---

## **TreeMap Demo**

Automatically **sorts keys**

```java
import java.util.*;

public class TreeMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new TreeMap<>();
        map.put("Zara", 90);
        map.put("Alan", 85);
        map.put("Ben", 75);

        System.out.println(map); // Sorted by keys
    }
}
```

---

## **Sorting a HashMap by Value**

```java
import java.util.*;

public class SortMapByValue {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Ravi", 95);
        scores.put("Amit", 85);
        scores.put("Sneha", 92);

        scores.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .forEach(System.out::println);
    }
}
```

---

## **Real-World Use Cases**

| Scenario                   | Why `Map`?          |
| -------------------------- | ------------------- |
| Employee ID → Name         | Search fast by ID   |
| ProductID → Price          | Inventory systems   |
| URL → Page Cache           | Web browser caching |
| Username → Password Hash   | Auth systems        |
| Country → Capital          | Lookup tables       |
| File Extension → MIME Type | Server config       |

---

## **Mini Project Example: User Login System**

```java
import java.util.*;

public class LoginSystem {
    public static void main(String[] args) {
        Map<String, String> users = new HashMap<>();
        users.put("admin", "admin123");
        users.put("john", "john99");
        users.put("jane", "jane007");

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String u = sc.next();
        System.out.print("Enter password: ");
        String p = sc.next();

        if (users.containsKey(u) && users.get(u).equals(p))
            System.out.println("Login Successful");
        else
            System.out.println("Invalid credentials");
    }
}
```

---

## **Key Interview Notes**

✅ `HashMap` is NOT thread-safe
✅ `Hashtable` is thread-safe but slow (legacy)
✅ Use `ConcurrentHashMap` for multithreading
✅ `TreeMap` sorts keys, cannot store null keys
✅ `putIfAbsent` avoids overwriting
✅ Best iteration = `entrySet()` when key & value both needed

---
