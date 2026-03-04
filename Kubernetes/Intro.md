# Kubernetes Introduction

## 1. What is Kubernetes?

![Image](https://upload.wikimedia.org/wikipedia/commons/thumb/3/39/Kubernetes_logo_without_workmark.svg/3840px-Kubernetes_logo_without_workmark.svg.png)

![Image](https://kubernetes.io/images/docs/kubernetes-cluster-architecture.svg)

![Image](https://securitypatterns.io/images/03-container-orchestration/overview.png)

![Image](https://learn.microsoft.com/en-us/azure/architecture/microservices/design/images/orchestration/multi-container-cluster-with-orchestrator.png)

**Kubernetes (K8s)** is an open-source **container orchestration platform** originally developed by Google and now maintained by the Cloud Native Computing Foundation.

It is used to:

* Deploy containers
* Scale applications
* Manage networking
* Handle failures automatically
* Perform rolling updates

In simple terms:

> Docker runs containers.
> Kubernetes manages containers at scale.

---

## 2. Why Do We Need Kubernetes?

Imagine you deployed:

* 5 Spring Boot containers
* 3 Angular containers
* 1 MySQL container

Now consider:

* One container crashes
* Traffic increases suddenly
* You need zero-downtime deployment
* You want auto-scaling

Managing this manually becomes impossible.

Kubernetes solves:

* High availability
* Load balancing
* Auto-scaling
* Self-healing
* Service discovery
* Configuration management

---

## 3. Kubernetes Architecture (Very Important)

![Image](https://kubernetes.io/images/docs/kubernetes-cluster-architecture.svg)

![Image](https://kubernetes.io/images/docs/components-of-kubernetes.svg)

![Image](https://media.licdn.com/dms/image/v2/D4D12AQFSDmDzoAh26A/article-inline_image-shrink_1000_1488/article-inline_image-shrink_1000_1488/0/1687453579458?e=2147483647\&t=9qt47_rtBG6NwO_7p-aF8zYRp4FHJaEcE3RxpAbUnCg\&v=beta)

![Image](https://www.researchgate.net/publication/334824445/figure/fig2/AS%3A786999057334272%401564646605324/This-diagram-illustrates-the-main-parts-The-master-node-representing-the-central-unit.jpg)

A Kubernetes cluster has **two main parts**:

## 3.1 Control Plane (Master Node)

This is the brain of Kubernetes.

### Key Components:

### 1️ kube-apiserver

* Entry point for all commands
* kubectl communicates with this
* Validates and processes requests

### 2️ etcd

* Distributed key-value store
* Stores cluster state
* Highly consistent storage

### 3️ kube-scheduler

* Decides which Pod runs on which Node
* Considers CPU, memory, constraints

### 4️ kube-controller-manager

* Ensures desired state matches actual state
* Examples:

  * Node Controller
  * Replication Controller

---

## 3.2 Worker Nodes

These run actual applications.

### Key Components:

### 1️ kubelet

* Agent running on each node
* Talks to API server
* Ensures containers are running

### 2️ kube-proxy

* Manages networking rules
* Enables service communication

### 3️ Container Runtime

Example:

* Docker
* containerd

Runs containers inside Pods.

---

## 4. Important Kubernetes Concepts (Core Technical Concepts)

---

# 4.1 Pod (Smallest Unit)

![Image](https://svg.template.creately.com/0WEHMSQdwTY)

![Image](https://matthewpalmer.net/kubernetes-app-developer/multi-container-pod-design.png)

![Image](https://opensource.com/sites/default/files/2022-05/1containerandpodnets.jpg)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1200/1%2AkQEAKUXMcCy5DtysZkiM0A.jpeg)

A **Pod** is:

* Smallest deployable unit
* Contains one or more containers
* Shares:

  * Network (IP)
  * Storage
  * Namespace

Example:

* Spring Boot container
* Sidecar logging container

### Important Characteristics:

* Ephemeral (can die anytime)
* Has unique IP
* Should not be created manually in production

---

# 4.2 Deployment

![Image](https://kubernetes.io/images/docs/kubernetes-cluster-architecture.svg)

![Image](https://kubernetes.io/docs/tutorials/kubernetes-basics/public/images/module_06_rollingupdates2.svg)

![Image](https://www.researchgate.net/publication/371543145/figure/fig2/AS%3A11431281415825806%401746050153374/Hierarchical-structure-of-Deployment-ReplicaSet-and-Pod-adapted-from-official.tif)

![Image](https://wiki.ciscolinux.co.uk/images/3/3b/ClipCapIt-191020-003257.PNG)

A **Deployment**:

* Manages Pods
* Ensures desired replica count
* Supports rolling updates
* Supports rollback

Example:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: spring
  template:
    metadata:
      labels:
        app: spring
    spec:
      containers:
      - name: spring
        image: myapp:v1
```

### Key Concept:

Kubernetes always maintains:

> Desired State = Actual State

---

# 4.3 Service (Networking Layer)

![Image](https://assets.bytebytego.com/diagrams/0005-4-k8s-service-types.png)

![Image](https://learn.redhat.com/t5/image/serverpage/image-id/7787iF3C1F78AC29162C4?v=v2)

![Image](https://cdn.sanity.io/images/6icyfeiq/production/28c8e4297864fb721ca9d94d5491fd3d53039f15-1286x746.png?auto=format\&fit=max\&h=552\&q=75\&w=952)

![Image](https://www.atatus.com/blog/content/images/2023/10/load-balancer-kubernetes-services.png)

Pods are dynamic. Their IPs change.

A **Service** provides:

* Stable IP
* DNS name
* Load balancing

### Types of Services:

### 1️ ClusterIP (Default)

* Internal communication
* Used between microservices

### 2️ NodePort

* Exposes service via Node IP + Port

### 3️ LoadBalancer

* Used in cloud
* Creates cloud load balancer

---

# 4.4 ReplicaSet

* Ensures a specific number of Pod replicas
* Deployment internally uses ReplicaSet
* Rarely created manually

---

# 4.5 Namespace

Logical separation inside cluster.

Example:

* dev
* qa
* prod

Command:

```bash
kubectl get pods -n dev
```

---

# 4.6 ConfigMap

Used to store:

* Non-sensitive configuration
* Properties
* Environment variables

Example:

```yaml
env:
- name: DB_HOST
  valueFrom:
    configMapKeyRef:
      name: db-config
      key: host
```

---

# 4.7 Secret

Used for:

* Passwords
* API keys
* Tokens

Secrets are base64 encoded (not encrypted by default).

---

# 4.8 Volume

Used for:

* Persistent storage
* Database data
* Shared files

Types:

* emptyDir
* hostPath
* PersistentVolume

---

# 4.9 Persistent Volume (PV) & Persistent Volume Claim (PVC)

PV → Actual storage
PVC → Request for storage

Database example:

* MySQL Pod
* PVC attached
* Data survives Pod restart

---

# 4.10 Ingress

![Image](https://tetrate.io/.netlify/images?h=549\&q=90\&url=_astro%2Fimage-1024x549.Dst0COpw.png\&w=1024)

![Image](https://d2908q01vomqb2.cloudfront.net/ca3512f4dfa95a03169c5a670a4c91a19b3077b4/2018/11/20/image1-1.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1200/1%2AKIVa4hUVZxg-8Ncabo8pdg.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/0%2A7970PSI1KE6Qskin.jpg)

Ingress:

* Manages external HTTP/HTTPS traffic
* Provides routing rules
* Requires Ingress Controller

Example:

* /api → Spring Boot
* / → Angular

---

# 5. Kubernetes Workflow (Step-by-Step)

1. Write YAML file
2. Run:

```bash
kubectl apply -f deployment.yaml
```

3. API Server validates
4. Scheduler assigns Node
5. kubelet pulls image
6. Container starts
7. Service exposes Pod

---

# 6. Important kubectl Commands

```bash
kubectl get pods
kubectl get svc
kubectl get deploy
kubectl describe pod <pod-name>
kubectl logs <pod-name>
kubectl exec -it <pod-name> -- bash
kubectl delete pod <pod-name>
```

---

# 7. Kubernetes Networking (Professional Explanation)

Each Pod:

* Gets unique IP
* Can communicate with other Pods

No NAT inside cluster.

Service:

* Uses kube-proxy
* Uses iptables or IPVS
* Load balances traffic

---

# 8. Self-Healing & Auto Scaling

## Self Healing

If Pod crashes:

* kubelet restarts
* Deployment recreates

## Horizontal Pod Autoscaler (HPA)

* Scales based on CPU
* Scales based on memory

---

# 9. Real-World Microservices Architecture Example

For your type of projects (Spring Boot + Angular + MySQL):

* Angular → NodePort / Ingress
* Spring Boot → ClusterIP
* MySQL → PVC + ClusterIP
* ConfigMap → application.properties
* Secret → DB password

---

# 10. Kubernetes vs Docker

| Feature           | Docker | Kubernetes   |
| ----------------- | ------ | ------------ |
| Container Runtime | Yes    | Uses runtime |
| Scaling           | Manual | Automatic    |
| Self Healing      | No     | Yes          |
| Load Balancing    | No     | Yes          |
| Rolling Updates   | No     | Yes          |

---

# 11. Learning Path (Recommended for You)

Since you train students on:

* Docker
* Spring Boot
* Angular
* CI/CD

Next logical progression:

1. Docker fundamentals
2. Kubernetes Pods
3. Deployments
4. Services
5. ConfigMap & Secrets
6. PV & PVC
7. Ingress
8. HPA
9. Helm (Advanced)
10. CI/CD with Kubernetes

---

# Final Summary

Kubernetes provides:

* Container orchestration
* Auto scaling
* Self healing
* Service discovery
* Rolling deployments
* High availability

It is the foundation of modern microservices architecture.

---
