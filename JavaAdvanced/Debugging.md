# Debugging Java Applications 

---

## PART 1: Debugging Java Applications (Concepts & Core Techniques)

---

## 1. What Is Debugging?

**Debugging** is the process of:

* Identifying defects (bugs) in a program
* Understanding the root cause
* Fixing incorrect behavior

In Java applications, bugs can occur due to:

* Logical errors
* Incorrect conditions
* Null values
* Data inconsistencies
* Concurrency issues
* Configuration issues

---

## 2. Types of Errors in Java

### 2.1 Compile-Time Errors

Detected by the compiler.

Examples:

* Syntax errors
* Missing semicolons
* Type mismatch

These are resolved before debugging.

---

### 2.2 Runtime Errors

Occur while the program is running.

Examples:

* NullPointerException
* ArrayIndexOutOfBoundsException
* ArithmeticException

Debugging is mainly used for these.

---

### 2.3 Logical Errors

Program runs but produces incorrect output.

Examples:

* Wrong condition in `if`
* Incorrect loop boundaries
* Incorrect calculations

These are the hardest to detect and require debugging.

---

## 3. Traditional Debugging Techniques in Java

### 3.1 Using Print Statements

```java
System.out.println("Value: " + value);
```

Limitations:

* Pollutes code
* Not scalable
* Hard to remove later
* Cannot inspect runtime state easily

This is not recommended for professional development.

---

### 3.2 Using Logs

```java
log.info("Processing user with id {}", userId);
```

Advantages:

* Production-safe
* Configurable log levels

Limitations:

* Cannot pause execution
* Cannot inspect all variables dynamically

---

## 4. What Is a Debugger?

A **debugger** allows you to:

* Pause program execution
* Inspect variable values
* Execute code line by line
* Change variable values at runtime
* Analyze call stack and threads

Java debuggers work using the **Java Debug Wire Protocol (JDWP)**.

---

## 5. Core Debugging Concepts

---

### 5.1 Breakpoints

A **breakpoint** pauses program execution at a specific line.

Used to:

* Inspect variables
* Understand flow
* Identify incorrect logic

---

### 5.2 Step Operations

| Operation | Description                                   |
| --------- | --------------------------------------------- |
| Step Over | Executes current line, skips method internals |
| Step Into | Enters the called method                      |
| Step Out  | Exits current method                          |
| Resume    | Continues execution until next breakpoint     |

---

### 5.3 Call Stack

The **call stack** shows:

* The chain of method calls
* Current execution point

Used to:

* Trace execution flow
* Identify incorrect method calls

---

### 5.4 Variable Inspection

Allows inspection of:

* Local variables
* Method parameters
* Object fields

---

### 5.5 Watches

Used to monitor:

* Specific variables
* Expressions

They update automatically during debugging.

---

### 5.6 Thread Debugging

In multi-threaded applications:

* Each thread has its own stack
* Debugger allows switching between threads
* Helps detect deadlocks and race conditions

---

## PART 2: Debugging Java Applications Using IntelliJ IDEA

---

## 6. Starting Debug Mode in IntelliJ IDEA

Ways to start debugging:

* Click the **Debug** icon (bug symbol)
* Right-click → Debug
* Keyboard shortcut:

  * Windows/Linux: `Shift + F9`
  * macOS: `Control + D`

---

## 7. Setting Breakpoints in IntelliJ

Click in the left gutter next to the line number.

Breakpoint appears as a red dot.

---

## 8. Debug Window Overview

Key sections:

* Variables
* Watches
* Call Stack
* Threads
* Console

---

## 9. Stepping Through Code in IntelliJ

### Step Over

Shortcut: `F8`

Executes current line without entering methods.

---

### Step Into

Shortcut: `F7`

Enters method call for detailed inspection.

---

### Step Out

Shortcut: `Shift + F8`

Returns to the caller method.

---

### Resume Program

Shortcut: `F9`

---

## 10. Evaluating Expressions

Use **Evaluate Expression**:

* Shortcut: `Alt + F8` (Windows/Linux)
* Allows execution of expressions at runtime

Example:

```java
user.getName().toUpperCase()
```

---

## 11. Watches in IntelliJ

Add a watch:

* Right-click variable → Add to Watches
* Or manually add expression

Used to track variable changes across steps.

---

## 12. Conditional Breakpoints

Pause execution only when a condition is true.

Example:

```java
i == 100
```

Steps:

* Right-click breakpoint
* Add condition

Used to avoid stopping on every iteration.

---

## 13. Exception Breakpoints

Break when a specific exception is thrown.

Steps:

* View Breakpoints (`Ctrl + Shift + F8`)
* Add Exception Breakpoint
* Choose exception type

Very useful for debugging runtime errors.

---

## 14. Method Breakpoints

Pauses when:

* Method is entered
* Method is exited

Useful for framework-level debugging but slower.

---

## 15. Smart Step Into

Allows choosing which method to step into when multiple calls exist on one line.

Shortcut:

* `Shift + F7`

---

## 16. Drop Frame

Re-executes a method without restarting the application.

Steps:

* Right-click stack frame
* Select Drop Frame

Useful for fixing logic during debugging.

---

## 17. Changing Variable Values at Runtime

You can modify variable values while debugging.

Steps:

* Right-click variable
* Set Value

Useful for testing edge cases.

---

## 18. Debugging Loops

Techniques:

* Conditional breakpoints
* Watches
* Evaluate expressions

Avoid stepping every iteration manually.

---

## 19. Debugging Multi-Threaded Applications

IntelliJ features:

* Thread view
* Freeze thread
* Switch execution context

Helps diagnose:

* Deadlocks
* Race conditions
* Thread starvation

---

## 20. Debugging Spring Boot Applications (Brief)

* Debug same as Java app
* Place breakpoints in:

  * Controllers
  * Services
  * Repositories
* Use Exception Breakpoints for runtime issues

---

## 21. Common Debugging Mistakes

* Overusing breakpoints
* Debugging without understanding expected behavior
* Ignoring call stack
* Debugging production code instead of replicating locally

---

## 22. Debugging Best Practices

* Reproduce the issue before debugging
* Understand business logic
* Use conditional breakpoints
* Debug minimal code paths
* Remove unused breakpoints

---

## 23. Interview-Oriented Debugging Questions

1. Difference between Step Into and Step Over
2. What is a breakpoint?
3. How do you debug a NullPointerException?
4. How do you debug multi-threaded issues?
5. What is a call stack?

---

## 24. Summary

Debugging is a critical skill for Java developers.
Understanding debugger concepts combined with IntelliJ IDEA’s advanced debugging features allows efficient diagnosis and resolution of complex issues in real-world applications.

---
