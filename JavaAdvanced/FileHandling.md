# Java File Handling 

## 1. What is File Handling in Java?

**File handling** allows Java programs to:

* Create files and directories
* Read data from files
* Write data to files
* Update and delete files

Java supports file handling through:

* `java.io` (legacy, stream-based)
* `java.nio.file` (modern, preferred)

---

## 2. File Handling Approaches

| API     | Package         | Notes                  |
| ------- | --------------- | ---------------------- |
| File    | `java.io`       | File metadata          |
| Streams | `java.io`       | Byte/Character streams |
| NIO     | `java.nio.file` | Fast, scalable, modern |

---

## 3. `File` Class (`java.io.File`)

Used to represent **file or directory metadata**, not file content.

### Create File Object

```java
File file = new File("data.txt");
```

### Common Methods

```java
file.exists();
file.createNewFile();
file.delete();
file.isFile();
file.isDirectory();
file.length();
file.getName();
file.getAbsolutePath();
```

---

## 4. Writing to a File (Character Stream)

### Using `FileWriter`

```java
FileWriter writer = new FileWriter("data.txt");
writer.write("Hello Java");
writer.close();
```

Append mode:

```java
new FileWriter("data.txt", true);
```

---

## 5. Reading from a File (Character Stream)

### Using `FileReader`

```java
FileReader reader = new FileReader("data.txt");
int ch;
while ((ch = reader.read()) != -1) {
    System.out.print((char) ch);
}
reader.close();
```

---

## 6. Buffered Streams (Recommended)

Buffered streams improve performance by reducing I/O operations.

### Writing with `BufferedWriter`

```java
BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"));
bw.write("Buffered write");
bw.newLine();
bw.close();
```

### Reading with `BufferedReader`

```java
BufferedReader br = new BufferedReader(new FileReader("data.txt"));
String line;
while ((line = br.readLine()) != null) {
    System.out.println(line);
}
br.close();
```

---

## 7. Byte Streams (Binary Data)

Used for images, PDFs, audio, etc.

### Writing using `FileOutputStream`

```java
FileOutputStream fos = new FileOutputStream("data.bin");
fos.write(65);
fos.close();
```

### Reading using `FileInputStream`

```java
FileInputStream fis = new FileInputStream("data.bin");
int b;
while ((b = fis.read()) != -1) {
    System.out.println(b);
}
fis.close();
```

---

## 8. Try-with-Resources (Best Practice)

Automatically closes resources.

```java
try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
    br.lines().forEach(System.out::println);
}
```

---

## 9. Java NIO (Modern File Handling)

### Important Classes

* `Path`
* `Paths`
* `Files`

### Creating a File

```java
Path path = Paths.get("nio.txt");
Files.createFile(path);
```

---

## 10. Writing Files using NIO

```java
Path path = Paths.get("nio.txt");
Files.write(path, List.of("Java", "File", "Handling"));
```

Append mode:

```java
Files.write(path, List.of("Append"), StandardOpenOption.APPEND);
```

---

## 11. Reading Files using NIO

### Read All Lines

```java
List<String> lines = Files.readAllLines(path);
```

### Stream Lines

```java
Files.lines(path).forEach(System.out::println);
```

---

## 12. Copy, Move, Delete Files (NIO)

```java
Files.copy(src, dest);
Files.move(src, dest);
Files.delete(path);
```

---

## 13. Directory Operations

```java
Files.createDirectory(Paths.get("docs"));
Files.list(Paths.get("docs")).forEach(System.out::println);
```

---

## 14. File Permissions (NIO)

```java
Files.isReadable(path);
Files.isWritable(path);
Files.isExecutable(path);
```

---

## 15. File Handling + Streams (Common Use Case)

```java
long count = Files.lines(Paths.get("data.txt"))
        .filter(line -> line.contains("Java"))
        .count();
```

---

## 16. Common Exceptions

| Exception             | Reason           |
| --------------------- | ---------------- |
| IOException           | I/O failure      |
| FileNotFoundException | File missing     |
| SecurityException     | Permission issue |

---

## 17. Interview Comparison: IO vs NIO

| Feature     | IO     | NIO    |
| ----------- | ------ | ------ |
| Blocking    | Yes    | Less   |
| Performance | Slower | Faster |
| Scalability | Low    | High   |
| Preferred   | no     | yes    |

---

## 18. Common Mistakes

* Forgetting to close streams
* Using `File` to read data
* Ignoring exceptions
* Using byte streams for text

---

## 19. When to Use What

| Scenario         | Use                   |
| ---------------- | --------------------- |
| Simple text file | BufferedReader/Writer |
| Binary file      | FileInputStream       |
| Large files      | NIO                   |
| Production code  | NIO + Streams         |

---

## 20. Interview One-Liners

* "`File` class represents metadata, not content."
* "Buffered streams improve performance."
* "NIO is preferred over IO."
* "Always use try-with-resources."

---

## 21. Final Summary

> Java file handling supports multiple APIs; **NIO is modern and preferred**, while **IO is still useful for basics**.

---
