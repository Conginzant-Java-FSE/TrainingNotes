# JAVA `Object` CLASS 

## WHAT IS `Object` CLASS

`java.lang.Object` is the **root class of the Java class hierarchy**.

**Every class in Java implicitly extends `Object`**, either directly or indirectly.
If a class does not explicitly extend another class, it **automatically extends `Object`**.

```java
class Employee {
}
```

The above class is equivalent to:

```java
class Employee extends Object {
}
```

This guarantees that **all Java objects share a common set of methods**, enabling **polymorphism, runtime behavior, and JVM-level services**.

---

## WHY `Object` CLASS IS IMPORTANT

The `Object` class provides:

* A **common parent type** for all objects
* Core methods for:

  * Object identity
  * Equality comparison
  * Hash-based collections
  * Thread coordination
  * Runtime type information
  * Safe object cloning
  * Garbage collection hooks

Without `Object`, Java would not support **collections, synchronization, or polymorphism**.

---

## PACKAGE AND VISIBILITY

* Package: `java.lang`
* Imported **automatically**
* Methods are **public / protected / native**

---

## METHODS OF `Object` CLASS

### COMPLETE METHOD LIST

| Method               | Type                   |
| -------------------- | ---------------------- |
| `toString()`         | Public                 |
| `equals(Object obj)` | Public                 |
| `hashCode()`         | Public                 |
| `getClass()`         | Public final           |
| `clone()`            | Protected              |
| `finalize()`         | Protected (deprecated) |
| `wait()`             | Public final           |
| `wait(long)`         | Public final           |
| `wait(long, int)`    | Public final           |
| `notify()`           | Public final           |
| `notifyAll()`        | Public final           |

---

## 1. `toString()` METHOD

### PURPOSE

Returns a **string representation of an object**.

Default implementation:

```
ClassName@hexHashCode
```

### Default Behavior Example

```java
class Student {
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
        System.out.println(s);
    }
}
```

Output:

```
Student@6d311334
```

---

### OVERRIDING `toString()`

Used for **logging, debugging, and readability**.

```java
class Student {
    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "'}";
    }
}
```

---

## 2. `equals(Object obj)` METHOD

### PURPOSE

Compares **logical equality**, not memory address.

Default behavior in `Object`:

```java
return this == obj;
```

This checks **reference equality**.

---

### WHY OVERRIDE `equals()`

Used in:

* Collections (`Set`, `Map`)
* Searching objects
* Business logic comparison

---

### CORRECT `equals()` IMPLEMENTATION

```java
class Employee {
    int id;
    String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Employee e = (Employee) obj;
        return id == e.id && name.equals(e.name);
    }
}
```

---

### `equals()` CONTRACT

Must be:

* Reflexive
* Symmetric
* Transitive
* Consistent
* `x.equals(null)` → false

---

## 3. `hashCode()` METHOD

### PURPOSE

Returns an integer used by **hash-based collections**.

If `equals()` is overridden, `hashCode()` **must also be overridden**.

---

### CONTRACT

* Equal objects **must have same hashCode**
* Same hashCode **does not mean equal objects**

---

### CORRECT IMPLEMENTATION

```java
@Override
public int hashCode() {
    return Objects.hash(id, name);
}
```

---

### COLLECTION ISSUE EXAMPLE

```java
Set<Employee> set = new HashSet<>();
set.add(new Employee(1, "A"));
set.add(new Employee(1, "A"));
```

Without overriding `hashCode()`, duplicates may exist.

---

## 4. `getClass()` METHOD

### PURPOSE

Returns the **runtime class metadata**.

* Final method
* Used in reflection and frameworks

```java
Employee e = new Employee(1, "John");
System.out.println(e.getClass().getName());
```

Output:

```
Employee
```

---

### `getClass()` vs `instanceof`

| `getClass()`      | `instanceof`       |
| ----------------- | ------------------ |
| Exact type        | Allows inheritance |
| Strict comparison | Polymorphic        |

---

## 5. `clone()` METHOD

### PURPOSE

Creates a **shallow copy** of an object.

* Protected method
* Requires `Cloneable` interface
* Otherwise throws `CloneNotSupportedException`

---

### CLONING EXAMPLE

```java
class Book implements Cloneable {
    int id;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```

---

### SHALLOW VS DEEP COPY

* **Shallow**: Copies references
* **Deep**: Copies actual objects

Java’s default `clone()` → **shallow copy**

---

## 6. `finalize()` METHOD (DEPRECATED)

### PURPOSE

Called by GC before object destruction.

 **Unreliable and deprecated since Java 9**

Use:

* `try-with-resources`
* `AutoCloseable`

---

## 7. THREAD COMMUNICATION METHODS

### `wait()`, `notify()`, `notifyAll()`

Used for **inter-thread communication**.

Important:

* Must be called **inside synchronized block**
* Belong to `Object`, not `Thread`

---

### Example

```java
synchronized (obj) {
    obj.wait();
}

synchronized (obj) {
    obj.notify();
}
```

---

## WHY WAIT/NOTIFY ARE IN `Object`

Because **every object has a monitor lock**, not just threads.

---

## REAL-WORLD USE CASES

* `toString()` → logging
* `equals()` & `hashCode()` → collections
* `getClass()` → frameworks (Spring, Hibernate)
* `wait()/notify()` → concurrency
* `clone()` → caching, prototypes

---

## COMMON INTERVIEW QUESTIONS

* Why is `Object` class needed?
* Can we override `getClass()`? (No)
* Why override `hashCode()` with `equals()`?
* Difference between `==` and `equals()`
* Why `wait()` is not in `Thread`?
* Why `clone()` is protected?

---

## KEY TAKEAWAYS

* `Object` is the **foundation of Java OOPS**
* Every class inherits its methods
* Overriding `equals()`, `hashCode()`, `toString()` is **critical**
* Thread methods are **monitor-based**
* Poor `Object` method usage causes **bugs in production**

---
