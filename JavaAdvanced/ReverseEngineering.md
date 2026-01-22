# Reverse Engineering Java Applications

---

## 1. What is Reverse Engineering?

**Reverse Engineering (RE)** is the process of analyzing a compiled application to understand:

* How it works internally
* What logic it implements
* How components interact
* What algorithms, validations, or protocols are used

In Java, reverse engineering usually means:

> **Converting `.class` or `.jar` files back into readable Java source code**

---

## 2. Why Reverse Engineer Java Applications?

### Legitimate Use Cases

* Understanding **legacy systems** with no source code
* Debugging third-party libraries
* Security audits and vulnerability analysis
* Malware analysis
* Learning from compiled frameworks
* Verifying license enforcement logic
* Recovering lost source code (partial)

### Illegal / Unethical Use (Avoid)

* Piracy
* License bypassing
* Intellectual property theft

Reverse engineering must always comply with **legal and contractual constraints**.

---

## 3. Why Java Is Easier to Reverse Engineer

Java compiles into **bytecode**, not native machine code.

```
Java Source → Bytecode (.class) → JVM
```

Bytecode:

* Is **high-level**
* Preserves method names, class structure, control flow
* Retains metadata (unless obfuscated)

As a result, Java decompilation can recover:

* Classes
* Methods
* Control flow
* Business logic (mostly)

---

## 4. What You Can and Cannot Recover

### Can Be Recovered Well

* Class and method structure
* Loops, conditions, logic
* Constants and literals
* Method calls
* Exception handling

### Cannot Be Perfectly Recovered

* Original variable names (often replaced)
* Comments
* Formatting
* Lambdas (may be synthetic)
* Generic type information (sometimes erased)
* Exact source code (only approximation)

---

## 5. Java Reverse Engineering Workflow

```
JAR / CLASS file
        ↓
Decompile
        ↓
Analyze structure
        ↓
Understand logic
        ↓
Reconstruct behavior
```

---

## 6. Tools Used for Java Reverse Engineering

| Tool            | Purpose                      |
| --------------- | ---------------------------- |
| JD-GUI          | Visual Java decompiler       |
| CFR             | Command-line Java decompiler |
| javap           | Bytecode inspection          |
| Bytecode Viewer | Advanced RE                  |
| Procyon         | Alternative decompiler       |

This tutorial focuses on **JD-GUI** and **CFR**.

---

# PART 1: Reverse Engineering Using JD-GUI

---

## 7. What is JD-GUI?

**JD-GUI** is a graphical Java decompiler that:

* Opens `.class` and `.jar` files
* Displays decompiled Java source
* Allows browsing package structure
* Exports decompiled source

Best for **quick inspection and learning**.

---

## 8. Installing JD-GUI

1. Download JD-GUI (official site)
2. Extract ZIP
3. Run:

   * `jd-gui.exe` (Windows)
   * `jd-gui` (Linux/Mac)

No installation required.

---

## 9. Sample Java Application (Original)

```java
public class LicenseChecker {

    public boolean validate(String key) {
        return key.startsWith("LIC-") && key.length() == 12;
    }
}
```

Compiled into:

```
LicenseChecker.class
```

---

## 10. Decompiling with JD-GUI

### Steps

1. Open JD-GUI
2. File → Open → Select `LicenseChecker.class`
3. Browse package tree
4. Click class to view source

---

## 11. Decompiled Output (JD-GUI)

```java
public class LicenseChecker {
    public boolean validate(String paramString) {
        return paramString.startsWith("LIC-") && paramString.length() == 12;
    }
}
```

### Observations

* Logic is fully visible
* Parameter name changed
* Structure preserved

---

## 12. Reverse Engineering a JAR File

1. Open `.jar` in JD-GUI
2. View:

   * Packages
   * Resources
   * MANIFEST.MF
3. Navigate classes

JD-GUI automatically:

* Resolves dependencies inside JAR
* Shows inner classes
* Displays lambdas (synthetic)

---

## 13. Exporting Decompiled Code

* File → Save All Sources
* JD-GUI generates:

  ```
  src.zip
  ```
* Extract and analyze locally

---

## 14. Limitations of JD-GUI

* GUI only
* Not scriptable
* Sometimes produces less readable code
* Weak with modern Java bytecode compared to CFR

---

# PART 2: Reverse Engineering Using CFR

---

## 15. What is CFR?

**CFR** is a **command-line Java decompiler** designed to:

* Handle modern Java versions (8–21+)
* Recover lambdas and switch expressions
* Produce cleaner, closer-to-source output
* Be scriptable and automatable

Preferred for **serious reverse engineering**.

---

## 16. Downloading CFR

Download:

```
cfr.jar
```

Requirements:

* Java 8 or above

---

## 17. Decompiling a Class with CFR

### Command

```bash
java -jar cfr.jar LicenseChecker.class
```

### Output

```java
public class LicenseChecker {
    public boolean validate(String key) {
        return key.startsWith("LIC-") && key.length() == 12;
    }
}
```

CFR recovered the **original variable name** (`key`) correctly.

---

## 18. Decompiling a JAR File

```bash
java -jar cfr.jar myapp.jar --outputdir output
```

This produces:

```
output/
 └── com/example/...
```

---

## 19. Important CFR Options

| Option                 | Purpose                   |
| ---------------------- | ------------------------- |
| `--outputdir`          | Save decompiled files     |
| `--comments false`     | Remove synthetic comments |
| `--decodelambdas true` | Decode lambdas            |
| `--hideutf false`      | Show readable strings     |
| `--extraclasspath`     | Add dependencies          |

Example:

```bash
java -jar cfr.jar app.jar --outputdir src --decodelambdas true
```

---

## 20. CFR vs JD-GUI

| Aspect           | JD-GUI  | CFR       |
| ---------------- | ------- | --------- |
| Interface        | GUI     | CLI       |
| Automation       | No      | Yes       |
| Java 17+ support | Limited | Excellent |
| Lambda handling  | Average | Very good |
| Readability      | Medium  | High      |

---

## 21. Typical Reverse Engineering Analysis Steps

1. Identify entry point (`main`)
2. Inspect configuration files
3. Trace service calls
4. Locate validations / checks
5. Reconstruct workflows
6. Rename variables manually
7. Document behavior

---

## 22. Common Challenges in Java Reverse Engineering

* Obfuscated code
* Synthetic methods
* Decompiled but unreadable logic
* Missing dependencies
* Inlined constants

---

## 23. Obfuscation (High-Level Mention)

Tools like:

* ProGuard
* R8
* DexGuard

They:

* Rename classes and methods
* Flatten control flow
* Encrypt strings

Decompilation still works, but **understanding logic becomes harder**.

---

## 24. Best Practices for Reverse Engineering

* Never trust variable names blindly
* Reconstruct intent, not syntax
* Compare multiple decompilers
* Use `javap` for bytecode verification
* Document findings clearly

---

## 25. Summary

* Java reverse engineering is feasible due to bytecode
* JD-GUI is ideal for quick visual inspection
* CFR is preferred for deep, modern analysis
* Decompiled code is an approximation, not original source
* Ethical and legal usage is mandatory

