# kubectl Commands Tutorial (Beginner to Strong Foundation)

## 1. What is kubectl?

![Image](https://civo-com-assets.ams3.digitaloceanspaces.com/content_images/2122.blog.png?1671122314=)

![Image](https://kubernetes.io/images/docs/kubernetes-cluster-architecture.svg)

![Image](https://matthewpalmer.net/kubernetes-app-developer/articles/deployment-yaml-diagram.gif)

![Image](https://www.researchgate.net/publication/349515063/figure/fig12/AS%3A994684541341699%401614162683530/An-example-of-the-Kubernetes-yaml-script-The-script-encloses-a-PBS-script-Submission-is.png)

**kubectl** is the command-line tool used to interact with a Kubernetes cluster.

It communicates with the Kubernetes API Server and allows you to:

* Deploy applications
* Inspect cluster state
* Debug issues
* Scale workloads
* Manage resources

Architecture flow:

`kubectl → API Server → etcd / Scheduler / Controllers → Nodes`

---

# 2. Basic Syntax Structure

```
kubectl <command> <resource-type> <resource-name> [flags]
```

Examples:

```
kubectl get pods
kubectl describe pod my-app
kubectl delete deployment spring-app
```

---

# 3. Checking Cluster Information

## 3.1 Check Cluster Connection

```
kubectl cluster-info
```

Shows:

* API server endpoint
* Core services

---

## 3.2 Check Nodes

```
kubectl get nodes
```

Output includes:

* Node name
* Status (Ready/NotReady)
* Roles
* Age
* Version

Detailed view:

```
kubectl describe node <node-name>
```

This shows:

* CPU capacity
* Memory capacity
* Allocated resources
* Running pods

---

# 4. Listing Resources (get command)

The most commonly used command is `get`.

## 4.1 Get Pods

```
kubectl get pods
```

With more details:

```
kubectl get pods -o wide
```

Shows:

* Pod IP
* Node where it runs
* Container image

Across all namespaces:

```
kubectl get pods -A
```

---

## 4.2 Get Deployments

```
kubectl get deployments
```

---

## 4.3 Get Services

```
kubectl get svc
```

---

## 4.4 Get ReplicaSets

```
kubectl get rs
```

---

# 5. Inspecting Resources (describe command)

The `describe` command gives deep technical details.

## Example: Describe Pod

```
kubectl describe pod my-app-pod
```

Important sections:

* Events (very important for debugging)
* Container status
* Image
* Restart count
* Environment variables

Use this when:

* Pod is in CrashLoopBackOff
* ImagePullBackOff
* Pending state

---

# 6. Viewing Logs

## 6.1 View Pod Logs

```
kubectl logs <pod-name>
```

If multiple containers exist:

```
kubectl logs <pod-name> -c <container-name>
```

Stream logs:

```
kubectl logs -f <pod-name>
```

View previous container logs (after restart):

```
kubectl logs --previous <pod-name>
```

This is critical for production debugging.

---

# 7. Creating and Applying Resources

## 7.1 Apply YAML File

```
kubectl apply -f deployment.yaml
```

This:

* Creates resource if not present
* Updates resource if already exists

Apply entire folder:

```
kubectl apply -f k8s/
```

---

## 7.2 Create Resource Imperatively

Create Deployment:

```
kubectl create deployment nginx --image=nginx
```

Expose as service:

```
kubectl expose deployment nginx --port=80 --type=NodePort
```

Production environments prefer YAML-based approach.

---

# 8. Deleting Resources

Delete Pod:

```
kubectl delete pod <pod-name>
```

Delete Deployment:

```
kubectl delete deployment <deployment-name>
```

Delete using YAML:

```
kubectl delete -f deployment.yaml
```

---

# 9. Scaling Applications

Scale Deployment:

```
kubectl scale deployment spring-app --replicas=3
```

Verify:

```
kubectl get pods
```

This updates the ReplicaSet automatically.

---

# 10. Rolling Updates and Rollbacks

## 10.1 Update Image

```
kubectl set image deployment/spring-app spring-container=myapp:v2
```

Check rollout status:

```
kubectl rollout status deployment/spring-app
```

View rollout history:

```
kubectl rollout history deployment/spring-app
```

Rollback:

```
kubectl rollout undo deployment/spring-app
```

This is critical for zero-downtime deployments.

---

# 11. Executing Commands Inside Pod

Access container shell:

```
kubectl exec -it <pod-name> -- /bin/sh
```

If bash exists:

```
kubectl exec -it <pod-name> -- bash
```

Run single command:

```
kubectl exec <pod-name> -- ls /app
```

Useful for:

* Checking files
* Testing connectivity
* Debugging runtime issues

---

# 12. Port Forwarding

Access Pod locally:

```
kubectl port-forward pod/<pod-name> 8080:80
```

Access Deployment:

```
kubectl port-forward deployment/spring-app 8080:8080
```

Useful for:

* Local debugging
* Testing without exposing service

---

# 13. Working with Namespaces

List namespaces:

```
kubectl get ns
```

Create namespace:

```
kubectl create namespace dev
```

Run command in namespace:

```
kubectl get pods -n dev
```

Set default namespace (temporary):

```
kubectl config set-context --current --namespace=dev
```

---

# 14. Configuration and Context

View kubeconfig:

```
kubectl config view
```

Check current context:

```
kubectl config current-context
```

Switch context:

```
kubectl config use-context <context-name>
```

This is important when working with:

* Multiple clusters
* Dev/QA/Prod environments

---

# 15. Debugging Commands (Very Important)

Check events:

```
kubectl get events
```

Sort by time:

```
kubectl get events --sort-by=.metadata.creationTimestamp
```

Check resource usage:

```
kubectl top pods
kubectl top nodes
```

Requires metrics-server.

---

# 16. Output Formatting

Get YAML output:

```
kubectl get pod <pod-name> -o yaml
```

Get JSON:

```
kubectl get pod <pod-name> -o json
```

Custom columns:

```
kubectl get pods -o custom-columns=NAME:.metadata.name,IMAGE:.spec.containers[*].image
```

---

# 17. Label and Selector Commands

Add label:

```
kubectl label pod mypod env=dev
```

Select by label:

```
kubectl get pods -l env=dev
```

Remove label:

```
kubectl label pod mypod env-
```

Labels are the foundation of:

* Services
* Deployments
* ReplicaSets

---

# 18. Real-World Debugging Workflow

When Pod fails:

1. `kubectl get pods`
2. `kubectl describe pod <pod>`
3. `kubectl logs <pod>`
4. `kubectl get events`
5. `kubectl exec -it <pod> -- sh`

This sequence solves most production issues.

---

# 19. Most Important Commands (Quick Revision)

```
kubectl get pods
kubectl describe pod <name>
kubectl logs <name>
kubectl apply -f file.yaml
kubectl delete -f file.yaml
kubectl scale deployment <name> --replicas=3
kubectl rollout undo deployment <name>
kubectl exec -it <pod> -- sh
kubectl port-forward pod/<pod> 8080:80
```

---

# Final Understanding

kubectl is the operational interface to Kubernetes.

You use it to:

* Deploy
* Inspect
* Debug
* Scale
* Maintain
* Secure
