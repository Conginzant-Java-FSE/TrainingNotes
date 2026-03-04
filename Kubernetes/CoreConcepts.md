# Core Kubernetes Concepts

---

## 1) Cluster

![Image](https://kubernetes.io/images/docs/kubernetes-cluster-architecture.svg)

![Image](https://miro.medium.com/1%2AQWJijlj7kwd0hIYk8Wsnow.png)

![Image](https://www.researchgate.net/publication/325134898/figure/fig1/AS%3A639944045383683%401529585958913/Kubernetes-cluster-high-level-architecture.png)

![Image](https://platform9.com/media/kubernetes-constructs-concepts-architecture.jpg)

A **Kubernetes Cluster** is the complete system that runs and manages your containerized applications.

It consists of:

* **Control Plane** (manages the cluster)
* **Worker Nodes** (run application workloads)

### Responsibilities of a Cluster

* Scheduling containers
* Maintaining desired state
* Handling failures (self-healing)
* Managing networking
* Scaling applications

### Important Concept: Desired State Management

Kubernetes operates on a **declarative model**:
You declare the desired state (for example, “3 replicas”), and Kubernetes continuously ensures the actual state matches it.

---

## 2) Node

![Image](https://kubernetes.io/images/docs/components-of-kubernetes.svg)

![Image](https://developer.ibm.com/developer/default/blogs/kube-cri-overview/images/Kubelet_Docker_Architecture.png)

![Image](https://kubernetes.io/images/docs/kubernetes-cluster-architecture.svg)

![Image](https://platform9.com/media/kubernetes-constructs-concepts-architecture.jpg)

A **Node** is a physical or virtual machine inside the cluster that runs application workloads.

There are two types:

* Control Plane Node
* Worker Node

### Components Inside a Worker Node

1. **kubelet**

   * Agent running on each node
   * Communicates with the API server
   * Ensures containers inside Pods are running

2. **kube-proxy**

   * Handles networking rules
   * Enables Service communication

3. **Container Runtime**

   * Runs containers (for example, containerd)

### Key Idea

Nodes provide:

* CPU
* Memory
* Storage
* Network

Kubernetes schedules Pods onto Nodes based on available resources.

---

## 3) Pod

![Image](https://svg.template.creately.com/0WEHMSQdwTY)

![Image](https://matthewpalmer.net/kubernetes-app-developer/multi-container-pod-design.png)

![Image](https://opensource.com/sites/default/files/2022-05/1containerandpodnets.jpg)

![Image](https://ronaknathani.com/blog/2020/08/how-a-kubernetes-pod-gets-an-ip-address/bridge-networking.png)

A **Pod** is the smallest deployable unit in Kubernetes.

It can contain:

* One container (most common)
* Multiple tightly coupled containers (sidecar pattern)

### Important Characteristics

* Pods share:

  * Network (same IP address)
  * Storage volumes
* Pods are ephemeral (temporary by design)
* Each Pod gets its own IP inside the cluster

### Why Not Create Pods Directly?

Pods are not self-healing by themselves.
In production, they are managed by higher-level controllers like Deployments.

---

## 4) ReplicaSet

![Image](https://www.researchgate.net/publication/371543145/figure/fig2/AS%3A11431281415825806%401746050153374/Hierarchical-structure-of-Deployment-ReplicaSet-and-Pod-adapted-from-official.tif)

![Image](https://cdn.prod.website-files.com/626a25d633b1b99aa0e1afa7/66cc2d4577b03b09e01329d7_66178fbf49897bfcd11e6557_image2.jpeg)

![Image](https://developers.redhat.com/sites/default/files/operator-reconciliation-kube-only.png)

![Image](https://developers.redhat.com/sites/default/files/operator-controller-reconciliation.jpeg)

A **ReplicaSet** ensures that a specified number of identical Pods are always running.

### Example

If you define:

* replicas: 3

Kubernetes guarantees:

* Exactly 3 Pods running at all times

If:

* One Pod crashes → ReplicaSet creates a new one.

### Important Concept: Reconciliation Loop

The controller constantly compares:

* Desired replicas
* Actual running replicas

And fixes differences automatically.

### Practical Note

You rarely create ReplicaSets directly.
They are managed by Deployments.

---

## 5) Deployment

![Image](https://kubernetes.io/docs/tutorials/kubernetes-basics/public/images/module_06_rollingupdates2.svg)

![Image](https://platform9.com/media/kubernetes-constructs-concepts-architecture.jpg)

![Image](https://miro.medium.com/1%2AKtnpIx1twobr16FP7hvAUg.png)

![Image](https://argo-rollouts.readthedocs.io/en/stable/architecture-assets/argo-rollout-architecture.png)

A **Deployment** is a higher-level controller that manages ReplicaSets and Pods.

### Responsibilities

* Creates ReplicaSet
* Manages Pod replicas
* Performs rolling updates
* Supports rollback
* Enables zero-downtime deployments

### Rolling Update Example

If you update:

* Image version from v1 → v2

Kubernetes:

* Gradually replaces old Pods
* Maintains availability during update

### Why Deployment is Important

It enables production-grade application lifecycle management.

---

## 6) Service

![Image](https://assets.bytebytego.com/diagrams/0005-4-k8s-service-types.png)

![Image](https://kodekloud.com/kk-media/image/upload/v1752884975/notes-assets/images/Kubernetes-for-the-Absolute-Beginners-Hands-on-Tutorial-Services-NodePort/frame_750.jpg)

![Image](https://cdn.sanity.io/images/6icyfeiq/production/28c8e4297864fb721ca9d94d5491fd3d53039f15-1286x746.png?auto=format\&fit=max\&h=552\&q=75\&w=952)

![Image](https://www.atatus.com/blog/content/images/2023/10/load-balancer-kubernetes-services.png)

Pods are dynamic:

* Their IP addresses change
* They can be recreated anytime

A **Service** provides:

* Stable IP
* Stable DNS name
* Load balancing across Pods

### Service Types

1. **ClusterIP**

   * Default type
   * Internal communication only

2. **NodePort**

   * Exposes service on each Node’s IP and port

3. **LoadBalancer**

   * Creates cloud provider load balancer
   * Used in production environments

### Key Technical Concept

Services use:

* Label selectors
* kube-proxy networking rules
* iptables or IPVS

---

## 7) Namespace

![Image](https://cdn.buttercms.com/aZo3OEXbQ0iTiPyg7MwO)

![Image](https://storage.googleapis.com/prd-engineering-asset/2022/02/3dcdf178-data-flow.png)

![Image](https://miro.medium.com/1%2AbWgWxQzsTy7T7bMdklDZzg.png)

![Image](https://containous.ghost.io/content/images/2022/05/Multi-Cluster.jpg)

A **Namespace** is a logical partition inside a cluster.

It allows:

* Resource isolation
* Environment separation
* Team separation

### Example Use Cases

* dev
* qa
* prod
* team-a
* team-b

### Why Namespaces Matter

They:

* Prevent naming conflicts
* Enable resource quotas
* Improve multi-tenant architecture

---

## 8) Secret

![Image](https://d2908q01vomqb2.cloudfront.net/ca3512f4dfa95a03169c5a670a4c91a19b3077b4/2020/04/09/Screen-Shot-2020-04-09-at-6.54.55-PM.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/0%2AIqSixgSg53qKT4cv.png)

![Image](https://cdn.prod.website-files.com/626a25d633b1b99aa0e1afa7/67bd6cc556e4e3cd1a95514d_image3.jpg)

![Image](https://cdn.prod.website-files.com/635e4ccf77408db6bd802ae6/6704d695156a64ece7cf5a04_AD_4nXfX2cwvThqXrzoP34YAe6LKA_IG1vZDRyfMqt93iJYlOdBenirHSYWqT55SkMI-8ARfukhP6j8EAJDmsRdSK48qzFDIOCNvh2Xhp1m4dS8CS6U76GjmwGv4GvEAof3UcNmnd1jokyRO2Sdcojynp5tGL0zN.png)

A **Secret** stores sensitive data securely.

Examples:

* Database passwords
* API tokens
* TLS certificates

### How Secrets Are Used

They can be:

* Injected as environment variables
* Mounted as files inside Pods

### Important Security Note

Secrets are:

* Base64 encoded by default
* Not encrypted unless encryption at rest is configured

In production:

* Enable encryption at rest
* Use RBAC for access control

---

# Concept Relationship Summary

Cluster
→ Contains Nodes
→ Nodes run Pods
→ ReplicaSet maintains Pod count
→ Deployment manages ReplicaSet
→ Service exposes Pods
→ Namespace isolates resources
→ Secret secures sensitive data

---

# Final Conceptual Understanding

Kubernetes is built around these principles:

1. Declarative configuration
2. Desired state reconciliation
3. Self-healing systems
4. Horizontal scalability
5. Infrastructure abstraction
