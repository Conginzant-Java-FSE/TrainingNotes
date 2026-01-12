
# POLYMORPHISM IN JAVA 
## WHAT IS POLYMORPHISM?

**Polymorphism** means **“many forms”**.

In Java, polymorphism allows **the same method name** to behave **differently** based on:

* the **type of object**, or
* the **method signature**

In simple words:

> **One interface, multiple behaviors**

---

## WHY POLYMORPHISM IS IMPORTANT?

Polymorphism enables:

* Loose coupling
* Code extensibility
* Runtime flexibility
* Cleaner design using OOP principles

Without polymorphism, Java would become:

* Hard to maintain
* Full of `if-else` and `instanceof`
* Rigid and non-scalable

---

## REAL-WORLD ANALOGY

Consider a **Payment system**:

* `pay()` using **Credit Card**
* `pay()` using **UPI**
* `pay()` using **Net Banking**

Same action → **different implementations**

That is **polymorphism**.

---

## TYPES OF POLYMORPHISM IN JAVA

Java supports **two types** of polymorphism:

1. **Compile-Time Polymorphism** (Static Polymorphism)

   * Method Overloading
2. **Runtime Polymorphism** (Dynamic Polymorphism)

   * Method Overriding + Inheritance

---

# 1️⃣ COMPILE-TIME POLYMORPHISM (METHOD OVERLOADING)

## THEORY

Method overloading means:

* **Same method name**
* **Different parameter list**
* Happens at **compile time**

The compiler decides **which method to call** based on:

* Number of parameters
* Type of parameters
* Order of parameters

---

## RULES OF METHOD OVERLOADING

✔ Method name must be same
✔ Parameter list must be different
- Return type alone is NOT sufficient
- Access modifier does not matter

---

## VALID OVERLOADING EXAMPLES

### Example 1: Different number of parameters

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

Usage:

```java
Calculator calc = new Calculator();
System.out.println(calc.add(10, 20));       // 30
System.out.println(calc.add(10, 20, 30));   // 60
```

---

### Example 2: Different data types

```java
class Calculator {

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b) {
        return a + b;
    }
}
```

---

###  INVALID OVERLOADING (COMMON INTERVIEW TRAP)

```java
int add(int a, int b) { return a + b; }
double add(int a, int b) { return a + b; }  //  Compile-time error
```

Reason:

> Return type alone cannot differentiate overloaded methods

---

## WHERE OVERLOADING IS USED INTERNALLY?

* `System.out.println()` → overloaded for all data types
* Constructors
* Utility APIs

---

# 2️⃣ RUNTIME POLYMORPHISM (METHOD OVERRIDING)

## THEORY

Runtime polymorphism means:

* Method call resolution happens at **runtime**
* Achieved using:

  * **Inheritance**
  * **Method Overriding**
  * **Parent reference → Child object**

This is also called:

> **Dynamic Method Dispatch**

---

## BASIC CONCEPT

```java
Parent ref = new Child();
ref.method();
```

👉 JVM decides **which method to execute at runtime** based on the **object**, not the reference.

---

## RULES OF METHOD OVERRIDING

✔ Same method name
✔ Same parameter list
✔ IS-A relationship (inheritance required)
✔ Return type can be **same or covariant**
✔ Access level cannot be reduced
 `static`, `final`, `private` methods cannot be overridden

---

## SIMPLE OVERRIDING EXAMPLE

```java
class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}
```

Usage:

```java
Animal a = new Dog();
a.sound();
```

Output:

```
Dog barks
```

✔ Method decided at **runtime**
✔ Reference type = `Animal`
✔ Object type = `Dog`

---

## WHY THIS IS POLYMORPHISM?

Because:

* Same method call → `sound()`
* Different behavior → based on object

---

## REALISTIC EXAMPLE (INTERVIEW-FRIENDLY)

### Payment System Example

```java
class Payment {
    void pay() {
        System.out.println("Processing payment");
    }
}

class UpiPayment extends Payment {
    void pay() {
        System.out.println("Processing UPI payment");
    }
}

class CreditCardPayment extends Payment {
    void pay() {
        System.out.println("Processing Credit Card payment");
    }
}
```

Usage:

```java
Payment payment;

payment = new UpiPayment();
payment.pay();

payment = new CreditCardPayment();
payment.pay();
```

Output:

```
Processing UPI payment
Processing Credit Card payment
```

---

## KEY POINT (VERY IMPORTANT)

> **Method overriding works only for instance methods, not static methods**

---

# STATIC METHODS AND POLYMORPHISM (INTERVIEW TRAP)

Static methods are **method hiding**, not overriding.

```java
class Parent {
    static void show() {
        System.out.println("Parent show");
    }
}

class Child extends Parent {
    static void show() {
        System.out.println("Child show");
    }
}
```

Usage:

```java
Parent p = new Child();
p.show();
```

Output:

```
Parent show
```

✔ Static methods are resolved at **compile time**
✔ No runtime polymorphism

---

# POLYMORPHISM WITH `final` METHODS

```java
class Parent {
    final void display() {
        System.out.println("Final method");
    }
}

class Child extends Parent {
    //  Cannot override final method
}
```

---

# POLYMORPHISM WITH `private` METHODS

Private methods:

* Are **not visible** to child
* Cannot be overridden

```java
class Parent {
    private void test() {
        System.out.println("Parent test");
    }
}
```

---

# POLYMORPHISM + INTERFACES (MOST IMPORTANT)

Interfaces provide **100% runtime polymorphism**

```java
interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle implements Shape {
    public void draw() {
        System.out.println("Drawing Rectangle");
    }
}
```

Usage:

```java
Shape shape;

shape = new Circle();
shape.draw();

shape = new Rectangle();
shape.draw();
```

---

## WHY INTERFACE POLYMORPHISM IS POWERFUL?

* No implementation dependency
* Follows **Open/Closed Principle**
* Used heavily in:

  * Spring
  * Hibernate
  * Microservices
  * Design patterns

---

# METHOD OVERLOADING vs OVERRIDING (INTERVIEW TABLE)

| Aspect            | Overloading   | Overriding       |
| ----------------- | ------------- | ---------------- |
| Polymorphism Type | Compile-time  | Runtime          |
| Inheritance       | Not required  | Required         |
| Method Signature  | Different     | Same             |
| Return Type       | Any           | Same / covariant |
| Binding           | Early binding | Late binding     |

---

# COMMON INTERVIEW QUESTIONS

### Q1: Can constructor be overridden?

 No (constructors are not inherited)

### Q2: Can main() be overloaded?

✔ Yes

### Q3: Can main() be overridden?

 No (static method)

### Q4: Why runtime polymorphism is called dynamic binding?

Because method resolution happens **at runtime based on object**

---

# SUMMARY

Polymorphism in Java:

* Allows one interface, many implementations
* Improves flexibility and maintainability
* Exists as:

  * **Compile-time (Overloading)**
  * **Runtime (Overriding)**

> **If you understand polymorphism well, you understand the heart of Java OOP.**

