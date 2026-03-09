# AWS EC2 Complete Tutorial for Beginners

## 1. What is AWS EC2?

Amazon EC2 (Elastic Compute Cloud) is a service from Amazon Web Services that provides **virtual servers in the cloud**.

Instead of buying physical machines, developers can **create and manage virtual machines (instances)** on demand.

These virtual machines can run:

* Java applications
* Spring Boot services
* Node.js APIs
* Databases
* Docker containers
* Microservices

---

# 2. Why Use EC2?

Benefits of EC2:

| Feature       | Explanation                              |
| ------------- | ---------------------------------------- |
| Scalable      | Increase or decrease server size anytime |
| Pay-as-you-go | Only pay for what you use                |
| Flexible      | Supports Linux and Windows               |
| Fast setup    | Server creation in minutes               |
| Secure        | Integrated with AWS security             |

Example usage:

```
Local Development
        ↓
Push application
        ↓
Deploy on EC2
        ↓
Application accessible via Public IP
```

---

# 3. EC2 Important Concepts

## 3.1 Instance

An **instance** is a **virtual machine** running in AWS.

Example:

```
EC2 Instance
   ├── CPU
   ├── Memory
   ├── Storage
   └── Operating System
```

Instance types:

| Type     | Usage                |
| -------- | -------------------- |
| t2.micro | Free tier            |
| t3.small | Small applications   |
| m5.large | Production workloads |

---

## 3.2 AMI (Amazon Machine Image)

AMI is a **template used to create an EC2 instance**.

Examples:

* Amazon Linux
* Ubuntu
* Windows Server

AMI includes:

* Operating system
* Preinstalled packages
* Configuration

---

## 3.3 Security Groups

A **security group acts as a firewall** controlling traffic.

Example rules:

| Type   | Port | Purpose         |
| ------ | ---- | --------------- |
| SSH    | 22   | Remote login    |
| HTTP   | 80   | Web access      |
| HTTPS  | 443  | Secure web      |
| Custom | 8080 | Spring Boot API |

---

## 3.4 Key Pair

A **Key Pair** is used to securely connect to EC2 using SSH.

It includes:

```
Private Key (.pem)
Public Key (stored in AWS)
```

Example:

```
spring-key.pem
```

---

## 3.5 Elastic IP

An **Elastic IP** is a **static public IP address**.

Normally EC2 public IP changes when the instance restarts.

Elastic IP ensures:

```
Permanent public address
```

---

# 4. Steps to Create an EC2 Instance

Login to AWS Console:

[https://console.aws.amazon.com](https://console.aws.amazon.com)

Navigate to **EC2 Dashboard**.

---

## Step 1: Launch Instance

Click **Launch Instance**.

Select AMI:

Recommended for beginners:

```
Amazon Linux 2023
```

---

## Step 2: Choose Instance Type

Select:

```
t2.micro
```

This is included in the **AWS Free Tier**.

---

## Step 3: Create Key Pair

Create a key pair.

Example:

```
spring-key.pem
```

Download and store it safely.

---

## Step 4: Configure Security Group

Add rules:

| Type       | Port |
| ---------- | ---- |
| SSH        | 22   |
| HTTP       | 80   |
| Custom TCP | 8080 |

This allows access to your Spring Boot application.

---

## Step 5: Launch Instance

Click **Launch Instance**.

After creation you will see:

```
Instance ID
Public IP
Instance State
```

---

# 5. Connect to EC2 Instance

Use SSH to connect.

Example command:

```
ssh -i spring-key.pem ec2-user@<public-ip>
```

Example:

```
ssh -i spring-key.pem ec2-user@54.210.45.12
```

Now you are connected to the EC2 server.

---

# 6. Install Java on EC2

Spring Boot requires Java.

Install OpenJDK.

```
sudo yum update -y
sudo yum install java-17-amazon-corretto -y
```

Check Java version:

```
java -version
```

Example output:

```
openjdk version "17"
```

---

# 7. Install Maven (Optional)

If you want to build the project on EC2.

```
sudo yum install maven -y
```

Check version:

```
mvn -version
```

---

# 8. Build Spring Boot Application

In your local machine:

Build the project:

```
mvn clean package
```

Output:

```
target/
   springboot-demo-0.0.1-SNAPSHOT.jar
```

---

# 9. Upload Spring Boot JAR to EC2

Use SCP.

Example:

```
scp -i spring-key.pem springboot-demo.jar ec2-user@<public-ip>:~
```

Example:

```
scp -i spring-key.pem springboot-demo.jar ec2-user@54.210.45.12:~
```

Now the file is inside EC2.

---

# 10. Run Spring Boot Application

Login to EC2.

Run the JAR file.

```
java -jar springboot-demo.jar
```

Application starts:

```
Tomcat started on port(s): 8080
```

---

# 11. Access the Application

Open browser:

```
http://<public-ip>:8080
```

Example:

```
http://54.210.45.12:8080
```

Now the Spring Boot application is live.

---

# 12. Run Application in Background

Use **nohup**.

```
nohup java -jar springboot-demo.jar &
```

Check running process:

```
ps -ef | grep java
```

Stop application:

```
kill <process-id>
```

---

# 13. Auto Start Spring Boot with systemd (Recommended)

Create a service file.

```
sudo nano /etc/systemd/system/springboot.service
```

Example:

```
[Unit]
Description=Spring Boot Application
After=network.target

[Service]
User=ec2-user
ExecStart=/usr/bin/java -jar /home/ec2-user/springboot-demo.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```

Reload systemd:

```
sudo systemctl daemon-reload
```

Start service:

```
sudo systemctl start springboot
```

Enable auto start:

```
sudo systemctl enable springboot
```

---

# 14. Typical Production Architecture

Basic architecture:

```
User Browser
      ↓
EC2 Instance
      ↓
Spring Boot Application
```

More advanced architecture:

```
User
  ↓
Load Balancer
  ↓
EC2 Instances
  ↓
Spring Boot APIs
  ↓
RDS MySQL Database
```

Using:

* Amazon RDS
* Amazon EC2

---

# 15. Useful EC2 CLI Commands

Update packages:

```
sudo yum update
```

Check running processes:

```
top
```

Check disk usage:

```
df -h
```

Check memory:

```
free -m
```

---

# 16. Best Practices for EC2

Security:

* Never expose SSH to public if unnecessary
* Use security groups carefully
* Use IAM roles instead of credentials

Performance:

* Use load balancer for scaling
* Monitor CPU using Amazon CloudWatch

Cost control:

* Stop unused instances
* Use Free Tier when possible

---

# 17. Example Student Project Deployment Architecture

```
Angular Frontend
        ↓
S3 Static Hosting
        ↓
API Calls
        ↓
EC2 (Spring Boot Backend)
        ↓
RDS MySQL Database
```

Services used:

* Amazon S3
* Amazon EC2
* Amazon RDS

---

# Conclusion

Amazon EC2 is one of the **most fundamental AWS services** for deploying applications.

Using EC2 you can:

* Host APIs
* Run microservices
* Deploy fullstack applications
* Run Docker containers
* Connect to managed databases


