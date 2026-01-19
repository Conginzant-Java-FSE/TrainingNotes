# Java Text Blocks

## 1. What are Text Blocks?

A **text block** is a multi-line string literal enclosed using **three double quotes**:

```java
"""
text
"""
```

---

## 2. Why Text Blocks?

Before:

```java
String json = "{\n" +
              "  \"name\": \"Java\"\n" +
              "}";
```

With text blocks:

```java
String json = """
    {
      "name": "Java"
    }
    """;
```

✔ Better readability
✔ No `\n` or `\"`
✔ Cleaner formatting

---

## 3. Basic Syntax Rules

* Starts and ends with `"""`
* Newline after opening `"""`
* Closing delimiter controls indentation

```java
String text = """
    Hello
    World
    """;
```

---

## 4. Indentation Handling

Java automatically removes **common leading whitespace**.

```java
String s = """
        Line1
        Line2
        """;
```

Output:

```
Line1
Line2
```

---

## 5. Escaping in Text Blocks

Fewer escapes needed.

```java
String sql = """
    SELECT * FROM users
    WHERE name = "John"
    """;
```

Only escape when required:

* `\"""` → to include `"""`

---

## 6. Line Break Control

### New line at end (default)

```java
String s = """
    Hello
    """;
```

### Avoid trailing newline

```java
String s = """
    Hello\
    """;
```

---

## 7. Useful Escape Sequences

| Escape | Meaning   |
| ------ | --------- |
| `\n`   | New line  |
| `\t`   | Tab       |
| `\s`   | Space     |
| `\\`   | Backslash |

---

## 8. Common Use Cases

* SQL queries
* JSON / XML
* HTML templates
* Logs
* Configuration strings

---

## 9. Text Blocks vs Normal Strings

| Feature     | String Literal | Text Block |
| ----------- | -------------- | ---------- |
| Multi-line  | no             | yes        |
| Readability | Low            | High       |
| Escaping    | More           | Less       |

---

## 10. Interview One-Liners

* "Text blocks simplify multi-line strings."
* "Introduced in Java 15."
* "Indentation is handled automatically."
* "Ideal for JSON, SQL, and HTML."

---

## 11. Final Summary

> **Text blocks make Java code cleaner by representing multi-line text naturally.**
