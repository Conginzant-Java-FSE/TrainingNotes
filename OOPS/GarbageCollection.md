# JAVA GARBAGE COLLECTION

## WHAT IS GARBAGE COLLECTION?

Garbage Collection (GC) is the **automatic process** by which the **JVM frees heap memory** by removing objects that are **no longer reachable**.

Developers create objects, but **JVM manages memory cleanup**.

---

## WHY GARBAGE COLLECTION?

* Prevents memory leaks
* Avoids manual memory management
* Improves application stability
* Eliminates dangling pointers

---

## WHAT IS GARBAGE?

An object becomes garbage when it is **not reachable from any live reference**.

### Common cases:

```java
obj = null;
obj = new MyClass();   // old object becomes garbage
new MyClass();        // anonymous object
```

---

## HOW JVM IDENTIFIES GARBAGE?

Using **reachability analysis**:

* JVM starts from **GC Roots** (stack variables, static variables, active threads)
* Objects not reachable from GC Roots are eligible for GC

---

## JVM HEAP STRUCTURE (OVERVIEW)

* **Young Generation** – newly created objects
* **Old Generation** – long-lived objects
* **Metaspace** – class metadata

---

## TYPES OF GARBAGE COLLECTION

* **Minor GC** – cleans Young Generation
* **Major GC** – cleans Old Generation
* **Full GC** – cleans entire heap

---

## CAN WE FORCE GC?

```java
System.gc();
```

* This is only a **request**
* JVM may ignore it

---

## `finalize()` METHOD

* Called before object is removed
* **Not guaranteed to execute**
* Deprecated and not recommended

---

## IMPORTANT POINTS

* GC works only on **heap memory**
* Java uses **automatic memory management**
* GC improves safety but does not eliminate memory leaks completely

---

## SUMMARY

* Garbage Collection removes unreachable objects
* JVM handles memory automatically
* Objects are collected when no longer referenced
* GC improves reliability and developer productivity

---

