# Docker CLI Commands Tutorial 

---

# 1. Verify Docker Installation

```bash
docker --version
```

Check detailed system info:

```bash
docker info
```

List all available commands:

```bash
docker --help
```

---

# 2. Working with Docker Images

Images are read-only templates used to create containers.

---

## 2.1 Search Images

```bash
docker search nginx
```

Searches on Docker Hub.

---

## 2.2 Pull an Image

```bash
docker pull nginx
```

Pull specific version:

```bash
docker pull nginx:1.25
```

---

## 2.3 List Images

```bash
docker images
```

OR

```bash
docker image ls
```

---

## 2.4 Remove Image

```bash
docker rmi nginx
```

Force remove:

```bash
docker rmi -f nginx
```

---

## 2.5 Inspect Image

```bash
docker image inspect nginx
```

Shows JSON metadata.

---

# 3. Working with Containers

Containers are runtime instances of images.

---

## 3.1 Run a Container

```bash
docker run nginx
```

This:

* Pulls image if not present
* Creates container
* Starts container

---

## 3.2 Run in Detached Mode

```bash
docker run -d nginx
```

`-d` → runs in background

---

## 3.3 Run with Port Mapping

```bash
docker run -d -p 8080:80 nginx
```

Access via:

```
http://localhost:8080
```

---

## 3.4 Name a Container

```bash
docker run -d -p 8080:80 --name mynginx nginx
```

---

## 3.5 List Containers

Running containers:

```bash
docker ps
```

All containers:

```bash
docker ps -a
```

---

## 3.6 Stop Container

```bash
docker stop mynginx
```

---

## 3.7 Start Container

```bash
docker start mynginx
```

---

## 3.8 Restart Container

```bash
docker restart mynginx
```

---

## 3.9 Remove Container

```bash
docker rm mynginx
```

Force remove running container:

```bash
docker rm -f mynginx
```

---

## 3.10 Execute Command Inside Container

```bash
docker exec -it mynginx bash
```

`-it` → interactive terminal

---

## 3.11 View Logs

```bash
docker logs mynginx
```

Follow logs live:

```bash
docker logs -f mynginx
```

---

## 3.12 Inspect Container

```bash
docker inspect mynginx
```

---

# 4. Docker Volumes Commands

Volumes store persistent data.

---

## 4.1 Create Volume

```bash
docker volume create myvolume
```

---

## 4.2 List Volumes

```bash
docker volume ls
```

---

## 4.3 Inspect Volume

```bash
docker volume inspect myvolume
```

---

## 4.4 Remove Volume

```bash
docker volume rm myvolume
```

---

## 4.5 Use Volume in Container

```bash
docker run -d -v myvolume:/data nginx
```

---

# 5. Docker Network Commands

---

## 5.1 List Networks

```bash
docker network ls
```

---

## 5.2 Create Network

```bash
docker network create mynetwork
```

---

## 5.3 Run Container in Custom Network

```bash
docker run -d --name app --network mynetwork nginx
```

---

## 5.4 Inspect Network

```bash
docker network inspect mynetwork
```

---

## 5.5 Remove Network

```bash
docker network rm mynetwork
```

---

# 6. Docker Build Commands

Used when creating custom images.

---

## 6.1 Build Image

```bash
docker build -t myapp:1.0 .
```

`-t` → tag name
`.` → current directory (contains Dockerfile)

---

## 6.2 Tag Image

```bash
docker tag myapp:1.0 myrepo/myapp:1.0
```

---

## 6.3 Push Image to Registry

```bash
docker push myrepo/myapp:1.0
```

Login first:

```bash
docker login
```

---

# 7. Docker System Cleanup Commands

---

## 7.1 Remove Stopped Containers

```bash
docker container prune
```

---

## 7.2 Remove Unused Images

```bash
docker image prune
```

---

## 7.3 Remove Everything Unused

```bash
docker system prune
```

Remove all unused images too:

```bash
docker system prune -a
```

---

# 8. Docker Stats & Monitoring

---

## 8.1 View Resource Usage

```bash
docker stats
```

Shows:

* CPU
* Memory
* Network I/O

---

# 9. Practical Mini Workflow Example

### Step 1 – Pull Image

```bash
docker pull nginx
```

### Step 2 – Run with Port Mapping

```bash
docker run -d -p 8080:80 --name web nginx
```

### Step 3 – Check Running Containers

```bash
docker ps
```

### Step 4 – View Logs

```bash
docker logs web
```

### Step 5 – Stop and Remove

```bash
docker stop web
docker rm web
```

---

# 10. Most Important Commands for Interviews

| Category   | Important Commands                   |
| ---------- | ------------------------------------ |
| Images     | pull, images, rmi, build             |
| Containers | run, ps, stop, start, rm, logs, exec |
| Volumes    | volume create, volume ls             |
| Networks   | network ls, network create           |
| Cleanup    | system prune                         |

---

# 11. Command Summary Cheat Sheet

```
docker pull <image>
docker run -d -p host:container --name name image
docker ps -a
docker stop <container>
docker rm <container>
docker rmi <image>
docker logs -f <container>
docker exec -it <container> bash
docker build -t name:tag .
docker system prune -a
```

---
