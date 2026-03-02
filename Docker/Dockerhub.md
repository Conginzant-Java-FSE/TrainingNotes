# Docker Hub Tutorial 
## 1. What is Docker Hub?

Docker Hub is a **cloud-based container image registry** provided by Docker.

It allows you to:

* Store Docker images
* Share images publicly or privately
* Version container images
* Automate image builds
* Integrate with CI/CD pipelines

Think of Docker Hub like **GitHub for Docker images**.

---

## 2. Docker Hub Architecture Overview

![Image](https://assets.bytebytego.com/diagrams/0414-how-does-docker-work.png)

![Image](https://miro.medium.com/1%2AbzWF8IYIhrSB-rh45pqFpw.png)

![Image](https://miro.medium.com/1%2AuuZ-h5EH76LOtJ614z-qDA.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1200/1%2ALQis_Wh44VXQkiW87hhsAw.png)

### Flow:

1. Developer builds Docker image locally
2. Image is tagged
3. Image is pushed to Docker Hub
4. Another user pulls image
5. Container runs anywhere

---

# 3. Prerequisites

You need:

* Docker installed
* Docker Hub account
* Internet connection

Check Docker:

```bash
docker --version
```

---

# 4. Creating Docker Hub Account

1. Go to [https://hub.docker.com](https://hub.docker.com)
2. Sign up
3. Verify email

After login, you can create repositories.

---

# 5. Understanding Docker Hub Terminology

| Term       | Meaning                             |
| ---------- | ----------------------------------- |
| Repository | Collection of Docker images         |
| Image      | Read-only template for containers   |
| Tag        | Version of image (e.g., latest, v1) |
| Namespace  | Your username or org name           |
| Push       | Upload image                        |
| Pull       | Download image                      |

---

# 6. Creating a Repository

1. Login to Docker Hub
2. Click **Create Repository**
3. Enter:

   * Name: `my-node-app`
   * Visibility: Public / Private
4. Click Create

Repository format:

```
username/repository-name
```

Example:

```
arulanguru/my-node-app
```

---

# 7. Login from Terminal

```bash
docker login
```

Enter:

* Username
* Password

Successful login:

```
Login Succeeded
```

---

# 8. Building a Docker Image

### Example: Simple Node App

Create `app.js`:

```js
console.log("Hello Docker Hub");
```

Create `Dockerfile`:

```dockerfile
FROM node:18
WORKDIR /app
COPY app.js .
CMD ["node", "app.js"]
```

Build image:

```bash
docker build -t my-node-app .
```

---

# 9. Tagging Image for Docker Hub

You must tag with:

```
username/repository:tag
```

Example:

```bash
docker tag my-node-app arulanguru/my-node-app:v1
```

Now check:

```bash
docker images
```

---

# 10. Push Image to Docker Hub

```bash
docker push arulanguru/my-node-app:v1
```

Output:

```
Pushed successfully
```

Now image is available globally.

---

# 11. Pulling Image from Docker Hub

Anyone can pull:

```bash
docker pull arulanguru/my-node-app:v1
```

Run container:

```bash
docker run arulanguru/my-node-app:v1
```

---

# 12. Image Versioning Strategy (Professional Practice)

Never depend only on:

```
latest
```

Use semantic versioning:

```
v1.0.0
v1.1.0
v2.0.0
```

Example:

```bash
docker tag my-node-app arulanguru/my-node-app:1.0.0
docker push arulanguru/my-node-app:1.0.0
```

---

# 13. Public vs Private Repositories

| Public               | Private                            |
| -------------------- | ---------------------------------- |
| Anyone can pull      | Restricted access                  |
| Free                 | Limited private repos in free plan |
| Good for open source | Good for enterprise                |

---

# 14. Automated Builds (CI/CD Integration)

Docker Hub can connect with:

* GitHub
* Bitbucket

Workflow:

1. Connect repository
2. Every code push triggers image build
3. Auto-push to Docker Hub

This eliminates manual `docker build` and `docker push`.

---

# 15. Organizations in Docker Hub

Organizations allow:

* Team collaboration
* Role-based access
* Centralized repository management

Used in companies.

---

# 16. Docker Hub Web Interface Overview

![Image](https://docker-docs.uclv.cu/docker-hub/images/repos-create.png)

![Image](https://kodekloud.com/blog/content/images/2023/05/data-src-image-66e146c5-d3f2-471a-868f-93772598087a.png)

![Image](https://docs.docker.com/docker-hub/repos/manage/builds/images/index-dashboard.png)

![Image](https://docs.docker.com/docker-hub/repos/manage/builds/images/index-active.png)

You can view:

* Tags
* Pull count
* Last updated
* Vulnerability scanning
* Build history

---

# 17. Security & Best Practices

### 1. Scan Images

Docker Hub supports vulnerability scanning.

### 2. Use Official Images

Examples:

* `node`
* `mysql`
* `nginx`

### 3. Minimize Image Size

Use:

```
node:18-alpine
```

### 4. Do Not Push Secrets

Never include:

* `.env`
* Private keys
* Credentials

---

# 18. Deleting Images

From Web UI:

Repository → Tags → Delete tag

Or delete entire repository.

---

# 19. Useful Docker Hub Commands

```bash
docker login
docker logout
docker build -t name .
docker tag image username/repo:tag
docker push username/repo:tag
docker pull username/repo:tag
docker search nginx
```

---

# 20. Real-Time Professional Workflow Example

Angular + Spring Boot + MySQL project:

Step 1: Build backend image
Step 2: Tag → push to Docker Hub
Step 3: Build frontend image
Step 4: Push
Step 5: In AWS EC2 → Pull images
Step 6: Run using Docker Compose

Docker Hub becomes central image registry.

---

# 21. Docker Hub vs Private Registry

| Docker Hub               | Private Registry    |
| ------------------------ | ------------------- |
| Cloud hosted             | Self-hosted         |
| Easy setup               | Requires infra      |
| Free tier                | Fully controlled    |
| Good for public projects | Good for enterprise |

Examples of private registries:

* AWS ECR
* GitHub Container Registry
* Azure Container Registry

---

# 22. Interview Questions

1. What is Docker Hub?
2. Difference between push and pull?
3. What is tagging?
4. Why avoid latest tag?
5. How to secure Docker images?
6. What is automated build?
7. Public vs Private repo difference?

---

# Final Summary

Docker Hub provides:

* Central image storage
* Version control for containers
* CI/CD integration
* Public & private hosting
* Team collaboration

It is essential in modern DevOps pipelines.

---
