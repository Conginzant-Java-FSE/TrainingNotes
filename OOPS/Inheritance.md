# INHERITANCE IN JAVA 

## WHAT IS INHERITANCE

Inheritance is an OOPS concept where one class **acquires the properties (fields) and behaviors (methods)** of another class. The class that provides the features is called the **parent (superclass)**, and the class that inherits them is called the **child (subclass)**.

In Java, inheritance represents an **IS-A relationship**.

Example:
A `Car` **is a** `Vehicle`
A `SavingsAccount` **is a** `BankAccount`

---

## WHY INHERITANCE IS NEEDED

Inheritance helps in:

* **Code reusability** – common logic written once
* **Maintainability** – changes in parent reflect in children
* **Extensibility** – add new features without modifying existing code
* **Polymorphism** – runtime method binding
* **Hierarchical design** – structured and readable architecture

---

## BASIC SYNTAX OF INHERITANCE IN JAVA

```java
class Parent {
    // fields and methods
}

class Child extends Parent {
    // additional fields and methods
}
```

Keyword used: **`extends`**

---

## SIMPLE INHERITANCE EXAMPLE

```java
class Vehicle {
    void start() {
        System.out.println("Vehicle starts");
    }
}

class Car extends Vehicle {
    void drive() {
        System.out.println("Car is driving");
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.start();   // inherited method
        car.drive();   // own method
    }
}
```

---

## IMPORTANT RULES OF INHERITANCE IN JAVA

* Java supports **single inheritance using classes**
* A subclass inherits **public and protected members**
* **Private members are not inherited** (but accessible via methods)
* Constructors are **not inherited**
* `final` class **cannot be inherited**
* `final` methods **cannot be overridden**
* Java does **not support multiple inheritance with classes**
* Java supports multiple inheritance **using interfaces**
* `super` keyword is used to access parent class members

---

## TYPES OF INHERITANCE IN JAVA

### 1. SINGLE INHERITANCE

One child class inherits from one parent class.

```
Parent → Child
```

#### Example

```java
class Animal {
    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks");
    }
}
```

#### Use Case

Basic reuse and specialization.

---

### 2. MULTILEVEL INHERITANCE

A class inherits from another derived class.

```
Grandparent → Parent → Child
```

#### Example

```java
class Device {
    void powerOn() {
        System.out.println("Device powered on");
    }
}

class Mobile extends Device {
    void call() {
        System.out.println("Calling...");
    }
}

class SmartPhone extends Mobile {
    void browse() {
        System.out.println("Browsing internet");
    }
}
```

#### Use Case

Feature layering in frameworks and libraries.

---

### 3. HIERARCHICAL INHERITANCE

Multiple child classes inherit from a single parent class.

```
      Parent
     /      \
  Child1   Child2
```

#### Example

```java
class Employee {
    void work() {
        System.out.println("Employee works");
    }
}

class Developer extends Employee {
    void code() {
        System.out.println("Developer codes");
    }
}

class Tester extends Employee {
    void test() {
        System.out.println("Tester tests");
    }
}
```

#### Use Case

Role-based specialization.

---

### 4. MULTIPLE INHERITANCE (NOT SUPPORTED WITH CLASSES)

Java **does NOT allow** multiple inheritance with classes to avoid ambiguity (**Diamond Problem**).

####  Not Allowed

```java
class A {
    void show() {}
}

class B {
    void show() {}
}

// class C extends A, B { }  // Compilation error
```

---

### DIAMOND PROBLEM (THEORY)

```
     A
    / \
   B   C
    \ /
     D
```

If `D` calls `show()`, which version should be used?
Java avoids this ambiguity by **not allowing multiple inheritance with classes**.

---

### 5. MULTIPLE INHERITANCE USING INTERFACES (SUPPORTED)

Java allows a class to implement **multiple interfaces** because interfaces don’t have state conflicts.

#### Example

```java
interface Camera {
    void takePhoto();
}

interface MusicPlayer {
    void playMusic();
}

class Phone implements Camera, MusicPlayer {
    public void takePhoto() {
        System.out.println("Photo taken");
    }

    public void playMusic() {
        System.out.println("Music playing");
    }
}
```

---

### 6. HYBRID INHERITANCE

Combination of two or more types of inheritance.
In Java, hybrid inheritance is achieved **only using interfaces**.

#### Example

```java
interface A {
    void methodA();
}

interface B extends A {
    void methodB();
}

class C implements B {
    public void methodA() {
        System.out.println("Method A");
    }

    public void methodB() {
        System.out.println("Method B");
    }
}
```

---

## `super` KEYWORD IN INHERITANCE

### Uses of `super`

1. Access parent class variables
2. Call parent class methods
3. Call parent class constructor

#### Example

```java
class Person {
    Person() {
        System.out.println("Person constructor");
    }
}

class Employee extends Person {
    Employee() {
        super(); // implicit if not written
        System.out.println("Employee constructor");
    }
}
```

---

## METHOD OVERRIDING AND INHERITANCE

Method overriding is **runtime polymorphism**, where a child class provides its own implementation of a parent method.

#### Rules

* Same method name
* Same parameter list
* IS-A relationship
* Access level cannot be reduced
* `@Override` annotation recommended

#### Example

```java
class Bank {
    double getInterestRate() {
        return 5.0;
    }
}

class SBI extends Bank {
    @Override
    double getInterestRate() {
        return 6.5;
    }
}
```

---

## INHERITANCE VS COMPOSITION (INTERVIEW FAVORITE)

| Inheritance               | Composition                |
| ------------------------- | -------------------------- |
| IS-A relationship         | HAS-A relationship         |
| Tight coupling            | Loose coupling             |
| Less flexible             | More flexible              |
| Overuse leads to rigidity | Preferred in modern design |

**Rule of thumb**:
*Prefer composition over inheritance unless there is a clear IS-A relationship.*

---

## REAL-WORLD ANALOGY

* Vehicle → Car → ElectricCar
* Employee → Manager / Developer
* Account → SavingsAccount / CurrentAccount
* Shape → Circle / Rectangle

---

## COMMON INTERVIEW QUESTIONS

* Why doesn’t Java support multiple inheritance with classes?
* Difference between `extends` and `implements`
* Can constructors be inherited?
* Can we override static methods? (No – method hiding)
* What happens if parent method is `final`?
* Can a subclass reduce access modifier? (No)

---
