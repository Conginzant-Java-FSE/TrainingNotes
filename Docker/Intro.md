# Docker Introduction 

## 1. What is Docker?

Docker is an open-source containerization platform that enables developers to package applications along with their dependencies into lightweight, portable containers.

A container includes:

* Application code
* Runtime (e.g., Java, Node.js)
* Libraries and dependencies
* Configuration files

This ensures the application runs consistently across:

* Developer machines
* Testing environments
* Production servers
* Cloud platforms

---

## 2. Why Docker?

### Traditional Deployment Problems

Before containers, applications were deployed in:

1. **Physical Servers**
2. **Virtual Machines (VMs)**
3. Shared OS environments

Problems:

* "Works on my machine" issue
* Dependency conflicts
* Heavy infrastructure
* Slow provisioning

---

## 3. Virtual Machines vs Containers

### Virtual Machine Architecture

![Image](https://www.researchgate.net/publication/242077512/figure/fig2/AS%3A282710602993666%401444414868359/Hosted-Virtual-Machine-Architecture.png)

![Image](https://www.researchgate.net/publication/335866538/figure/fig2/AS%3A882394324287494%401587390609903/Type-1-and-type-2-hypervisors.png)

![Image](https://www.netapp.com/media/container-vs-vm-inline1_tcm19-82163.png?v=85344)

![Image](https://akfpartners.com//uploads/blog/VM_Image.PNG)

**VM Architecture:**

```
Hardware
   ↓
Host OS
   ↓
Hypervisor
   ↓
Guest OS (multiple)
   ↓
Applications
```

Each VM:

* Has its own full OS
* Takes GBs of space
* Slower to boot

Popular hypervisors:

* VMware
* VirtualBox
* Microsoft Hyper-V

---

### Docker Container Architecture

![Image](https://assets.bytebytego.com/diagrams/0414-how-does-docker-work.png)

![Image](https://docs.docker.com/get-started/images/docker-architecture.webp)

![Image](https://i.sstatic.net/soe8E.jpg)

![Image](https://i.sstatic.net/R99OW.jpg)

**Container Architecture:**

```
Hardware
   ↓
Host OS
   ↓
Docker Engine
   ↓
Containers (share OS kernel)
   ↓
Applications
```

Key Difference:

* Containers share the host OS kernel
* No full guest OS
* Lightweight and fast

---

## 4. What is a Container?

A container is:

* A lightweight, isolated environment
* Runs a single process (ideally)
* Uses OS-level virtualization

Characteristics:

* Fast startup (seconds)
* Small size (MBs)
* Portable
* Isolated

---

## 5. Core Docker Components (Architecture Deep Dive)

### 1. Docker Client

* Command-line tool: `docker`
* Sends commands to Docker Daemon
* Example:

  ```bash
  docker build
  docker run
  docker pull
  ```

---

### 2. Docker Daemon (dockerd)

* Background service
* Manages containers, images, networks, volumes
* Listens to Docker API requests

---

### 3. Docker Images

An image is:

* A read-only template
* Used to create containers
* Built from a Dockerfile

Example image:

* Ubuntu
* Nginx
* MySQL

Image Layers:

* Docker images are layered
* Each instruction in Dockerfile creates a new layer
* Layers are cached

---

### 4. Docker Containers

* Runtime instance of an image
* Read-write layer added on top of image
* Can be started, stopped, deleted

---

### 5. Docker Registry

Stores Docker images.

Public registry:

* Docker Hub

Private registries:

* Amazon Elastic Container Registry
* Google Container Registry

---

## 6. Docker Architecture – End-to-End Flow

When you run:

```bash
docker run nginx
```

Flow:

1. Docker Client sends request
2. Docker Daemon checks local images
3. If not available → pulls from Docker Hub
4. Creates container
5. Allocates network
6. Starts process inside container

---

## 7. Docker Objects Overview

| Object     | Description                      |
| ---------- | -------------------------------- |
| Image      | Blueprint/template               |
| Container  | Running instance                 |
| Volume     | Persistent storage               |
| Network    | Communication between containers |
| Dockerfile | Script to build image            |

---

## 8. Dockerfile – Basic Concept

A Dockerfile defines how to build an image.

Example:

```dockerfile
FROM openjdk:17
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```

Each instruction creates a layer.

---

## 9. Docker Networking (High-Level Theory)

Docker provides:

1. **Bridge Network** (default)
2. **Host Network**
3. **None Network**
4. Overlay (for clustering)

Bridge network:

* Containers communicate using IP
* Port mapping required for host access

Example:

```bash
docker run -p 8080:80 nginx
```

---

## 10. Docker Storage – Volumes

Problem:

* Container data is lost when container is removed.

Solution:

* Docker Volumes

Volumes:

* Stored outside container
* Persistent
* Can be shared between containers

Example:

```bash
docker volume create mydata
```

---

## 11. Docker vs Traditional Deployment

| Feature      | Virtual Machine | Docker        |
| ------------ | --------------- | ------------- |
| OS per app   | Yes             | No            |
| Startup Time | Minutes         | Seconds       |
| Size         | GBs             | MBs           |
| Isolation    | Strong          | Process-level |
| Performance  | Moderate        | Near native   |

---

## 12. Docker in Modern Architecture

Docker is widely used in:

* Microservices architecture
* CI/CD pipelines
* DevOps workflows
* Cloud-native applications
* Kubernetes deployments

It integrates well with:

* Kubernetes
* Jenkins
* GitHub Actions

---

## 13. High-Level Docker Architecture Diagram (Conceptual)

```
                Docker Client
                       ↓
                Docker Daemon
          ┌────────────┼────────────┐
       Images       Containers     Networks
          ↓              ↓            ↓
       Registry       Running Apps   Communication
```

---

## 14. Advantages of Docker

* Environment consistency
* Faster deployment
* Easy scaling
* Better resource utilization
* Simplifies DevOps

---

## 15. Limitations of Docker

* Shared kernel (not full OS isolation)
* Security concerns if misconfigured
* Not ideal for GUI-heavy apps
* Windows containers differ from Linux containers

---

## 16. Summary

Docker is:

* A containerization platform
* Lightweight alternative to VMs
* Based on OS-level virtualization
* Core component of modern DevOps and cloud-native systems

---
