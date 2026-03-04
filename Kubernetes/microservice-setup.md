# Kubernetes - Spring Boot Microservices Deployment


Assumption:

* All Docker images are already built and available in a registry.
* Example images:

  * `myrepo/config-server:1.0`
  * `myrepo/eureka-server:1.0`
  * `myrepo/api-gateway:1.0`
  * `myrepo/user-service:1.0`
  * `myrepo/order-service:1.0`
  * `mysql:8`

---

# 1. Target Architecture Overview

![Image](https://media2.dev.to/dynamic/image/width%3D800%2Cheight%3D%2Cfit%3Dscale-down%2Cgravity%3Dauto%2Cformat%3Dauto/https%3A%2F%2Fdev-to-uploads.s3.amazonaws.com%2Fuploads%2Farticles%2Fgrzeu54rgkzuonk8yx3x.png)

![Image](https://www.eksworkshop.com/assets/images/catalog-microservice-eaca1c3f701c42630b93e13e4c2d629a.webp)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AopwoTH3vt54NZ2RUuU5Www.png)

![Image](https://miro.medium.com/1%2Ae-tofjgSqic130AYtY91zg.png)

### Flow

Client → Ingress → API Gateway → User / Order Service
Services → Register with Eureka
Services → Load configuration from Config Server
User & Order Service → Connect to MySQL

---

# 2. Kubernetes Design Decisions (Theoretical Foundation)

Before writing YAML, understand architectural choices.

## 2.1 Why Separate Deployments?

Each microservice is deployed as a separate Deployment because:

* Independent scaling
* Independent versioning
* Fault isolation
* Rolling updates per service

Example:

* Order service under heavy load → scale only order-service

---

## 2.2 Why Services?

Pods are ephemeral. If a Pod restarts:

* Its IP changes
* Other services break

Kubernetes Service provides:

* Stable DNS
* Load balancing
* Service discovery inside cluster

Example DNS inside cluster:

```
http://user-service:8080
http://order-service:8080
```

---

## 2.3 Why Not Use Eureka Alone?

Eureka handles:

* Application-level service registry

Kubernetes handles:

* Infrastructure-level service discovery

In Kubernetes:

* You technically do NOT need Eureka
* But it is useful for hybrid deployments or learning Spring Cloud patterns

---

# 3. Namespace Creation

Create a dedicated namespace:

```bash
kubectl create namespace microservices
```

Set default namespace:

```bash
kubectl config set-context --current --namespace=microservices
```

---

# 4. MySQL Deployment

## 4.1 Create Secret for DB Password

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: mysql-secret
type: Opaque
data:
  MYSQL_ROOT_PASSWORD: cGFzc3dvcmQ=   # base64 of "password"
```

Apply:

```bash
kubectl apply -f mysql-secret.yaml
```

---

## 4.2 Persistent Volume Claim

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mysql-pvc
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 2Gi
```

---

## 4.3 MySQL Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
        - name: mysql
          image: mysql:8
          env:
            - name: MYSQL_ROOT_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: mysql-secret
                  key: MYSQL_ROOT_PASSWORD
          ports:
            - containerPort: 3306
          volumeMounts:
            - name: mysql-storage
              mountPath: /var/lib/mysql
      volumes:
        - name: mysql-storage
          persistentVolumeClaim:
            claimName: mysql-pvc
```

---

## 4.4 MySQL Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: mysql
spec:
  selector:
    app: mysql
  ports:
    - port: 3306
  type: ClusterIP
```

---

# 5. Config Server Deployment

## 5.1 Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: config-server
spec:
  replicas: 1
  selector:
    matchLabels:
      app: config-server
  template:
    metadata:
      labels:
        app: config-server
    spec:
      containers:
        - name: config-server
          image: myrepo/config-server:1.0
          ports:
            - containerPort: 8888
```

---

## 5.2 Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: config-server
spec:
  selector:
    app: config-server
  ports:
    - port: 8888
  type: ClusterIP
```

---

# 6. Eureka Server Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: eureka-server
spec:
  replicas: 1
  selector:
    matchLabels:
      app: eureka-server
  template:
    metadata:
      labels:
        app: eureka-server
    spec:
      containers:
        - name: eureka
          image: myrepo/eureka-server:1.0
          ports:
            - containerPort: 8761
```

Service:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: eureka-server
spec:
  selector:
    app: eureka-server
  ports:
    - port: 8761
  type: ClusterIP
```

---

# 7. User Service Deployment

Key environment variables:

* SPRING_DATASOURCE_URL
* EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
* SPRING_CONFIG_IMPORT

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: user-service
  template:
    metadata:
      labels:
        app: user-service
    spec:
      containers:
        - name: user-service
          image: myrepo/user-service:1.0
          env:
            - name: SPRING_DATASOURCE_URL
              value: jdbc:mysql://mysql:3306/userdb
            - name: EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
              value: http://eureka-server:8761/eureka/
            - name: SPRING_CONFIG_IMPORT
              value: configserver:http://config-server:8888
          ports:
            - containerPort: 8081
```

Service:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: user-service
spec:
  selector:
    app: user-service
  ports:
    - port: 8081
  type: ClusterIP
```

---

# 8. Order Service Deployment

Similar to user-service.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: order-service
  template:
    metadata:
      labels:
        app: order-service
    spec:
      containers:
        - name: order-service
          image: myrepo/order-service:1.0
          env:
            - name: SPRING_DATASOURCE_URL
              value: jdbc:mysql://mysql:3306/orderdb
            - name: EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
              value: http://eureka-server:8761/eureka/
          ports:
            - containerPort: 8082
```

Service created similarly.

---

# 9. API Gateway Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api-gateway
spec:
  replicas: 2
  selector:
    matchLabels:
      app: api-gateway
  template:
    metadata:
      labels:
        app: api-gateway
    spec:
      containers:
        - name: api-gateway
          image: myrepo/api-gateway:1.0
          env:
            - name: EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
              value: http://eureka-server:8761/eureka/
          ports:
            - containerPort: 8080
```

Service:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: api-gateway
spec:
  selector:
    app: api-gateway
  ports:
    - port: 80
      targetPort: 8080
  type: NodePort
```

---

# 10. Optional: Ingress (Production Style)

Instead of NodePort:

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: microservices-ingress
spec:
  rules:
    - host: myapp.local
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: api-gateway
                port:
                  number: 80
```

---

# 11. Deployment Order (Very Important)

1. Namespace
2. Secret
3. PVC
4. MySQL
5. Config Server
6. Eureka Server
7. User & Order Service
8. API Gateway
9. Ingress

Reason:
Services depend on DB and Config Server.

---

# 12. Theoretical Deep Concepts

## 12.1 Startup Dependency Problem

Microservices may fail if:

* Config server not ready
* Eureka not ready
* DB not ready

Solution:

* Readiness probes
* Retry configuration
* Init containers

---

## 12.2 Horizontal Scaling

Scale user-service:

```bash
kubectl scale deployment user-service --replicas=5
```

Load is automatically balanced via Service.

---

## 12.3 Failure Scenario Example

If one order-service Pod crashes:

* ReplicaSet creates new Pod
* Service automatically routes traffic to healthy Pods

Zero manual intervention required.

---

## 12.4 Config Change Without Rebuild

If using Spring Cloud Config:

* Update Git repo
* Refresh via actuator
* No Docker rebuild required

---

## 12.5 Production Enhancements

Add:

* Liveness probe
* Readiness probe
* Resource limits
* HPA
* TLS via Ingress
* RBAC
* NetworkPolicies

---

# 13. Complete Traffic Flow

1. Client hits Ingress
2. Ingress forwards to API Gateway
3. Gateway checks Eureka
4. Gateway routes to user-service
5. user-service queries MySQL
6. Response returns through gateway

---

# Final Architectural Understanding

This deployment demonstrates:

* Infrastructure-level service discovery (Kubernetes Service)
* Application-level service discovery (Eureka)
* Centralized configuration (Config Server)
* API routing (Gateway)
* Persistent storage (PVC)
* Load balancing (Service)
* Scalability (ReplicaSet)
