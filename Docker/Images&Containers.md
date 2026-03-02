# Docker Images, Containers & Dockerfile 

# 1. Application Architecture Overview

## Logical Architecture

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2A4BrCpPp0_Ij95NBx5JU81w.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1123/1%2AcjmroaJtu8f9CBnG-2wXzw.jpeg)

![Image](https://miro.medium.com/1%2AhgqSEzcPJcs2uof_vfuYoQ.png)

![Image](https://user-images.githubusercontent.com/86077654/141684313-71b57416-fa47-4010-9b8c-60d981a8efce.png)

### Flow:

```
Browser
   ↓
Angular (Frontend)
   ↓ REST API
Spring Boot (Backend)
   ↓ JDBC
MySQL Database
```

---

## Dockerized Architecture

![Image](https://miro.medium.com/1%2ArF5CG4AMu8TdPebH3ooSFw.jpeg)

![Image](https://blogs.cisco.com/gcs/ciscoblogs/1/2022/08/docker-bridge-1-768x478.jpeg)

![Image](https://www.researchgate.net/publication/369941890/figure/fig4/AS%3A11431281158901786%401684248796927/Multiple-Docker-containers-architecture-running-on-single-host-Operating-system.png)

![Image](https://almablog-media.s3.ap-south-1.amazonaws.com/docker_architecture_d8ec8e9cb8.png)

Now each component runs in its own container:

```
[ Angular Container ]  →  [ Spring Boot Container ]  →  [ MySQL Container ]
```

All containers communicate using a Docker network.

---

# 2. Docker Concepts (Using This Example)

## 2.1 What is a Docker Image?

An image is:

* A blueprint
* Read-only
* Built from a Dockerfile

Example images:

* MySQL image from Docker Hub
* Custom Spring Boot image
* Custom Angular image

---

## 2.2 What is a Container?

A container is:

* A running instance of an image
* Has its own filesystem layer
* Runs as an isolated process

Example:

```bash
docker run mysql
```

Creates:

* MySQL container
* Running MySQL process inside it

---

# 3. MySQL Container Setup

Instead of installing MySQL locally, we use official image:

```bash
docker run -d \
  --name mysqldb \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=myapp \
  -p 3306:3306 \
  mysql:8
```

What happens:

* Docker pulls MySQL image
* Creates container
* Initializes database
* Exposes port 3306

---

# 4. Spring Boot + Docker

## Step 1 – Typical Spring Boot Project Structure

```
springboot-app/
 ├── src/
 ├── pom.xml
 └── target/app.jar
```

Build JAR first:

```bash
mvn clean package
```

---

## Step 2 – Dockerfile for Spring Boot

Create a file named `Dockerfile`:

```dockerfile
# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/app.jar app.jar

# Expose application port
EXPOSE 8080

# Run application
ENTRYPOINT ["java","-jar","app.jar"]
```

---

## Step 3 – Build Spring Boot Image

```bash
docker build -t my-springboot-app .
```

This creates a custom image.

Check images:

```bash
docker images
```

---

## Step 4 – Run Spring Boot Container

```bash
docker run -d \
  --name backend \
  --network mynetwork \
  -p 8080:8080 \
  my-springboot-app
```

Important:

* Use same Docker network as MySQL
* Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://mysqldb:3306/myapp
spring.datasource.username=root
spring.datasource.password=root
```

Notice:
Instead of localhost → use container name `mysqldb`.

---

# 5. Angular + Docker

## Step 1 – Angular Production Build

Inside Angular project:

```bash
ng build --configuration production
```

Output folder:

```
dist/my-angular-app/
```

---

## Step 2 – Dockerfile for Angular

We use Nginx to serve Angular app.

```dockerfile
# Stage 1 – Build
FROM node:18 as build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Stage 2 – Serve
FROM nginx:alpine

COPY --from=build /app/dist/my-angular-app /usr/share/nginx/html

EXPOSE 80
CMD ["nginx","-g","daemon off;"]
```

This is called a **multi-stage build**.

---

## Step 3 – Build Angular Image

```bash
docker build -t my-angular-app .
```

---

## Step 4 – Run Angular Container

```bash
docker run -d \
  --name frontend \
  --network mynetwork \
  -p 80:80 \
  my-angular-app
```

Access:

```
http://localhost
```

---

# 6. Docker Network Creation

Create a custom bridge network:

```bash
docker network create mynetwork
```

All containers must join this network:

* mysqldb
* backend
* frontend

---

# 7. Full Flow (Step-by-Step Execution Order)

### 1. Create network

```bash
docker network create mynetwork
```

### 2. Start MySQL

```bash
docker run -d --name mysqldb --network mynetwork \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_DATABASE=myapp \
mysql:8
```

### 3. Build & Start Spring Boot

```bash
docker build -t my-springboot-app .
docker run -d --name backend --network mynetwork -p 8080:8080 my-springboot-app
```

### 4. Build & Start Angular

```bash
docker build -t my-angular-app .
docker run -d --name frontend --network mynetwork -p 80:80 my-angular-app
```

---

# 8. Image vs Container in This Example

| Component   | Image             | Container |
| ----------- | ----------------- | --------- |
| MySQL       | mysql:8           | mysqldb   |
| Spring Boot | my-springboot-app | backend   |
| Angular     | my-angular-app    | frontend  |

Image = Blueprint
Container = Running instance

---

# 9. Important Dockerfile Concepts Explained

## FROM

Base image

## WORKDIR

Sets working directory inside container

## COPY

Copies files from host to container

## RUN

Executes command during build

## EXPOSE

Documents port

## CMD / ENTRYPOINT

Defines default startup command

---

# 10. Common Beginner Mistakes

1. Using localhost inside container
   → Must use container name

2. Not creating network
   → Containers can’t communicate

3. Not exposing ports

4. Forgetting to build jar before Docker build

---

# 11. Real-World Interview Explanation

If interviewer asks:

“How do you Dockerize Spring Boot + Angular + MySQL?”

Answer structure:

1. Use official MySQL image
2. Create custom image for Spring Boot
3. Use multi-stage build for Angular
4. Create Docker bridge network
5. Connect containers via service names
6. Use environment variables for DB config

---

