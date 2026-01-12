# NON-ACCESS MODIFIERS IN JAVA

## WHAT ARE NON-ACCESS MODIFIERS?

**Non-access modifiers** are Java keywords that:

* **Do NOT control visibility**
* Control **behavior, properties, or restrictions** of classes, methods, and variables

They define **how something behaves**, not **who can access it**.

---

## LIST OF NON-ACCESS MODIFIERS IN JAVA

| Modifier       | Applicable To                              |
| -------------- | ------------------------------------------ |
| `static`       | variables, methods, blocks, nested classes |
| `final`        | variables, methods, classes                |
| `abstract`     | classes, methods                           |
| `synchronized` | methods, blocks                            |
| `volatile`     | variables                                  |
| `transient`    | variables                                  |
| `strictfp`     | classes, methods                           |

---

# 1. `static`

## THEORY

`static` means:

> Belongs to the **class**, not to individual objects

Only **one copy** exists, shared across all instances.

---

## STATIC VARIABLE

```java
class Counter {
    static int count = 0;

    Counter() {
        count++;
    }
}
```

```java
new Counter();
new Counter();
System.out.println(Counter.count); // 2
```

---

## STATIC METHOD

```java
class Utils {
    static void printMessage() {
        System.out.println("Hello");
    }
}
```

```java
Utils.printMessage();
```

### Key Points

* Cannot access non-static members directly
* Cannot be overridden (method hiding happens)

---

## STATIC BLOCK

```java
class Demo {
    static {
        System.out.println("Static block executed");
    }
}
```

Runs **once** when class is loaded.

---

# 2. `final`

## THEORY

`final` means:

> Cannot be changed or extended

---

## FINAL VARIABLE

```java
final int MAX = 100;
```

* Value cannot be reassigned
* For objects, reference is final, not state

---

## FINAL METHOD

```java
class Parent {
    final void show() {
        System.out.println("Cannot override");
    }
}
```

* Cannot be overridden
* Can be inherited

---

## FINAL CLASS

```java
final class Utility {
}
```

* Cannot be extended
* Example: `String`, `Integer`

---

# 3. `abstract`

## THEORY

`abstract` means:

> Incomplete, needs implementation

---

## ABSTRACT CLASS

```java
abstract class Vehicle {
    abstract void start();
}
```

* Cannot be instantiated
* May contain concrete methods

---

## ABSTRACT METHOD

```java
abstract void calculate();
```

* No method body
* Must be implemented by child class

---

## RULES

* Abstract class cannot be `final`
* Abstract methods cannot be `private`, `static`, or `final`

---

# 4. `synchronized`

## THEORY

`synchronized` ensures:

> **Thread-safe access** to shared resources

Only **one thread** can execute synchronized code at a time.

---

## SYNCHRONIZED METHOD

```java
class Counter {
    synchronized void increment() {
        count++;
    }
}
```

Lock is on **current object (`this`)**.

---

## SYNCHRONIZED BLOCK

```java
synchronized(this) {
    count++;
}
```

Allows fine-grained locking.

---

# 5. `volatile`

## THEORY

`volatile` ensures:

> Changes to a variable are **immediately visible** to all threads

Prevents thread-local caching.

---

## EXAMPLE

```java
volatile boolean flag = true;
```

### Key Points

* Guarantees visibility, not atomicity
* Often used with flags

---

# 6. `transient`

## THEORY

`transient` means:

> Variable should NOT be serialized

Used in serialization.

---

## EXAMPLE

```java
class User implements Serializable {
    String username;
    transient String password;
}
```

* `password` will not be saved during serialization

---


## EXAMPLE

```java
class NativeDemo {
    native void process();
}
```

* No method body
* Implementation provided externally

---

# 7. `strictfp`

## THEORY

`strictfp` ensures:

> Floating-point calculations are consistent across platforms

---

## EXAMPLE

```java
strictfp class Calculator {
    strictfp double add(double a, double b) {
        return a + b;
    }
}
```

---

# COMMON INTERVIEW TRAPS

### Can abstract be used with static?

No (abstract needs instance behavior)

### Can final method be overridden?

No

### Can static methods be overridden?

No (method hiding occurs)

### Is volatile thread-safe?

No (only visibility, not atomicity)

---

# NON-ACCESS vs ACCESS MODIFIERS

| Access Modifiers | Non-Access Modifiers                                |
| ---------------- | --------------------------------------------------- |
| public           | static                                              |
| protected        | final                                               |
| default          | abstract                                            |
| private          | synchronized, volatile, transient, native, strictfp |

---

# SUMMARY

* Non-access modifiers define **behavior and restrictions**
* They do NOT control visibility
* Critical for:

  * Multithreading (`synchronized`, `volatile`)
  * Design (`final`, `abstract`)
  * Performance and safety (`static`, `transient`)
* Frequently asked in interviews and certification exams

---
