
## **1. Introduction to Multithreading in Java**

**Multithreading** in Java allows concurrent execution of two or more parts of a program, known as **threads**. Each thread runs independently, sharing the same memory space but executing different tasks simultaneously.

It helps in:

* Improving CPU utilization.
* Enhancing performance for I/O-bound or computational tasks.
* Keeping applications responsive.

A **process** has its own memory space, while **threads** within a process share memory and resources (heap, method area).

---

## **2. Thread Lifecycle**

A thread in Java passes through several states defined by the `Thread.State` enumeration:

| State             | Description                                                                       |
| ----------------- | --------------------------------------------------------------------------------- |
| **NEW**           | Thread is created but not yet started.                                            |
| **RUNNABLE**      | Thread is ready to run or currently running.                                      |
| **BLOCKED**       | Thread is waiting to acquire a monitor lock.                                      |
| **WAITING**       | Thread is waiting indefinitely for another thread to perform a particular action. |
| **TIMED_WAITING** | Thread is waiting for another thread for a specified period.                      |
| **TERMINATED**    | Thread has finished execution.                                                    |

---

## **3. Key Methods in `Thread` Class**

| Method                               | Description                                                  |
| ------------------------------------ | ------------------------------------------------------------ |
| `start()`                            | Starts a new thread and calls the `run()` method internally. |
| `run()`                              | Defines the code to be executed in a thread.                 |
| `sleep(long millis)`                 | Pauses the current thread for specified milliseconds.        |
| `join()`                             | Waits for a thread to complete before continuing execution.  |
| `getName()` / `setName(String)`      | Gets or sets the name of a thread.                           |
| `getPriority()` / `setPriority(int)` | Gets or sets the priority of a thread (1–10).                |
| `isAlive()`                          | Checks if a thread is still running.                         |
| `interrupt()`                        | Interrupts a sleeping or waiting thread.                     |

---

## **4. Different Ways to Create Threads**

There are **three main ways** to create threads in Java:

---

### **4.1 Extending the `Thread` Class**

The simplest method is to create a subclass of `Thread` and override the `run()` method.

#### **Example**

```java
class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName() + " - Count: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}

public class ThreadExample1 {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        t1.setName("Worker-1");
        t2.setName("Worker-2");

        t1.start();
        t2.start();
    }
}
```

#### **Explanation**

* `start()` launches a new thread.
* Each thread runs the `run()` method concurrently.
* Threads share CPU time and may execute interleaved output.

---

### **4.2 Implementing the `Runnable` Interface**

The recommended approach for better design (since Java supports single inheritance).

#### **Example**

```java
class Task implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName() + " executing iteration " + i);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}

public class ThreadExample2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Task(), "Alpha");
        Thread t2 = new Thread(new Task(), "Beta");

        t1.start();
        t2.start();
    }
}
```

#### **Explanation**

* The `Runnable` interface contains a single abstract method `run()`.
* The `Thread` object executes the logic defined in the `Runnable` implementation.
* This approach separates the **task definition** from **thread control**, promoting reusability.

---

### **4.3 Using `Callable` and `Future` (with ExecutorService)**

The `Callable` interface is similar to `Runnable` but:

* It can **return a result**.
* It can **throw a checked exception**.

#### **Example**

```java
import java.util.concurrent.*;

class ComputeTask implements Callable<Integer> {
    private int n;

    ComputeTask(int n) {
        this.n = n;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
            Thread.sleep(200);
        }
        return sum;
    }
}

public class ThreadExample3 {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Integer> result1 = executor.submit(new ComputeTask(5));
        Future<Integer> result2 = executor.submit(new ComputeTask(10));

        System.out.println("Result 1: " + result1.get());
        System.out.println("Result 2: " + result2.get());

        executor.shutdown();
    }
}
```

#### **Explanation**

* `ExecutorService` manages a pool of threads.
* `submit()` executes a `Callable` task asynchronously.
* `Future.get()` blocks until the computation is done and returns the result.

---

## **5. Comparison of Approaches**

| Approach               | Return Value | Exception Handling       | Reusability | Suitable For                 |
| ---------------------- | ------------ | ------------------------ | ----------- | ---------------------------- |
| **Thread Class**       | No           | No checked exception     | Low         | Simple concurrent tasks      |
| **Runnable Interface** | No           | No checked exception     | High        | General-purpose threading    |
| **Callable Interface** | Yes          | Yes (checked exceptions) | High        | Concurrent computation tasks |

---

## **6. Thread Priority and Scheduling**

Threads are scheduled by the **JVM thread scheduler** based on **priority** and **time slicing** (implementation-dependent).

```java
thread.setPriority(Thread.MAX_PRIORITY);   // 10
thread.setPriority(Thread.MIN_PRIORITY);   // 1
thread.setPriority(Thread.NORM_PRIORITY);  // 5
```

However, priority **does not guarantee execution order**, only a hint to the scheduler.

---

## **7. Thread Synchronization (Brief Overview)**

When multiple threads access shared resources, **race conditions** may occur. Java provides synchronization mechanisms like:

* **Synchronized blocks or methods**
* **Locks (java.util.concurrent.locks)**
* **Atomic variables**
* **Concurrent collections**

Example:

```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
```

This ensures only one thread can execute the `increment()` method at a time.

---

## **8. Summary**

| Concept                | Description                                         |
| ---------------------- | --------------------------------------------------- |
| **Thread**             | A lightweight sub-process for concurrent execution. |
| **Creation Methods**   | `Thread`, `Runnable`, `Callable`                    |
| **Executor Framework** | Manages thread pools efficiently.                   |
| **Synchronization**    | Prevents race conditions.                           |
| **Thread Lifecycle**   | NEW → RUNNABLE → WAITING/BLOCKED → TERMINATED       |

---
