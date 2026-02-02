# Reactive Programming with Spring WebFlux 

---

## 1. Why Reactive Programming?

### Traditional (Blocking) Model – Problem

```
Client Request
   ↓
Thread (blocked)
   ↓
DB / API Call (wait)
   ↓
Response
```

Issues:

* One thread per request
* Threads get blocked waiting for I/O
* Poor scalability under high load

---

### Reactive Model – Solution

```
Client Request
   ↓
Event Loop (few threads)
   ↓
Non-blocking I/O
   ↓
Callback when data is ready
```

Benefits:

* Better CPU utilization
* Handles **more concurrent users**
* Backpressure support

---

## 2. What is Reactive Programming?

> A **non-blocking, asynchronous, event-driven** programming paradigm that reacts to data streams.

Core principles:

* **Asynchronous**
* **Non-blocking**
* **Stream-oriented**
* **Backpressure-aware**

---

## 3. Reactive Streams Specification

Reactive Streams defines **4 interfaces**:

| Interface      | Purpose                |
| -------------- | ---------------------- |
| `Publisher`    | Emits data             |
| `Subscriber`   | Consumes data          |
| `Subscription` | Controls demand        |
| `Processor`    | Publisher + Subscriber |

➡️ Spring WebFlux uses **Project Reactor**, which implements Reactive Streams.

---

## 4. Project Reactor Basics

### Core Types

| Type      | Meaning         |
| --------- | --------------- |
| `Mono<T>` | 0 or 1 element  |
| `Flux<T>` | 0 to N elements |

---

### Example

```java
Mono<String> mono = Mono.just("Hello");

Flux<Integer> flux = Flux.just(1, 2, 3);
```

Nothing executes yet — **lazy execution**.

---

## 5. Spring WebFlux Overview

Spring WebFlux is:

* Reactive web framework
* Alternative to Spring MVC
* Built on **Reactor**

---

### WebFlux Execution Models

| Stack         | Server                     |
| ------------- | -------------------------- |
| Servlet-based | Tomcat (non-blocking mode) |
| Reactive      | Netty (default)            |

---

## 6. WebFlux vs Spring MVC

| Feature           | MVC                | WebFlux          |
| ----------------- | ------------------ | ---------------- |
| Programming model | Blocking           | Non-blocking     |
| Thread usage      | Thread-per-request | Event loop       |
| Return type       | Object             | Mono / Flux      |
| Best for          | Traditional apps   | High concurrency |

---

## 7. WebFlux Project Setup

### Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

Netty is included by default.

---

## 8. WebFlux Programming Models

### 8.1 Annotation-Based (Like MVC)

```java
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello WebFlux");
    }
}
```

---

### 8.2 Functional Endpoints (Router + Handler)

```java
@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(Handler handler) {
        return RouterFunctions.route()
            .GET("/hello", handler::hello)
            .build();
    }
}
```

Handler:

```java
@Component
public class Handler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok()
                .bodyValue("Hello Functional WebFlux");
    }
}
```

---

## 9. Mono & Flux in Depth

### Creating Mono

```java
Mono.just("A");
Mono.empty();
Mono.fromCallable(() -> "DB Result");
```

---

### Creating Flux

```java
Flux.fromIterable(List.of(1,2,3));
Flux.interval(Duration.ofSeconds(1));
```

---

### Operators (Transformations)

```java
Flux.just(1,2,3)
    .map(i -> i * 2)
    .filter(i -> i > 2)
    .subscribe(System.out::println);
```

---

## 10. Backpressure (Critical Concept)

> Ability of consumer to tell producer **how much data it can handle**.

Example:

```java
Flux.range(1, 100)
    .limitRate(10)
    .subscribe(System.out::println);
```

Prevents memory overload.

---

## 11. Reactive Database Access

### R2DBC (Reactive DB)

Blocking JDBC ❌
Reactive R2DBC ✅

Dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>
```

Repository:

```java
public interface UserRepository
        extends ReactiveCrudRepository<User, Integer> {
}
```

---

## 12. Calling External APIs (Non-Blocking)

### WebClient

```java
WebClient client = WebClient.create("http://service");

Mono<String> response =
    client.get()
          .uri("/data")
          .retrieve()
          .bodyToMono(String.class);
```

---

## 13. Error Handling in WebFlux

### On Error Resume

```java
Mono.just(10)
    .map(i -> i / 0)
    .onErrorResume(e -> Mono.just(-1));
```

---

### Controller Level

```java
@GetMapping("/user/{id}")
public Mono<User> getUser(@PathVariable int id) {
    return service.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Not Found")));
}
```

---

## 14. Threading Model (Schedulers)

| Scheduler          | Use Case       |
| ------------------ | -------------- |
| `parallel()`       | CPU work       |
| `boundedElastic()` | Blocking calls |
| `immediate()`      | Current thread |

Example:

```java
Mono.fromCallable(() -> blockingCall())
    .subscribeOn(Schedulers.boundedElastic());
```

---

## 15. Mixing Blocking Code (Important Warning)

❌ Do NOT call JDBC directly
✔ Wrap blocking calls:

```java
Mono.fromCallable(() -> jdbcCall())
    .subscribeOn(Schedulers.boundedElastic());
```

---

## 16. Reactive Testing

```java
StepVerifier.create(flux)
    .expectNext(1)
    .expectNext(2)
    .verifyComplete();
```

---

## 17. Real-World Use Cases

* API Gateways
* Streaming APIs
* High-traffic microservices
* SSE / WebSockets

---

## 18. Common Mistakes (Interview Gold)

* Blocking inside reactive flow
* Using `block()`
* Not handling backpressure
* Mixing MVC and WebFlux incorrectly

---

## 19. When NOT to Use WebFlux

* Simple CRUD apps
* Heavy blocking DB usage
* Low traffic systems

---

## 20. Summary

* WebFlux = Reactive + Non-Blocking
* Built on Project Reactor
* Mono / Flux are lazy streams
* Netty provides event loop
* Best for high concurrency systems

---
