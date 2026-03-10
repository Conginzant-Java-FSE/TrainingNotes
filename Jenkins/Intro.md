## Introduction to Jenkins 
**Jenkins** is an open-source **automation server** widely used for **Continuous Integration (CI)** and **Continuous Delivery/Deployment (CD)** in modern software development.

It helps automate repetitive tasks such as:

* Building applications
* Running tests
* Packaging software
* Deploying applications to servers or cloud

Jenkins is written in **Java** and runs on platforms like Windows, Linux, and macOS.

It integrates easily with tools such as:

* Git
* Docker
* Kubernetes
* Maven
* Gradle
* SonarQube

Because of this flexibility, Jenkins is one of the most widely used **DevOps tools**.

---

# 1. Why Jenkins is Used

Before Jenkins, developers performed many tasks manually.

### Traditional Development Process

1. Developer writes code
2. Pushes code to repository
3. Build is done manually
4. Testing is manual
5. Deployment is manual

This process causes:

* Slow development
* Frequent integration issues
* Human errors
* Late bug detection

### With Jenkins (CI/CD)

1. Developer pushes code
2. Jenkins automatically triggers a pipeline
3. Build is executed
4. Tests are executed
5. Code quality checks run
6. Application is packaged
7. Deployment happens automatically

This automation makes development **faster and reliable**.

---

# 2. Core Features of Jenkins

## 1. Continuous Integration (CI)

CI means **automatically integrating code changes frequently**.

Whenever a developer pushes code to a repository like GitHub or GitLab, Jenkins can:

* Pull the latest code
* Build the project
* Run tests
* Report failures

### Example

Developer pushes code → Jenkins triggers build.

```
Git Push → Jenkins Build → Run Tests → Success/Failure Report
```

Benefits:

* Detect bugs early
* Improve code quality
* Faster integration

---

## 2. Continuous Delivery (CD)

Continuous Delivery means **automatically preparing software for release**.

After successful build and tests, Jenkins can:

* Package the application
* Create Docker images
* Push artifacts to repository
* Deploy to staging server

Example:

```
Build → Test → Package → Deploy to Staging
```

---

## 3. Continuous Deployment

Continuous Deployment goes one step further.

If all tests pass, Jenkins can **automatically deploy to production**.

Example workflow:

```
Developer Push
      ↓
Build
      ↓
Unit Tests
      ↓
Integration Tests
      ↓
Docker Image Build
      ↓
Deploy to Kubernetes
```

Tools commonly integrated:

* Docker
* Kubernetes

---

## 4. Plugin Ecosystem

One of Jenkins’ biggest strengths is its **plugin system**.

Jenkins has **2000+ plugins** that extend its capabilities.

Examples:

| Plugin            | Purpose                         |
| ----------------- | ------------------------------- |
| Git Plugin        | Integrate with Git repositories |
| Docker Plugin     | Build and run Docker containers |
| Kubernetes Plugin | Deploy containers               |
| SonarQube Plugin  | Code quality scanning           |
| Slack Plugin      | Send notifications              |

Example integration:

```
Git → Jenkins → Maven → SonarQube → Docker → Kubernetes
```

---

## 5. Pipeline as Code

Jenkins pipelines can be defined using code in a file called:

```
Jenkinsfile
```

This allows:

* Version control of pipelines
* Easy replication
* Infrastructure as Code practices

Example Jenkins Pipeline:

```groovy
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker build -t spring-app .'
            }
        }
    }
}
```

---

## 6. Distributed Build System

Jenkins supports **Master-Agent architecture**.

### Components

| Component                   | Role           |
| --------------------------- | -------------- |
| Jenkins Controller (Master) | Manages jobs   |
| Agent (Worker Node)         | Executes tasks |

Example:

```
Jenkins Master
   │
   ├── Linux Agent (Build Java apps)
   ├── Windows Agent (Build .NET apps)
   └── Docker Agent (Container builds)
```

Benefits:

* Faster builds
* Parallel execution
* Scalability

---

## 7. Job Scheduling

Jenkins can run jobs:

* On code push
* On schedule (cron jobs)
* Manually
* On pipeline triggers

Example schedule:

```
Run build every day at midnight
```

Cron format:

```
0 0 * * *
```

---

## 8. Integration with DevOps Tools

Jenkins integrates with almost every DevOps tool.

Example **modern CI/CD pipeline**:

```
Developer
   ↓
GitHub
   ↓
Jenkins
   ↓
Maven Build
   ↓
SonarQube Scan
   ↓
Docker Build
   ↓
Push to Container Registry
   ↓
Deploy to Kubernetes
```

---

# 3. Jenkins Architecture

Basic Jenkins architecture:

```
Developer
    │
    ▼
Git Repository
    │
    ▼
Jenkins Server
    │
    ├── Build
    ├── Test
    ├── Package
    └── Deploy
```

In large systems:

```
            Jenkins Controller
                    │
      ┌─────────────┼─────────────┐
      │             │             │
   Agent 1       Agent 2       Agent 3
(Java Build)   (Docker Build)  (Tests)
```

---

# 4. Key Jenkins Concepts

| Concept   | Meaning                     |
| --------- | --------------------------- |
| Job       | Task executed by Jenkins    |
| Build     | Execution instance of a job |
| Pipeline  | Series of stages in CI/CD   |
| Node      | Machine running Jenkins     |
| Agent     | Worker machine for builds   |
| Workspace | Directory where builds run  |

---

# 5. Example Jenkins Workflow (Spring Boot Project)

Example CI/CD pipeline for a **Spring Boot application**:

1. Developer pushes code to Git
2. Jenkins pulls repository
3. Jenkins runs Maven build
4. Unit tests execute
5. Docker image is built
6. Image pushed to registry
7. Deployment to Kubernetes

Pipeline:

```
Git Push
   ↓
Jenkins
   ↓
Maven Build
   ↓
Tests
   ↓
Docker Build
   ↓
Deploy
```

---

# 6. Advantages of Jenkins

✔ Open source and free
✔ Huge plugin ecosystem
✔ Supports CI/CD pipelines
✔ Highly customizable
✔ Works with any programming language
✔ Integrates with most DevOps tools

---

# 7. Limitations of Jenkins

 Requires maintenance
 Plugin dependency issues
 UI is relatively old
 Setup complexity for beginners

Modern alternatives include:

* GitHub Actions
* GitLab CI/CD
* CircleCI

---

# 8. Where Jenkins is Commonly Used

Jenkins is used in projects involving:

* Microservices
* Cloud deployments
* Containerized applications
* Automated testing
* CI/CD pipelines

Common stack example:

```
Angular + Spring Boot + MySQL
        │
        ▼
      Jenkins
        │
        ▼
Docker → Kubernetes → AWS
```

---
