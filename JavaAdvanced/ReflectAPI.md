# Java Reflection API 

## 1. What is Reflection?

> Reflection lets Java code **examine itself at runtime**.

Using reflection, you can:

* Inspect classes, methods, fields
* Create objects dynamically
* Invoke methods at runtime
* Access private members

---

## 2. Why Reflection is Needed

Reflection is commonly used in:

* Frameworks (Spring, Hibernate)
* Dependency Injection
* ORM tools
* Serialization libraries
* Unit testing tools

---

## 3. Core Reflection Classes

| Class         | Package             | Purpose                  |
| ------------- | ------------------- | ------------------------ |
| `Class`       | `java.lang`         | Represents a class       |
| `Method`      | `java.lang.reflect` | Represents a method      |
| `Field`       | `java.lang.reflect` | Represents a field       |
| `Constructor` | `java.lang.reflect` | Represents a constructor |

---

## 4. Getting `Class` Object

### 1. Using `.class`

```java
Class<?> cls = String.class;
```

### 2. Using `getClass()`

```java
Class<?> cls = obj.getClass();
```

### 3. Using `Class.forName()`

```java
Class<?> cls = Class.forName("java.lang.String");
```

---

## 5. Getting Class Information

```java
cls.getName();
cls.getSuperclass();
cls.getInterfaces();
cls.getModifiers();
```

---

## 6. Accessing Constructors

```java
Constructor<?>[] constructors = cls.getDeclaredConstructors();
```

Create object dynamically:

```java
Constructor<?> cons = cls.getConstructor();
Object obj = cons.newInstance();
```

---

## 7. Accessing Methods

```java
Method[] methods = cls.getDeclaredMethods();
```

Invoke method:

```java
Method m = cls.getDeclaredMethod("methodName");
m.setAccessible(true);
m.invoke(obj);
```

---

## 8. Accessing Fields

```java
Field[] fields = cls.getDeclaredFields();
```

Access private field:

```java
Field f = cls.getDeclaredField("fieldName");
f.setAccessible(true);
f.set(obj, "value");
```

---

## 9. Example: Reflection in Action

```java
class User {
    private String name = "Java";
}
```

```java
User u = new User();
Field f = User.class.getDeclaredField("name");
f.setAccessible(true);
System.out.println(f.get(u));
```

Output:

```
Java
```

---

## 10. `setAccessible(true)`

* Bypasses access checks
* Allows access to private members
* Requires proper permissions

⚠ Can break encapsulation

---

## 11. Reflection + Annotations

```java
if (cls.isAnnotationPresent(MyAnnotation.class)) {
    MyAnnotation ann = cls.getAnnotation(MyAnnotation.class);
}
```

Used heavily in frameworks.

---

## 12. Performance Impact

Reflection is:

* Slower than direct calls
* Harder to debug
* Less type-safe

Use only when necessary.

---

## 13. When NOT to Use Reflection

* Business logic
* Performance-critical code
* Simple object creation
* Regular method calls

---

## 14. Common Exceptions

| Exception                 | Reason          |
| ------------------------- | --------------- |
| ClassNotFoundException    | Class not found |
| NoSuchMethodException     | Method missing  |
| IllegalAccessException    | Access denied   |
| InvocationTargetException | Method error    |

---

## 15. Interview One-Liners

* "Reflection enables runtime inspection of classes."
* "`Class` object is the entry point to reflection."
* "Frameworks rely heavily on reflection."
* "Reflection breaks encapsulation."

---

## 16. Reflection vs Normal Code

| Feature             | Reflection | Normal |
| ------------------- | ---------- | ------ |
| Compile-time safety | ❌          | ✔      |
| Performance         | Slower     | Faster |
| Flexibility         | High       | Low    |

---

## 17. Final Summary

> **Reflection provides flexibility at runtime but must be used cautiously due to performance and security concerns.**

---
