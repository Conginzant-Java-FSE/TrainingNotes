

# ✅ **Queue in Java — Complete Demo**

## 📌 **What is a Queue?**

A **FIFO (First-In-First-Out)** data structure.

> First item added → first removed
> Like a **ticket counter line**.

### Queue in Collections Framework

```
Collection → Queue → (LinkedList, PriorityQueue, ArrayDeque)
                        ↑
                      Deque → (ArrayDeque, LinkedList)
```

---

## 🎯 **Key Queue Operations**

| Operation     | Method                | Meaning                                    |
| ------------- | --------------------- | ------------------------------------------ |
| Insert        | `add(e)`, `offer(e)`  | offer() returns false instead of exception |
| Remove        | `remove()`, `poll()`  | poll() returns null instead of exception   |
| Retrieve head | `element()`, `peek()` | peek() returns null instead of exception   |

**Preferred safe methods:** `offer()`, `poll()`, `peek()`

---

## ✅ **Most Common Queue Implementations**

| Type              | Backed By        | Order                 | Null Allowed | When to use         |
| ----------------- | ---------------- | --------------------- | ------------ | ------------------- |
| **LinkedList**    | Linked list      | FIFO                  | Yes          | General queue       |
| **ArrayDeque**    | Resizeable array | FIFO / stack          | ❌ No         | **Best choice**     |
| **PriorityQueue** | Heap             | Sorted priority order | ❌ No         | Priority scheduling |

---

## 🚦 **Queue Basic Demo (using LinkedList)**

```java
import java.util.*;

public class QueueDemo {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();

        queue.offer("Customer1");
        queue.offer("Customer2");
        queue.offer("Customer3");

        System.out.println("Queue: " + queue);

        System.out.println("Next to serve: " + queue.peek());   // head
        System.out.println("Serving: " + queue.poll());         // removes head

        System.out.println("Remaining Queue: " + queue);
    }
}
```

**Output**

```
Queue: [Customer1, Customer2, Customer3]
Next to serve: Customer1
Serving: Customer1
Remaining Queue: [Customer2, Customer3]
```

---

## 🧠 **ArrayDeque (Best Option) Demo**

> Faster than LinkedList for queue/stack

```java
import java.util.*;

public class ArrayDequeDemo {
    public static void main(String[] args) {
        ArrayDeque<String> q = new ArrayDeque<>();

        q.offer("Task1");
        q.offer("Task2");
        q.offer("Task3");

        System.out.println(q);

        System.out.println("Processing: " + q.poll());
        System.out.println("Queue After Poll: " + q);
    }
}
```

---

### ⭐ **PriorityQueue Demo**

> Elements come out sorted (natural order or custom comparator)

```java
import java.util.*;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.offer(30);
        pq.offer(10);
        pq.offer(50);
        pq.offer(20);

        System.out.println("PriorityQueue: " + pq);
        while(!pq.isEmpty()) {
            System.out.println("Serving: " + pq.poll());
        }
    }
}
```

**Output**

```
Serving:
10
20
30
50
```

---

## 🛠️ **Real-World Use Cases**

| Use Case          | Explanation                      |
| ----------------- | -------------------------------- |
| Message Queues    | Kafka / RabbitMQ / Redis Streams |
| Task Scheduling   | CPU scheduling (queues)          |
| Customer Support  | FIFO tickets                     |
| Printer queue     | First file prints first          |
| OS Job Scheduling | Round-robin queue                |
| Call center / IVR | Next caller served first         |
| BFS algorithms    | Graph traversal uses queue       |

---

### 🔁 **Queue vs Stack vs Deque**

| Feature      | Queue                   | Stack              | Deque                           |
| ------------ | ----------------------- | ------------------ | ------------------------------- |
| Order        | FIFO                    | LIFO               | Both                            |
| Typical Impl | LinkedList / ArrayDeque | Stack / ArrayDeque | ArrayDeque                      |
| Operations   | offer/poll/peek         | push/pop/peek      | offerFirst/Last, pollFirst/Last |

---

## 💣 Common Mistakes

| Mistake                              | Fix                               |
| ------------------------------------ | --------------------------------- |
| Using `add()` instead of `offer()`   | Prefer offer() for safety         |
| Using `remove()` instead of `poll()` | Prefer poll() to avoid exceptions |
| Using LinkedList always              | Prefer **ArrayDeque**             |

---

### ✅ Complete FIFO Simulation Example

### Problem

Simulate **doctor clinic queue system**.

```java
import java.util.*;

public class ClinicQueue {
    public static void main(String[] args) {
        Queue<String> patients = new ArrayDeque<>();

        patients.offer("Ravi");
        patients.offer("Priya");
        patients.offer("Karan");

        while(!patients.isEmpty()) {
            System.out.println("Calling patient: " + patients.poll());
        }

        System.out.println("All patients seen.");
    }
}
```

---

#### 🧪 **Mini Project Challenge**

### Task: **Bank Counter System**

Operations:

```
1. Add customer
2. Serve customer
3. Display waiting line
4. Exit
```

Use `ArrayDeque`.

---

### 💬 Interview Questions

| Question                            | Answer                                     |
| ----------------------------------- | ------------------------------------------ |
| What is FIFO?                       | First in, first out                        |
| Queue vs PriorityQueue              | FIFO vs priority sorted                    |
| LinkedList or ArrayDeque for Queue? | **ArrayDeque** preferred                   |
| Remove vs Poll?                     | Poll returns null, remove throws exception |

---


### ✅ **PriorityQueue in Java**

### 📌 Key Concept

PriorityQueue does **NOT follow FIFO** —
It removes elements based on **priority (smallest or largest value)**.

> Default priority = **ascending (min-heap)**

---

### ✨ **Basic PriorityQueue **

```java
import java.util.*;

public class PriorityQueueBasicDemo {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.offer(50);
        pq.offer(10);
        pq.offer(40);
        pq.offer(20);

        System.out.println("Internal Queue: " + pq); // Heap structure (not sorted visually)

        while (!pq.isEmpty()) {
            System.out.println("Removing: " + pq.poll());
        }
    }
}
```

### ✅ Output

```
Removing: 10
Removing: 20
Removing: 40
Removing: 50
```

> Lowest number removed first (min-priority queue)

---

###  **Max-Priority Queue (Highest priority first)**

Use a **custom comparator**:

```java
import java.util.*;

public class MaxPriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        pq.offer(50);
        pq.offer(10);
        pq.offer(40);
        pq.offer(20);

        while (!pq.isEmpty()) {
            System.out.println("Removing: " + pq.poll());
        }
    }
}
```

### ✅ Output

```
Removing: 50
Removing: 40
Removing: 20
Removing: 10
```

---

### 🎭 **PriorityQueue with Custom Objects**

### ✅ Real-Time Example: Task Scheduler

```java
import java.util.*;

class Task {
    String name;
    int priority; // 1 = High, 5 = Low

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return name + " (priority " + priority + ")";
    }
}

public class TaskScheduler {
    public static void main(String[] args) {

        PriorityQueue<Task> queue = new PriorityQueue<>(Comparator.comparingInt(t -> t.priority));

        queue.offer(new Task("Fix Prod Bug", 1));
        queue.offer(new Task("Write Unit Tests", 4));
        queue.offer(new Task("Implement Feature", 2));
        queue.offer(new Task("Code Review", 3));

        while (!queue.isEmpty()) {
            System.out.println("Executing: " + queue.poll());
        }
    }
}
```

### ✅ Output

```
Executing: Fix Prod Bug (priority 1)
Executing: Implement Feature (priority 2)
Executing: Code Review (priority 3)
Executing: Write Unit Tests (priority 4)
```

---

## 🎯 Summary

| Feature    | Queue                          |
| ---------- | ------------------------------ |
| Order      | FIFO                           |
| Duplicates | ✅ Allowed                      |
| Nulls      | ❌ In PriorityQueue, ArrayDeque |
| Best Impl  | **ArrayDeque**                 |
| Where used | Scheduling, messaging, BFS     |

---
