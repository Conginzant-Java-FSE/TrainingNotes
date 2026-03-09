
---

# Fullstack Deployment on AWS

### Angular + Spring Boot + MySQL (EC2 + RDS)

Services used:

* Amazon EC2 – host Angular and Spring Boot servers
* Amazon RDS – managed MySQL database
* Amazon VPC – networking
* Amazon S3 (optional) – static Angular hosting

---

# Final Architecture

```
User Browser
     │
     ▼
Angular Frontend (EC2)
Port: 80
     │
     ▼
Spring Boot Backend (EC2)
Port: 8080
     │
     ▼
MySQL Database (RDS)
Port: 3306
```

Security communication:

```
Angular EC2  → Spring Boot EC2 → RDS MySQL
```

---

# Step 1: Create Security Groups

Before launching instances, create **three security groups**.

Go to **EC2 → Security Groups → Create Security Group**

---

# 1 Angular Security Group

Name:

```
angular-sg
```

Inbound Rules:

| Type | Port | Source    | Purpose       |
| ---- | ---- | --------- | ------------- |
| HTTP | 80   | 0.0.0.0/0 | Public access |
| SSH  | 22   | Your IP   | Remote login  |

This allows users to access the Angular application.

---

# 2 Spring Boot Security Group

Name:

```
springboot-sg
```

Inbound Rules:

| Type       | Port | Source     | Purpose              |
| ---------- | ---- | ---------- | -------------------- |
| SSH        | 22   | Your IP    | SSH access           |
| Custom TCP | 8080 | angular-sg | Allow Angular server |
| Custom TCP | 8080 | Your IP    | Testing              |

Important rule:

```
Allow Angular EC2 to call Spring Boot API
```

---

# 3 RDS Security Group

Name:

```
rds-sg
```

Inbound Rules:

| Type         | Port | Source        |
| ------------ | ---- | ------------- |
| MySQL/Aurora | 3306 | springboot-sg |

Meaning:

```
Only Spring Boot server can access the database
```

This is **best practice security**.

---

# Step 2: Create RDS MySQL Database

Open AWS Console → RDS → Create Database.

Choose:

```
Standard Create
```

Engine:

```
MySQL
```

Version:

```
MySQL 8
```

---

## Database Settings

DB instance identifier

```
springboot-db
```

Master username

```
admin
```

Password

```
StrongPassword123
```

---

## Instance Type

Choose free tier:

```
db.t3.micro
```

---

## Storage

```
20 GB
```

---

## Connectivity Settings

VPC:

```
Default VPC
```

Public access:

```
No
```

Security group:

```
rds-sg
```

---

## Create Database

After creation, note:

```
RDS Endpoint
```

Example:

```
springboot-db.xxxxxx.ap-south-1.rds.amazonaws.com
```

---

# Step 3: Launch Spring Boot EC2

Go to EC2 → Launch Instance.

Settings:

Name:

```
springboot-server
```

AMI:

```
Amazon Linux 2023
```

Instance Type:

```
t2.micro
```

Security Group:

```
springboot-sg
```

Create key pair:

```
springkey.pem
```

Launch instance.

---

# Step 4: Connect to Spring Boot EC2

SSH into the instance.

```
ssh -i springkey.pem ec2-user@<springboot-public-ip>
```

---

# Step 5: Install Java

Install Java 17.

```
sudo yum update -y
sudo yum install java-17-amazon-corretto -y
```

Check version:

```
java -version
```

---

# Step 6: Configure Spring Boot Database

Edit **application.properties**

```
spring.datasource.url=jdbc:mysql://RDS-ENDPOINT:3306/mydb
spring.datasource.username=admin
spring.datasource.password=StrongPassword123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Example:

```
spring.datasource.url=jdbc:mysql://springboot-db.xxxxxx.ap-south-1.rds.amazonaws.com:3306/mydb
```

Important:

Spring Boot will create tables automatically.

---

# Step 7: Build Spring Boot Application

Run in local machine:

```
mvn clean package
```

Output:

```
target/app.jar
```

---

# Step 8: Upload JAR to EC2

Use SCP.

```
scp -i springkey.pem app.jar ec2-user@<springboot-public-ip>:~
```

Example:

```
scp -i springkey.pem app.jar ec2-user@43.204.88.90:~
```

---

# Step 9: Run Spring Boot

Inside EC2:

```
java -jar app.jar
```

Application runs on:

```
Port 8080
```

Test API:

```
http://SPRINGBOOT_PUBLIC_IP:8080/api/users
```

---

# Step 10: Launch Angular EC2

Create another instance.

Name:

```
angular-server
```

AMI:

```
Amazon Linux 2023
```

Instance Type:

```
t2.micro
```

Security Group:

```
angular-sg
```

---

# Step 11: Connect to Angular Server

```
ssh -i springkey.pem ec2-user@<angular-public-ip>
```

---

# Step 12: Install Node and Angular

Install NodeJS.

```
curl -fsSL https://rpm.nodesource.com/setup_18.x | sudo bash -
sudo yum install nodejs -y
```

Install Angular CLI.

```
sudo npm install -g @angular/cli
```

---

# Step 13: Build Angular Project

In local machine:

```
ng build
```

Output folder:

```
dist/project-name
```

---

# Step 14: Install Nginx

Angular will be served using Nginx.

```
sudo yum install nginx -y
```

Start service:

```
sudo systemctl start nginx
```

Enable auto start:

```
sudo systemctl enable nginx
```

---

# Step 15: Upload Angular Build

Upload using SCP.

```
scp -i springkey.pem -r dist/project-name ec2-user@<angular-ip>:~
```

Move files to Nginx.

```
sudo rm -rf /usr/share/nginx/html/*
sudo cp -r project-name/* /usr/share/nginx/html/
```

Restart nginx.

```
sudo systemctl restart nginx
```

---

# Step 16: Configure Angular API URL

Edit environment.ts.

```
export const environment = {
  production: true,
  apiUrl: "http://SPRINGBOOT_PUBLIC_IP:8080/api"
};
```

Example:

```
apiUrl: "http://43.204.88.90:8080/api"
```

Rebuild Angular.

```
ng build
```

Upload again.

---

# Step 17: Test the Application

Open browser:

```
http://ANGULAR_PUBLIC_IP
```

Flow:

```
User → Angular UI
Angular → Spring Boot API
Spring Boot → RDS MySQL
```

---

# Security Communication Flow

```
Internet
   │
   ▼
Angular EC2
Port 80 open to public
   │
   ▼
Spring Boot EC2
Port 8080 open only to Angular SG
   │
   ▼
RDS MySQL
Port 3306 open only to Spring Boot SG
```

This ensures **secure communication between services**.

---

# Production Improvements (Recommended)

Add:

Load Balancer:

```
AWS Application Load Balancer
```

Use:

```
HTTPS
```

Host Angular in:

```
S3 + CloudFront
```

Monitor with:

```
CloudWatch
```

---

# Complete Cloud Architecture

```
User Browser
      │
      ▼
Angular Server (EC2)
      │
 REST API
      ▼
Spring Boot Server (EC2)
      │
 JDBC
      ▼
MySQL Database (RDS)
```

---

# Cost (Free Tier Friendly)

Typical student deployment uses:

| Service | Monthly Free Tier |
| ------- | ----------------- |
| EC2     | 750 hours         |
| RDS     | 750 hours         |
| Storage | 20GB              |

This fits within **AWS Free Tier** if managed carefully.

---
