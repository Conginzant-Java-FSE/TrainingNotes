# Java Virtual Threads (Project Loom) 

### Why Virtual Threads? (The Real-World Problem They Solve)

**Before Virtual Threads (Platform Threads):**
- One platform thread ≈ one OS thread ≈ 1–2 MB stack + expensive context switching
- Maximum realistic concurrency on a normal server: ~few thousand threads
- Blocking I/O (JDBC, HttpClient synchronous, File I/O, etc.) → each thread sits idle → waste of memory & CPU

**Virtual Threads (since Java 19 preview → stable in Java 21):**
- One virtual thread ≈ few hundred bytes
- You can comfortably create **hundreds of thousands or even millions** of virtual threads
- Blocking code no longer kills scalability
- Write simple, synchronous, readable code → but get Tomcat/Node.js-level throughput

Goal: Write code like it’s 1998 (synchronous & simple) but scale like it’s 2026.

### 1. Starting a Virtual Thread – The 3 Official Ways (Without Executors)

```java
public class VirtualThreadDemo {
    public static void main(String[] args) throws InterruptedException {

        // Way 1: Thread.ofVirtual().start(...)  ← Most common & recommended
        Thread vt1 = Thread.ofVirtual().start(() -> {
            System.out.println("Running in virtual thread 1: " + Thread.currentThread());
            sleepSilently(2000);
            System.out.println("Done 1");
        });

        // Way 2: Thread.startVirtualThread(...)  ← Static factory (Java 21+)
        Thread vt2 = Thread.startVirtualThread(() -> {
            System.out.println("Running in virtual thread 2: " + Thread.currentThread());
            sleepSilently(1000);
            System.out.println("Done 2");
        });

        // Way 3: Thread.Builder (fluent style) – useful when you want to set name
        Thread vt3 = Thread.ofVirtual()
                          .name("my-worker-")   // auto-incrementing: my-worker-0, my-worker-1...
                          .start(() -> {
                              System.out.println("Named virtual thread: " + Thread.currentThread());
                              sleepSilently(1500);
                              System.out.println("Done 3");
                          });

        // Don't forget to join if you want to wait (optional in real apps)
        vt1.join();
        vt2.join();
        vt3.join();

        System.out.println("All virtual threads finished");
    }

    private static void sleepSilently(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
```

Output example:
```
Running in virtual thread 1: Thread[#22,v1,my-worker-0]
Running in virtual thread 2: Thread[#23,v2,/192.168.1.10]
Named virtual thread: Thread[#24,my-worker-0,5,main]
Done 2
Done 3
Done 1
All virtual threads finished
```

Notice the thread names: they all have `VirtualThread[#id,...]/` prefix.

### 2. Key Characteristics of Virtual Threads

| Feature                     | Platform Thread          | Virtual Thread                          |
|-----------------------------|--------------------------|-----------------------------------------|
| Memory per thread           | 1–8 MB                   | ~few KB                                 |
| Creation cost               | High                     | Extremely low                           |
| Blocking cost               | Blocks OS thread         | "Parks" the virtual thread → OS thread is freed |
| Max realistic concurrency   | Few thousand             | Hundreds of thousands → millions        |
| Scheduler                   | OS                       | JVM (ForkJoinPool by default)           |
| Debugging / Thread dumps    | Normal                   | Shown as virtual threads (jstack works) |

### 3. Real-World Blocking Code That Magically Scales

```java
public class VirtualThreadWebServerSimulation {
    public static void main(String[] args) throws Exception {

        // Simulate 100_000 concurrent HTTP-like requests (each does blocking I/O)
        IntStream.range(0, 100_000).forEach(i -> {
            Thread.startVirtualThread(() -> {
                try {
                    // Simulate blocking external call (DB, REST, etc.)
                    URL url = new URL("https://httpbin.org/delay/1");
                    url.openConnection().getInputStream().readAllBytes();

                    System.out.println("Request " + i + " completed by " + Thread.currentThread());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        System.out.println("Launched 100k virtual threads... press Enter to exit");
        System.in.read();  // keep alive
    }
}
```

This program uses only ~50–200 MB RAM and finishes in ~1–2 seconds (because of carrier thread reuse).  
With platform threads it would crash with OutOfMemoryError.

### 4. Structured Concurrency (Java 21+) – The Future-Proof Way

Never use raw virtual threads in production without StructuredTaskScope (Java 21+).

```java
import java.util.concurrent.StructuredTaskScope;

public class StructuredVirtualThreads {
    public static void main(String[] args) throws Exception {
        
        try (var scope = new StructuredTaskScope<Void>()) {
            
            // Launch many virtual child threads
            for (int i = 0; i < 10; i++) {
                int taskId = i;
                scope.fork(() -> {
                    Thread.sleep(1000);
                    System.out.println("Task " + taskId + " done by " + Thread.currentThread());
                    return null;
                });
            }

            scope.join();           // Wait for all children
            System.out.println("All tasks completed successfully");
        } // auto-shutdown + auto-interrupt on failure (from Java 22 ShutdownOnFailure)
    }
}
```

Benefits:
- Automatic propagation of cancellation
- Automatic shutdown on first failure (with ShutdownOnFailure subclass)
- No leaked threads
- Cleaner than ThreadGroups or ExecutorService

### 5. What You Should NOT Do with Virtual Threads

```java
// DON'T do this – pins the carrier thread!
synchronized (someObject) {
    Thread.sleep(10_000);  // Blocks entire carrier thread for 10s → kills scalability
}

// DO this instead
LockSupport.parkNanos(...);  // or just normal Thread.sleep() is fine!
// Thread.sleep(), I/O, ReentrantLock, etc. are all "cooperative" → they yield the carrier thread
```

Good news: Almost all JDK blocking operations (Thread.sleep, Socket, FileChannel, JDBC, HttpClient synchronous, etc.) are automatically "virtual-thread-friendly" since Java 21.

### 6. When to Use Virtual Threads in Real Projects

Use them when you have:
- High number of concurrent requests/tasks (> 500–1000)
- Mostly blocking I/O (REST calls, DB via JDBC, message queues, file I/O)
- You want simple, readable synchronous code

Perfect for:
- REST APIs with Spring Boot 3.2+ / WebFlux not needed
- Microservices doing many outbound calls
- Batch processing with JDBC
- Web crawlers, report generators, etc.

### Summary – Quick Cheat Sheet

```java
// 1. Simple one-off
Thread.startVirtualThread(() -> { /* blocking code */ });

// 2. With name
Thread.ofVirtual().name("worker-").start(runnable);

// 3. Best for production (Java 21+)
try (var scope = new StructuredTaskScope<Void>()) {
    scope.fork(() -> { /* task */ return null; });
    scope.join();
}

// Never use ExecutorService.submit() with virtual threads unless you explicitly create it with virtual thread factory
// Wrong → creates platform threads inside pool
// Executors.newFixedThreadPool(200).submit(...)

// Correct if you really need ExecutorService
ExecutorService virtExec = Executors.newVirtualThreadPerTaskExecutor();
virtExec.submit(() -> { /* task */ });
```
