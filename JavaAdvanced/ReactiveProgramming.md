# Reactive Programming 

---

## 1. What is Reactive Programming?

Reactive Programming is a paradigm for building **asynchronous**, **non-blocking**, and **event-driven** applications.

Instead of waiting for results, your program **reacts when data becomes available**.

### Traditional (Imperative)

* Method call
* Thread blocks
* Result returned

### Reactive

* Define a pipeline
* Subscribe
* Data is pushed asynchronously

---

## 2. Why Reactive Programming?

Traditional systems struggle when:

* Many users access the system concurrently
* Threads are blocked on I/O (DB, HTTP, files)
* System becomes unresponsive under load

Reactive systems:

* Use fewer threads
* Do not block while waiting for I/O
* Scale better with the same resources

---

## 3. Key Characteristics

Reactive programming emphasizes:

* **Asynchronous execution**
* **Non-blocking I/O**
* **Event-driven flow**
* **Lazy execution**

Nothing runs until someone **subscribes**.

---

## 4. Reactive Types (Project Reactor)

In Java (Project Reactor), reactive programming is based on two main types:

| Type      | Description               |
| --------- | ------------------------- |
| `Mono<T>` | Emits zero or one value   |
| `Flux<T>` | Emits zero to many values |

---

## 5. Domain Model (Class-Based Example)

```java
class Order {
    private final String id;
    private final double amount;

    Order(String id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
}
```

---

## 6. Creating Reactive Data Sources

### Mono Example

```java
Mono<Order> orderMono =
        Mono.just(new Order("ORD-1", 5000));
```

### Flux Example

```java
Flux<Order> orderFlux =
        Flux.just(
            new Order("ORD-1", 5000),
            new Order("ORD-2", 12000),
            new Order("ORD-3", 3000)
        );
```

 At this stage, **nothing executes**.

---

## 7. Subscribing to a Stream

```java
orderFlux.subscribe(order ->
    System.out.println(order.getId())
);
```

* Subscription **triggers execution**
* Without `subscribe()`, nothing happens

---

## 8. Transforming Data with Operators

### 8.1 `map` – Transform Objects

```java
Flux<Double> orderAmounts =
    orderFlux.map(Order::getAmount);
```

---

### 8.2 `filter` – Apply Conditions

```java
Flux<Order> highValueOrders =
    orderFlux.filter(order -> order.getAmount() > 5000);
```

---

### 8.3 Chaining Operators

```java
orderFlux
    .filter(order -> order.getAmount() > 5000)
    .map(order -> "High value order: " + order.getId())
    .subscribe(System.out::println);
```

---

## 9. Non-Blocking Execution Behavior

```java
System.out.println("Start");

orderFlux.subscribe(order ->
    System.out.println(order.getId())
);

System.out.println("End");
```

### Possible Output

```
Start
End
ORD-1
ORD-2
ORD-3
```

The main thread continues execution without waiting.

---

## 10. Simulating Asynchronous I/O

```java
Flux<Order> asyncOrders =
    Flux.fromIterable(loadOrders())
        .delayElements(Duration.ofSeconds(1));
```

```java
private static List<Order> loadOrders() {
    return List.of(
        new Order("ORD-1", 5000),
        new Order("ORD-2", 12000)
    );
}
```

* Thread is **released during delay**
* Values arrive asynchronously

---

## 11. Error Handling in Reactive Pipelines

### Error Scenario

```java
Flux<Order> faultyOrders =
    Flux.just(
        new Order("ORD-1", 5000),
        null
    );
```

---

### Graceful Error Handling

```java
faultyOrders
    .onErrorResume(ex ->
        Flux.just(new Order("DEFAULT", 0))
    )
    .subscribe(order ->
        System.out.println(order.getId())
    );
```

---

## 12. Observing Stream Lifecycle Events

```java
orderFlux
    .doOnSubscribe(sub ->
        System.out.println("Subscription started")
    )
    .doOnNext(order ->
        System.out.println("Processing " + order.getId())
    )
    .doOnComplete(() ->
        System.out.println("Stream completed")
    )
    .subscribe();
```

---

## 13. Realistic Service-Oriented Example

```java
class OrderService {

    Flux<Order> getOrders() {
        return Flux.just(
            new Order("ORD-1", 5000),
            new Order("ORD-2", 12000),
            new Order("ORD-3", 3000)
        );
    }
}
```

### Reactive Usage

```java
OrderService service = new OrderService();

service.getOrders()
    .filter(order -> order.getAmount() > 4000)
    .map(order -> "Approved order: " + order.getId())
    .subscribe(System.out::println);
```

---

## 14. Reactive vs Imperative (Conceptual)

| Imperative Style     | Reactive Style         |
| -------------------- | ---------------------- |
| Blocking calls       | Non-blocking flow      |
| Sequential execution | Event-driven execution |
| Thread-heavy         | Resource-efficient     |

---

## 15. Key Takeaways (Mental Model)

* Reactive streams are **lazy**
* Execution starts only on **subscription**
* Data flows through **operators**
* Threads are not blocked
* Code describes **what to do when data arrives**

---
