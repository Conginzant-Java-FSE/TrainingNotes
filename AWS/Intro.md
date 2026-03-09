# Introduction to AWS

Amazon Web Services (AWS) is a **cloud computing platform provided by** Amazon that allows developers and organizations to **build, deploy, and manage applications using scalable cloud infrastructure**.

Instead of purchasing physical servers and data centers, AWS allows users to **rent computing resources on demand**.

AWS follows a **pay-as-you-go model**, meaning you only pay for the resources you use.

---

# Why Use AWS?

Traditional Infrastructure Problems:

| Traditional Infrastructure       | AWS Cloud                  |
| -------------------------------- | -------------------------- |
| Requires buying physical servers | Virtual servers on demand  |
| High upfront cost                | Pay only for usage         |
| Difficult to scale               | Auto scaling               |
| Maintenance required             | AWS manages infrastructure |
| Slow deployment                  | Deploy within minutes      |

Advantages of AWS:

* High scalability
* Global infrastructure
* High availability
* Strong security
* Massive ecosystem of services
* Integration with modern architectures (microservices, containers, AI)

---

# AWS Global Infrastructure

AWS runs its services from **global data centers**.

### 1. Regions

A **Region** is a geographic area containing AWS infrastructure.

Example regions:

* US East (N. Virginia)
* Asia Pacific (Mumbai)
* Asia Pacific (Sydney)

Each region is **isolated for fault tolerance**.

---

### 2. Availability Zones (AZ)

Each region contains **multiple Availability Zones**.

An **Availability Zone** is a **separate data center with independent power, networking, and cooling**.

Example:

```
Region: Asia Pacific (Mumbai)

AZ1 – Data Center A
AZ2 – Data Center B
AZ3 – Data Center C
```

Applications can be deployed across AZs for **high availability**.

---

### 3. Edge Locations

Edge locations are used for **content delivery and caching**.

They are used by:

Amazon CloudFront

Purpose:

* Faster content delivery
* Lower latency

---

# AWS Service Categories

AWS provides **200+ services** grouped into categories.

Major categories include:

| Category   | Example Services |
| ---------- | ---------------- |
| Compute    | EC2, Lambda      |
| Storage    | S3, EBS          |
| Databases  | RDS, DynamoDB    |
| Networking | VPC, Route53     |
| Security   | IAM, KMS         |
| Containers | ECS, EKS         |
| DevOps     | CodePipeline     |
| Monitoring | CloudWatch       |

---

# Important AWS Services for Beginners

These are the **most important services students should understand first**.

---

# 1. Amazon EC2 (Elastic Compute Cloud)

Amazon EC2

EC2 provides **virtual servers in the cloud**.

Developers can run applications like:

* Spring Boot
* Node.js
* Python
* Docker containers

Example:

Instead of buying a server, you can launch a **virtual Linux machine**.

### Example EC2 Setup

```
Spring Boot Application
        ↓
Run on EC2 instance
        ↓
Accessible via Public IP
```

Instance types include:

| Instance Type | Usage                |
| ------------- | -------------------- |
| t2.micro      | Free tier            |
| t3.small      | Small workloads      |
| m5.large      | Production workloads |

---

# 2. Amazon S3 (Simple Storage Service)

Amazon S3

S3 is **object storage** used to store:

* Images
* Videos
* Documents
* Application backups
* Static websites

Example Use Cases:

* Angular build hosting
* Application logs
* File uploads

Structure:

```
S3
 ├── Bucket
 │     ├── image1.png
 │     ├── file.pdf
 │     └── video.mp4
```

Features:

* Unlimited storage
* 99.999999999% durability
* Versioning support

---

# 3. Amazon RDS (Relational Database Service)

Amazon RDS

RDS allows developers to run **managed relational databases**.

Supported databases:

* MySQL
* PostgreSQL
* Oracle
* SQL Server
* MariaDB

Example Architecture:

```
Spring Boot Application
        ↓
Connects to
        ↓
MySQL Database (RDS)
```

Benefits:

* Automated backups
* Automatic patching
* High availability
* Easy scaling

---

# 4. Amazon VPC (Virtual Private Cloud)

Amazon VPC

VPC allows users to create **private networks inside AWS**.

Inside a VPC you can create:

* Subnets
* Route tables
* Security groups
* Internet gateways

Example architecture:

```
VPC
 ├── Public Subnet
 │     └── EC2 Web Server
 │
 └── Private Subnet
       └── RDS Database
```

Benefits:

* Network isolation
* Secure architecture
* Controlled traffic

---

# 5. IAM (Identity and Access Management)

AWS Identity and Access Management

IAM manages **user permissions and access control**.

You can create:

* Users
* Roles
* Policies
* Groups

Example:

```
Developer User
     ↓
Policy
     ↓
Access only to EC2 and S3
```

Security best practices:

* Never use root account
* Use roles instead of credentials
* Enable MFA

---

# 6. AWS Lambda (Serverless Compute)

AWS Lambda

Lambda allows developers to run **code without managing servers**.

Example:

```
Upload file to S3
      ↓
Lambda triggers
      ↓
Process the file
```

Supported languages:

* Java
* Python
* Node.js
* Go

Benefits:

* No server management
* Automatic scaling
* Pay per execution

---

# 7. CloudWatch (Monitoring)

Amazon CloudWatch

CloudWatch is used for:

* Monitoring applications
* Logging
* Alerts
* Metrics

Example metrics:

* CPU usage
* Memory usage
* Network traffic

Example alert:

```
If CPU > 80%
Send alert notification
```

---

# 8. Amazon ECR (Container Registry)

Amazon Elastic Container Registry

ECR is used to **store Docker images**.

Example workflow:

```
Docker Build
     ↓
Push to ECR
     ↓
Deploy in ECS / Kubernetes
```

Useful for:

* Microservices
* Kubernetes deployments
* CI/CD pipelines

---

# 9. Amazon EKS (Kubernetes Service)

Amazon Elastic Kubernetes Service

EKS provides **managed Kubernetes clusters**.

Developers can deploy:

* Microservices
* Containers
* APIs

Architecture example:

```
EKS Cluster
   ├── Node
   │     ├── Pod (User Service)
   │     ├── Pod (Order Service)
   │     └── Pod (Payment Service)
```

---

# 10. AWS Route 53 (DNS Service)

Amazon Route 53

Route53 is AWS’s **Domain Name System (DNS)** service.

Example:

```
www.myapp.com
      ↓
Points to
      ↓
EC2 Load Balancer
```

Features:

* Domain registration
* DNS routing
* Health checks

---

# Example AWS Architecture for a Fullstack Project

Example **Java Fullstack Application Deployment**

```
User Browser
      ↓
Route53 (DNS)
      ↓
Load Balancer
      ↓
EC2 Instance (Spring Boot API)
      ↓
RDS (MySQL Database)
      ↓
S3 (File Storage)
```

Optional advanced architecture:

```
Angular App → S3
Spring Boot → EC2 / Kubernetes
Database → RDS
Images → S3
Monitoring → CloudWatch
```

---

# AWS Free Tier (Important for Students)

AWS Free Tier provides free usage for beginners.

Examples:

| Service | Free Usage                 |
| ------- | -------------------------- |
| EC2     | 750 hours/month (t2.micro) |
| S3      | 5 GB storage               |
| RDS     | 750 hours                  |
| Lambda  | 1 million requests         |

Free tier duration:

* **12 months after account creation**

---

# AWS Shared Responsibility Model

AWS divides responsibilities between **AWS and the user**.

| AWS Responsibility | User Responsibility    |
| ------------------ | ---------------------- |
| Hardware           | Application code       |
| Networking         | OS updates             |
| Data centers       | Security configuration |
| Infrastructure     | IAM policies           |

---

# Typical Workflow for Developers

Example development lifecycle:

```
Write Code (Spring Boot)
      ↓
Build Docker Image
      ↓
Push to ECR
      ↓
Deploy to EC2 / EKS
      ↓
Store data in RDS
      ↓
Monitor with CloudWatch
```

---

# Key AWS Concepts Beginners Must Know

| Concept           | Meaning                 |
| ----------------- | ----------------------- |
| Region            | Geographic AWS location |
| Availability Zone | Independent data center |
| Instance          | Virtual server          |
| Bucket            | Storage container in S3 |
| Security Group    | Firewall for instances  |
| VPC               | Private network         |
| AMI               | Machine image for EC2   |

---

# Conclusion

AWS provides a **complete cloud platform for building modern scalable applications**.

For beginners, the most important services to learn first are:

1. EC2
2. S3
3. RDS
4. VPC
5. IAM
6. Lambda
7. CloudWatch

These services form the **foundation for most real-world cloud architectures**.
