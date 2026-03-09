
# AWS Foundational 

The deployment you built using **Angular + Spring Boot + MySQL** depends on several core AWS infrastructure components provided by Amazon Web Services.

Key foundations include:

* Virtual networking
* Identity and access control
* Remote server access
* Firewall and network security
* Storage architecture

---

# 1. Virtual Private Cloud (VPC)

Amazon VPC (Virtual Private Cloud) is a **virtual network inside AWS where all cloud resources run**.

When you create EC2 or RDS, they always run **inside a VPC**.

Think of VPC as your **private cloud data center network**.

Example architecture:

```
VPC (10.0.0.0/16)
│
├── Public Subnet
│     ├── Angular EC2
│     └── Spring Boot EC2
│
└── Private Subnet
      └── RDS MySQL
```

### Key Features

* IP address control
* Subnet isolation
* Routing configuration
* Network security

---

# 2. Subnets

A **Subnet** is a **smaller network inside a VPC**.

Subnets divide infrastructure into **public and private segments**.

### Public Subnet

Resources accessible from the internet.

Examples:

* Angular server
* Load balancers
* Public APIs

```
Public Subnet
 ├── Angular EC2
 └── Spring Boot EC2
```

Public subnets are connected to the internet through an **Internet Gateway**.

---

### Private Subnet

Resources not directly accessible from the internet.

Examples:

* Databases
* Internal services
* Message queues

```
Private Subnet
 └── RDS Database
```

This improves **security by isolating sensitive services**.

---

# 3. Internet Gateway

An **Internet Gateway (IGW)** allows resources inside a VPC to access the internet.

```
Internet
   │
Internet Gateway
   │
Public Subnet
   │
EC2 Instance
```

Without an Internet Gateway:

* EC2 instances cannot access the internet
* Users cannot access public servers

---

# 4. Route Tables

A **Route Table** determines how network traffic moves inside the VPC.

Example route table:

| Destination | Target           |
| ----------- | ---------------- |
| 0.0.0.0/0   | Internet Gateway |

Meaning:

```
All internet traffic → Internet Gateway
```

Example architecture:

```
Public Subnet
Route: 0.0.0.0/0 → IGW
```

Private subnet example:

```
Private Subnet
No internet route
```

---

# 5. Security Groups (Instance Firewall)

Security Groups act as **virtual firewalls for AWS resources**.

They control:

* inbound traffic
* outbound traffic

Example security group for Spring Boot:

| Type | Port | Source       |
| ---- | ---- | ------------ |
| SSH  | 22   | Developer IP |
| HTTP | 8080 | Angular EC2  |

This means:

```
Only Angular server can call Spring Boot APIs
```

Example RDS security group:

| Type  | Port | Source         |
| ----- | ---- | -------------- |
| MySQL | 3306 | Spring Boot SG |

This ensures:

```
Database not publicly accessible
```

---

# 6. Network Access Control Lists (NACL)

AWS Network ACL provides **subnet-level security control**.

Difference from security groups:

| Security Group | NACL         |
| -------------- | ------------ |
| Instance level | Subnet level |
| Stateful       | Stateless    |
| Simpler        | More complex |

Most beginner architectures rely primarily on **security groups**.

---

# 7. SSH (Secure Shell)

SSH allows developers to **securely connect to EC2 servers remotely**.

SSH uses encrypted communication.

Example command:

```
ssh -i springkey.pem ec2-user@EC2_PUBLIC_IP
```

Components:

| Component          | Purpose           |
| ------------------ | ----------------- |
| Private key (.pem) | Used by client    |
| Public key         | Stored in AWS     |
| Encryption         | Secure connection |

Example connection process:

```
Developer Machine
      │
      ▼
SSH Connection
      │
      ▼
EC2 Instance
```

SSH is required for:

* installing software
* deploying applications
* managing servers

---

# 8. Key Pairs

A **Key Pair** is used for secure authentication with EC2.

It contains:

```
Public Key → stored in AWS
Private Key → downloaded (.pem)
```

Example file:

```
springkey.pem
```

Important rules:

* Never share private keys
* Store securely
* Use restricted permissions

Example permission command:

```
chmod 400 springkey.pem
```

---

# 9. Identity and Access Management (IAM)

AWS Identity and Access Management (IAM) controls **who can access AWS resources**.

IAM manages:

* users
* roles
* policies
* permissions

Example IAM structure:

```
AWS Account
   │
   ├── Admin User
   ├── Developer User
   └── DevOps User
```

---

## IAM Policies

Policies define **allowed actions**.

Example policy:

```
Allow EC2 full access
Allow S3 read-only
```

Example JSON policy:

```
{
 "Effect": "Allow",
 "Action": "ec2:*",
 "Resource": "*"
}
```

---

## IAM Roles

Roles allow services to **access other AWS services securely**.

Example:

```
EC2 Instance
    │
IAM Role
    │
Access S3 Bucket
```

This removes the need for storing credentials in code.

---

# 10. Elastic IP

Elastic IP address is a **static public IP address**.

Normally:

```
EC2 restart → IP changes
```

Elastic IP ensures:

```
Permanent IP address
```

Useful for:

* APIs
* production servers
* DNS mapping

---

# 11. Elastic Block Store (EBS)

Amazon Elastic Block Store provides **persistent disk storage for EC2 instances**.

Example:

```
EC2 Instance
     │
EBS Volume
     │
Application Files
```

Features:

* persistent storage
* snapshots
* high performance SSD

---

# 12. Cloud Monitoring

Monitoring is provided by:

Amazon CloudWatch

CloudWatch tracks:

* CPU usage
* memory usage
* disk usage
* network traffic

Example metric:

```
CPU > 80%
Trigger alert
```

Logs from Spring Boot can also be monitored.

---

# 13. DNS (Domain Name System)

AWS DNS service:

Amazon Route 53

Instead of accessing:

```
http://54.210.43.12
```

You can use:

```
https://myapp.com
```

Route53 maps domain → server IP.

---

# Complete Architecture with Foundations

```
Internet
   │
Route53 DNS
   │
Internet Gateway
   │
VPC
│
├── Public Subnet
│     ├── Angular EC2
│     └── Spring Boot EC2
│
└── Private Subnet
      └── RDS MySQL
```

Security layers:

```
IAM → Identity control
Security Groups → Instance firewall
NACL → Subnet firewall
SSH → Secure server access
```

---

# AWS Foundations Checklist for Students

Before deploying applications students should understand:

| Concept          | Importance                |
| ---------------- | ------------------------- |
| VPC              | Network architecture      |
| Subnets          | Public vs private servers |
| Internet Gateway | Internet access           |
| Route Tables     | Traffic routing           |
| Security Groups  | Instance firewall         |
| SSH              | Remote server access      |
| IAM              | Access control            |
| EBS              | Storage                   |
| CloudWatch       | Monitoring                |

---

# Final Learning Path (Recommended)

For students learning AWS deployments:

1. AWS fundamentals
2. EC2 basics
3. RDS database deployment
4. Security groups and VPC networking
5. Angular + Spring Boot deployment
6. Docker containers
7. Kubernetes / EKS
8. CI/CD pipelines

---
