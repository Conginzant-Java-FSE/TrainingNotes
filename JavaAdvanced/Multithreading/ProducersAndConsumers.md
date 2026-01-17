

# **Producer–Consumer Problem in Java**

## **1. Introduction**

The **Producer–Consumer problem** is a classic example of a **multi-threading synchronization problem**.
It involves two types of threads:

* **Producer:** Generates data and puts it into a shared buffer.
* **Consumer:** Fetches data from the buffer and processes it.

The challenge is to ensure that:

* The **producer waits** if the buffer is **full**.
* The **consumer waits** if the buffer is **empty**.
* Both access the shared buffer in a **thread-safe** manner.

---

## **2. Approach 1: Using `wait()` and `notify()`**

This is the **low-level synchronization mechanism** in Java.
You use the **object’s intrinsic lock** (monitor) along with:

* `wait()` → causes the thread to wait until another thread calls `notify()` or `notifyAll()`.
* `notify()` → wakes up one waiting thread.
* `synchronized` keyword → ensures mutual exclusion.

---

### **2.1 Code Example**

```java
import java.util.LinkedList;
import java.util.Queue;

class SharedBuffer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;

    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int value) throws InterruptedException {
        while (buffer.size() == capacity) {
            System.out.println("Buffer full. Producer waiting...");
            wait();  // release lock and wait
        }

        buffer.add(value);
        System.out.println("Produced: " + value);
        notifyAll(); // notify consumers waiting
    }

    public synchronized int consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("Buffer empty. Consumer waiting...");
            wait();  // release lock and wait
        }

        int value = buffer.remove();
        System.out.println("Consumed: " + value);
        notifyAll(); // notify producers waiting
        return value;
    }
}

class Producer extends Thread {
    private SharedBuffer buffer;

    public Producer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int value = 0;
        try {
            while (true) {
                buffer.produce(value++);
                Thread.sleep(400);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer extends Thread {
    private SharedBuffer buffer;

    public Consumer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.consume();
                Thread.sleep(600);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerWaitNotifyExample {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(5);
        new Producer(buffer).start();
        new Consumer(buffer).start();
    }
}
```

---

### **2.2 Explanation**

1. **`SharedBuffer`**

    * Acts as the **shared resource** between producer and consumer.
    * `produce()` waits if the buffer is full.
    * `consume()` waits if the buffer is empty.
    * Both are synchronized to ensure only one thread executes at a time.

2. **Synchronization Logic**

    * `wait()` releases the lock and pauses the thread.
    * `notifyAll()` wakes up threads that are waiting on the same monitor.
    * `while` loop ensures re-checking the condition after waking up (to avoid spurious wakeups).

3. **Output Example (interleaved)**

   ```
   Produced: 0
   Produced: 1
   Consumed: 0
   Produced: 2
   Consumed: 1
   Buffer full. Producer waiting...
   Consumed: 4
   Produced: 5
   ...
   ```

---

### **2.3 Pros and Cons**

| Pros                                  | Cons                                       |
| ------------------------------------- | ------------------------------------------ |
| Fine control over synchronization     | Complex to implement correctly             |
| Good for learning low-level threading | Risk of deadlocks and missed notifications |
| Works in core Java (no extra imports) | Harder to scale or debug                   |

---

## **3. Approach 2: Using `BlockingQueue` (High-Level Concurrency)**

Java’s **`java.util.concurrent`** package simplifies thread communication.
The **`BlockingQueue`** interface (and its implementations like `ArrayBlockingQueue`, `LinkedBlockingQueue`) handles synchronization internally.

### **Key Advantages**

* No need to use `wait()` or `notify()`.
* Thread-safe by design.
* Built-in blocking behavior.

---

### **3.1 Code Example**

```java
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ProducerBQ implements Runnable {
    private BlockingQueue<Integer> queue;

    public ProducerBQ(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int value = 0;
        try {
            while (true) {
                System.out.println("Producing: " + value);
                queue.put(value++); // blocks if queue is full
                Thread.sleep(400);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ConsumerBQ implements Runnable {
    private BlockingQueue<Integer> queue;

    public ConsumerBQ(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int value = queue.take(); // blocks if queue is empty
                System.out.println("Consumed: " + value);
                Thread.sleep(600);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerBlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        Thread producer = new Thread(new ProducerBQ(queue));
        Thread consumer = new Thread(new ConsumerBQ(queue));

        producer.start();
        consumer.start();
    }
}
```

---

### **3.2 Explanation**

1. **BlockingQueue Mechanics**

    * `put()` blocks the producer if the queue is full.
    * `take()` blocks the consumer if the queue is empty.
    * Internal synchronization eliminates explicit `wait()` / `notify()`.

2. **Implementation Classes**

    * `ArrayBlockingQueue` → bounded, array-based, FIFO order.
    * `LinkedBlockingQueue` → optionally bounded, linked-node based.
    * `PriorityBlockingQueue` → orders elements according to priority.

3. **Output Example**

   ```
   Producing: 0
   Producing: 1
   Consumed: 0
   Producing: 2
   Consumed: 1
   Producing: 3
   ...
   ```

---

### **3.3 Comparison of Both Approaches**

| Feature                      | `wait()` / `notify()` | `BlockingQueue`      |
| ---------------------------- | --------------------- | -------------------- |
| **Complexity**               | High                  | Low                  |
| **Thread Safety**            | Manual                | Built-in             |
| **Readability**              | Moderate              | High                 |
| **Risk of Deadlock**         | Possible              | Avoided              |
| **Performance**              | Slightly lower        | Optimized internally |
| **Preferred in Modern Java** | No                    | Yes                  |

---

## **4. Summary**

| Concept                 | Description                                                |
| ----------------------- | ---------------------------------------------------------- |
| **Goal**                | Synchronize producer and consumer threads safely           |
| **Low-Level Approach**  | `wait()` and `notify()` for manual thread coordination     |
| **High-Level Approach** | `BlockingQueue` for automatic blocking and synchronization |
| **Recommended**         | `BlockingQueue` (clean, maintainable, safer)               |

---

## **5. Practical Use Cases**

* Logging systems (log producers and log processors)
* Data pipelines (data ingestion → processing)
* Messaging systems (task queues)
* Thread pool task submission and execution

---
