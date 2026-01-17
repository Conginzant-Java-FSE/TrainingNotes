## 1. Deadlock in Java

### 1.1 What is Deadlock?

A **deadlock** occurs when **two or more threads are blocked forever**, each waiting for a resource held by another thread.

 No thread can proceed, and the program gets stuck.

---

### 1.2 Real-World Analogy

Two people are standing in a narrow corridor:

* Person A will move only if Person B moves first.
* Person B will move only if Person A moves first.

Both wait forever → **Deadlock**

---

### 1.3 Necessary Conditions for Deadlock (Coffman Conditions)

Deadlock occurs **only if all four conditions are true**:

1. **Mutual Exclusion**
   Resource can be held by only one thread at a time.

2. **Hold and Wait**
   Thread holds one resource and waits for another.

3. **No Preemption**
   Resource cannot be forcibly taken.

4. **Circular Wait**
   Thread A waits for Thread B, and Thread B waits for Thread A.

---

### 1.4 Deadlock Example in Java

```java
class DeadlockDemo {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock1");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (lock2) {
                    System.out.println("Thread 1: Holding lock2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Holding lock2");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (lock1) {
                    System.out.println("Thread 2: Holding lock1");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
```

### 1.5 What Happens Here?

* Thread 1 holds `lock1` and waits for `lock2`
* Thread 2 holds `lock2` and waits for `lock1`
* Circular wait → **Deadlock**

---

### 1.6 How to Prevent Deadlock

#### 1. Consistent Lock Ordering (Best Practice)

```java
synchronized (lock1) {
    synchronized (lock2) {
        // safe code
    }
}
```

#### 2. Avoid Nested Locks

Use fewer synchronized blocks.

#### 3. Use `tryLock()` with timeout (ReentrantLock)

```java
lock1.tryLock(1, TimeUnit.SECONDS);
```

#### 4. Use High-Level Concurrency APIs

* `ExecutorService`
* `ConcurrentHashMap`
* `Semaphore`

---

### 1.7 Deadlock Interview Keywords

* Circular wait
* Nested synchronization
* Thread dump
* `jstack` command

---

## 2. Livelock in Java

### 2.1 What is Livelock?

A **livelock** occurs when:

* Threads are **not blocked**
* Threads keep **responding to each other**
* But **no real progress is made**

 Threads are active but stuck in a loop.

---

### 2.2 Real-World Analogy

Two people try to pass each other:

* Person A steps left
* Person B steps right
* They keep adjusting politely
* No one passes → **Livelock**

---

### 2.3 Livelock Example in Java

```java
class LivelockDemo {

    static class Worker {
        private boolean active = true;

        public boolean isActive() {
            return active;
        }

        public void work(Worker other) {
            while (active) {
                if (other.isActive()) {
                    System.out.println(Thread.currentThread().getName() +
                            " : Waiting for other worker");
                    try { Thread.sleep(100); } catch (Exception e) {}
                    continue;
                }
                System.out.println(Thread.currentThread().getName() +
                        " : Working now");
                active = false;
            }
        }
    }

    public static void main(String[] args) {

        Worker w1 = new Worker();
        Worker w2 = new Worker();

        new Thread(() -> w1.work(w2), "Worker-1").start();
        new Thread(() -> w2.work(w1), "Worker-2").start();
    }
}
```

### 2.4 What Happens Here?

* Both threads keep checking the other
* Both keep yielding
* No one finishes work
* CPU usage is high
   **Livelock**

---

### 2.5 How to Prevent Livelock

#### 1. Add Random Delays (Backoff Strategy)

```java
Thread.sleep(new Random().nextInt(200));
```

#### 2. Limit Retries

Break after certain attempts.

#### 3. Avoid Excessive Politeness

Do not always give up resources immediately.

---

## 3. Deadlock vs Livelock

| Aspect       | Deadlock         | Livelock          |
| ------------ | ---------------- | ----------------- |
| Thread state | Blocked          | Running           |
| CPU usage    | Low              | High              |
| Progress     | None             | None              |
| Cause        | Circular waiting | Over-coordination |
| Detection    | Thread dump      | Harder to detect  |

---

## 4. Interview-Oriented Summary

* **Deadlock**: Threads are blocked forever due to circular resource dependency.
* **Livelock**: Threads are active but continuously responding to each other without progress.
* Deadlock can be detected using **thread dumps**.
* Livelock requires **design-level fixes**, not just locks.
