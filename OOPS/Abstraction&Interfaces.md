# ABSTRACTION IN JAVA 

## WHAT IS ABSTRACTION?

**Abstraction** is the process of:

> **Hiding implementation details and exposing only essential behavior**

It focuses on:

* **What an object does**
* Not **how it does it**

---

## WHY ABSTRACTION IS IMPORTANT?

Abstraction helps to:

* Reduce complexity
* Improve maintainability
* Increase security
* Enable loose coupling
* Enforce contracts in code

Without abstraction:

* Internal logic leaks to users
* Code becomes rigid and tightly coupled

---

## HOW JAVA SUPPORTS ABSTRACTION?

Java provides **two mechanisms**:

1. **Abstract Classes**
2. **Interfaces**

---

# 1. ABSTRACT CLASS

## THEORY

An **abstract class**:

* Is declared using the `abstract` keyword
* Can contain both:

  * Abstract methods (no body)
  * Concrete methods (with body)
* Cannot be instantiated

It represents a **partially implemented base class**.

---

## SYNTAX

```java
abstract class Vehicle {
    abstract void start();
    void fuel() {
        System.out.println("Needs fuel");
    }
}
```

---

## IMPLEMENTATION EXAMPLE

```java
class Car extends Vehicle {
    @Override
    void start() {
        System.out.println("Car starts with key");
    }
}
```

Usage:

```java
Vehicle v = new Car();
v.start();
v.fuel();
```

Output:

```
Car starts with key
Needs fuel
```

---

## KEY RULES OF ABSTRACT CLASS

* Can have abstract and non-abstract methods
* Can have constructors and instance variables
* Cannot be instantiated
* Child class must implement all abstract methods
* Supports inheritance but not multiple inheritance

---

## ABSTRACT CLASS WITH MULTIPLE CHILD CLASSES

```java
class Bike extends Vehicle {
    void start() {
        System.out.println("Bike starts with kick");
    }
}
```

```java
Vehicle v = new Bike();
v.start();
```

---

## WHEN TO USE ABSTRACT CLASS?

* When classes are closely related
* When partial implementation is required
* When base class needs shared logic or state

---

# 2. INTERFACE

## THEORY

An **interface** defines a contract:

> What a class **must do**, not **how it does it**

Before Java 8:

* Only abstract methods
* Full abstraction

---

## BASIC INTERFACE SYNTAX

```java
interface Payment {
    void pay();
}
```

---

## IMPLEMENTATION EXAMPLE

```java
class UpiPayment implements Payment {
    public void pay() {
        System.out.println("Paying via UPI");
    }
}
```

Usage:

```java
Payment p = new UpiPayment();
p.pay();
```

---

## MULTIPLE IMPLEMENTATIONS

```java
class CardPayment implements Payment {
    public void pay() {
        System.out.println("Paying via Card");
    }
}
```

```java
Payment p1 = new UpiPayment();
Payment p2 = new CardPayment();
```

---

## KEY RULES OF INTERFACE

* All methods are `public` and `abstract` by default
* Variables are `public static final`
* Supports multiple inheritance
* No constructors
* No instance variables

---

## INTERFACE FEATURES (JAVA 8+)

### Default Method

```java
interface Logger {
    default void log() {
        System.out.println("Logging");
    }
}
```

### Static Method

```java
interface Utility {
    static void help() {
        System.out.println("Helping");
    }
}
```

---

## WHEN TO USE INTERFACE?

* When multiple inheritance is needed
* When designing APIs
* When loose coupling is required
* When behavior may vary across implementations

---

# ABSTRACT CLASS vs INTERFACE (INTERVIEW COMPARISON)

| Feature              | Abstract Class | Interface |
| -------------------- | -------------- | --------- |
| Abstraction          | Partial        | Full      |
| Multiple Inheritance | No             | Yes       |
| Constructors         | Yes            | No        |
| Instance Variables   | Yes            | No        |
| Use Case             | Base class     | Contract  |

---

# ABSTRACTION + POLYMORPHISM (REAL DESIGN)

```java
interface Notification {
    void send();
}

class EmailNotification implements Notification {
    public void send() {
        System.out.println("Sending Email");
    }
}
```

```java
Notification n = new EmailNotification();
n.send();
```

This design:

* Hides implementation
* Enables runtime polymorphism
* Follows Open/Closed Principle

---

# COMMON INTERVIEW QUESTIONS

* Can we create object of abstract class? → No
* Can abstract class have constructor? → Yes
* Can interface have methods with body? → Yes (default, static)
* Can interface support multiple inheritance? → Yes

---

# SUMMARY

* Abstraction hides implementation details
* Achieved using abstract classes and interfaces
* Abstract class = partial abstraction
* Interface = full abstraction
* Works best with polymorphism
* Core foundation for frameworks like Spring

---
