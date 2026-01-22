# Java Socket Programming

---

## 1. What is Socket Programming?

**Socket programming** enables communication between two processes over a network.

* One process acts as a **server**
* Another process acts as a **client**
* Communication happens using **IP address + Port number**

A **socket** is an endpoint for communication.

---

## 2. Where Socket Programming is Used

* Client–Server applications
* Chat systems
* File transfer utilities
* Distributed systems
* Microservice communication (low-level)

---

## 3. Types of Sockets in Java

| Type       | Description                   |
| ---------- | ----------------------------- |
| TCP Socket | Reliable, connection-oriented |
| UDP Socket | Fast, connectionless          |

This tutorial focuses on **TCP sockets**, which are most commonly used.

---

## 4. Core Classes in `java.net`

| Class          | Purpose                        |
| -------------- | ------------------------------ |
| `ServerSocket` | Listens for client connections |
| `Socket`       | Represents a client connection |
| `InetAddress`  | Represents IP address          |
| `InputStream`  | Read data                      |
| `OutputStream` | Write data                     |

---

## 5. TCP Communication Flow

```
Client → Connect → Server
Server → Accept → Client
Client ↔ Server (Read / Write)
Connection Closed
```

---

## 6. Simple Client–Server Architecture

```
Client Application
    |
    | TCP Connection
    |
Server Application (Port 5000)
```

---

## 7. Server Program (Basic)

### Responsibilities

* Open a port
* Wait for client connection
* Read request
* Send response

### Server Code

```java
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            System.out.println("Waiting for client...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );

            PrintWriter out =
                new PrintWriter(
                    clientSocket.getOutputStream(), true
                );

            String clientMessage = in.readLine();
            System.out.println("Received: " + clientMessage);

            out.println("Hello Client, message received");

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## 8. Client Program (Basic)

### Responsibilities

* Connect to server
* Send request
* Read response

### Client Code

```java
import java.io.*;
import java.net.Socket;

public class SimpleClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port)) {

            PrintWriter out =
                new PrintWriter(
                    socket.getOutputStream(), true
                );

            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

            out.println("Hello Server");

            String response = in.readLine();
            System.out.println("Server response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## 9. How to Run the Program

1. Compile both classes

   ```
   javac SimpleServer.java
   javac SimpleClient.java
   ```
2. Start the server first

   ```
   java SimpleServer
   ```
3. Start the client in another terminal

   ```
   java SimpleClient
   ```

---

## 10. Understanding the Blocking Nature

* `accept()` blocks until a client connects
* `readLine()` blocks until data is received
* One client at a time in this example

This is a **blocking I/O model**.

---

## 11. Handling Multiple Clients (Concept)

To handle multiple clients:

* Accept connections in a loop
* Handle each client in a separate thread

```java
while (true) {
    Socket client = serverSocket.accept();
    new Thread(new ClientHandler(client)).start();
}
```

Each client is processed independently.

---

## 12. Client Handler Example

```java
class ClientHandler implements Runnable {

    private final Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );
            PrintWriter out =
                new PrintWriter(
                    socket.getOutputStream(), true
                )
        ) {
            String message = in.readLine();
            out.println("Processed: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## 13. Important Points to Remember

* Always close sockets to avoid resource leaks
* Use buffered streams for efficiency
* Handle exceptions carefully
* TCP guarantees delivery but adds overhead
* Ports below 1024 require admin privileges

---

## 14. Common Mistakes

* Forgetting to flush output streams
* Blocking main thread unintentionally
* Not handling client disconnects
* Using fixed ports already in use

---

## 15. Socket Programming vs HTTP

| Socket           | HTTP          |
| ---------------- | ------------- |
| Low-level        | High-level    |
| Manual protocol  | Standardized  |
| Faster           | More overhead |
| Harder to manage | Easier        |

---

## 16. When to Use Socket Programming

Use sockets when:

* You need custom protocols
* Low latency is required
* Full control over communication

Avoid when:

* Simple REST APIs suffice
* Scalability and security are primary concerns

---

## 17. What to Learn Next

* Object streams (`ObjectInputStream`)
* NIO (`SocketChannel`, `Selector`)
* Secure sockets (SSL/TLS)
* Non-blocking servers
* Protocol design

---
