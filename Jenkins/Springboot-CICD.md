Below is a **complete beginner-friendly CI/CD tutorial** using **Jenkins**, **Amazon Web Services** EC2, and **Publish Over SSH** to deploy a **Spring Boot** application.

I also **verified your Jenkinsfile** and adjusted the tutorial around it.

---

# Jenkins CI/CD Tutorial

## Deploy Spring Boot App to AWS EC2 using Publish Over SSH

### Architecture Overview

CI/CD flow:

```
Developer
   │
   ▼
Git Repository
   │
   ▼
Jenkins Pipeline
   │
   ├── Build (Maven)
   ├── Test
   ├── Package JAR
   └── Deploy to AWS EC2 via SSH
```

Technologies used:

* Jenkins
* Apache Maven
* Spring Boot
* Amazon Web Services
* Git

---

# Step 1 — Create AWS EC2 Instance

Login to **AWS Management Console**

### Launch Instance

Settings:

| Setting        | Value                |
| -------------- | -------------------- |
| Name           | springboot-server    |
| AMI            | Amazon Linux 2023    |
| Instance Type  | t2.micro (free tier) |
| Key pair       | create new key       |
| Security Group | Allow SSH (22)       |

Add **custom port for Spring Boot**

```
TCP 8000
```

Launch instance.

---

# Step 2 — Connect to EC2

Use SSH.

Example:

```bash
ssh -i springkey.pem ec2-user@<EC2_PUBLIC_IP>
```

---

# Step 3 — Install Java on EC2

Your pipeline uses **JDK 21**.

Install Java:

```bash
sudo dnf update -y
sudo dnf install java-21-amazon-corretto -y
```

Verify:

```bash
java -version
```

Expected:

```
openjdk version "21"
```

---

# Step 4 — Install Jenkins

Install Jenkins on a **separate machine or server**.

Recommended: Linux VM.

Install Java first:

```bash
sudo apt install openjdk-21-jdk -y
```

Install Jenkins:

```bash
wget -O /usr/share/keyrings/jenkins-keyring.asc \
https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
/etc/apt/sources.list.d/jenkins.list > /dev/null

sudo apt update
sudo apt install jenkins -y
```

Start Jenkins:

```bash
sudo systemctl start jenkins
sudo systemctl enable jenkins
```

Open:

```
http://SERVER_IP:8080
```

Unlock Jenkins.

---

# Step 5 — Install Required Jenkins Plugins

Go to:

```
Manage Jenkins → Plugins
```

Install:

| Plugin            | Purpose          |
| ----------------- | ---------------- |
| Git Plugin        | Pull source code |
| Pipeline Plugin   | Pipeline jobs    |
| Publish Over SSH  | Deploy to EC2    |
| Maven Integration | Build projects   |

Restart Jenkins.

---

# Step 6 — Configure Tools in Jenkins

Go to:

```
Manage Jenkins → Global Tool Configuration
```

### Configure JDK

Add:

```
Name: JDK21
Install automatically
```

### Configure Maven

```
Name: Maven3
Install automatically
```

These names must match the **Jenkinsfile tools section**.

```
tools{
    jdk "JDK21"
    maven "Maven3"
}
```

---

# Step 7 — Configure Publish Over SSH

Go to:

```
Manage Jenkins → System
```

Find:

```
Publish over SSH
```

Add new server.

### Configuration

| Field            | Value          |
| ---------------- | -------------- |
| Name             | ec2-server     |
| Hostname         | EC2 Public IP  |
| Username         | ec2-user       |
| Remote Directory | /home/ec2-user |

Authentication:

Use **private key from EC2 keypair**.

Paste contents of:

```
springkey.pem
```

Test connection.

Expected:

```
Success
```

---

# Step 8 — Create Spring Boot Project

Example structure:

```
SpringbootDemo
 ├── src
 ├── pom.xml
 └── Jenkinsfile
```

Build jar manually to confirm:

```
mvn clean package
```

Jar will appear in:

```
target/SpringbootDemo-0.0.1-SNAPSHOT.jar
```

This matches your pipeline variable:

```
JAR_NAME = "SpringbootDemo-0.0.1-SNAPSHOT.jar"
```

---

# Step 9 — Create Jenkins Pipeline Job

In Jenkins:

```
New Item → Pipeline
```

Name:

```
springboot-cicd
```

Select:

```
Pipeline script from SCM
```

Repository:

```
Git URL
```

Script Path:

```
Jenkinsfile
```

---

# Step 10 — Verified Jenkinsfile (Your Script)

Your pipeline is **mostly correct**.
But one **important improvement** is needed.

Your script uses:

```
bat
```

This works **only on Windows Jenkins agents**.

If Jenkins runs on **Linux**, use:

```
sh
```

### Correct Version (Recommended)

```groovy
pipeline{
    agent any

    tools{
        jdk "JDK21"
        maven "Maven3"
    }

    options {
        skipStagesAfterUnstable()
    }

    environment{
        JAR_NAME = "SpringbootDemo-0.0.1-SNAPSHOT.jar"
        REMOTE_DIR = "/home/ec2-user"
        APP_PORT = "8000"
    }

    stages{

        stage("Checkout"){
            steps{
                checkout scm
            }
        }

        stage("Build"){
            steps{
                sh 'mvn -B clean compile'
            }
        }

        stage("Test"){
            steps{
                sh 'mvn -B test'
            }
        }

        stage("Package"){
            steps{
                sh 'mvn -B package -DskipTests'
            }
        }

        stage("Deploy to EC2"){
            steps{
                sshPublisher(
                    publishers:[
                        sshPublisherDesc(
                            configName:"ec2-server",
                            verbose: true,
                            transfers:[
                                sshTransfer(
                                    sourceFiles:"target/${JAR_NAME}",
                                    removePrefix:"target/",
                                    remoteDirectory:"${REMOTE_DIR}",
                                    flatten:true,
                                    execCommand:"""
                                    pkill -f "java -jar" || true
                                    nohup java -jar ${JAR_NAME} --server.port=${APP_PORT} > application.log 2>&1 &
                                    """
                                )
                            ]
                        )
                    ]
                )
            }
        }

    }
}
```

---

# Step 11 — Run Pipeline

Click:

```
Build Now
```

Pipeline stages:

```
Checkout
Build
Test
Package
Deploy
```

If successful:

```
jar created successfully
deployed successfully
```

---

# Step 12 — Verify Application

Open browser:

```
http://EC2_PUBLIC_IP:8000
```

Example output:

```
Hello from Spring Boot deployed via Jenkins CI/CD!
```

---

# Step 13 — Verify Application Logs

SSH to EC2.

Check log:

```
cat application.log
```

Check running process:

```
ps -ef | grep java
```

---

# Final CI/CD Flow

```
Git Push
   │
   ▼
Jenkins
   │
   ├── Build (Maven)
   ├── Test
   ├── Package JAR
   └── SSH Deploy
           │
           ▼
        AWS EC2
           │
           ▼
   Spring Boot Application
```

---

# Common Beginner Errors

| Problem                 | Cause                       |
| ----------------------- | --------------------------- |
| Permission denied SSH   | Wrong private key           |
| Jar not found           | Wrong JAR_NAME              |
| EC2 port not accessible | Security group not opened   |
| Pipeline fails          | JDK or Maven not configured |

---
