# Spring Security Basics

---

## 1. What is Spring Security?

**Spring Security** is a powerful and customizable **authentication and authorization framework** for Java applications, especially Spring-based applications.

It provides:

* Authentication (Who are you?)
* Authorization (What are you allowed to do?)
* Protection against common security attacks
* Integration with various authentication mechanisms

Spring Security is **filter-based** and works at the **web request level**.

---

## 2. Core Security Concepts

### 2.1 Authentication

**Authentication** is the process of verifying the identity of a user.

Examples:

* Username and password
* JWT token
* OAuth2 access token
* LDAP credentials

Spring Security answers:

> *Is this user really who they claim to be?*

---

### 2.2 Authorization

**Authorization** determines what an authenticated user is allowed to access.

Examples:

* Role-based access (`ROLE_ADMIN`)
* Permission-based access (`READ_USERS`)
* Method-level restrictions

Spring Security answers:

> *Is this user allowed to access this resource?*

---

### 2.3 Principal

A **Principal** represents the currently authenticated user.

* Usually a username
* Accessible via `SecurityContext`

---

### 2.4 Granted Authority

A **GrantedAuthority** represents a permission or role.

Examples:

* `ROLE_USER`
* `ROLE_ADMIN`
* `READ_PRIVILEGES`

Spring Security internally checks authorities during authorization.

---

## 3. High-Level Architecture

Spring Security works using a **chain of filters**.

```
Client Request
   ↓
Security Filter Chain
   ↓
Authentication
   ↓
Authorization
   ↓
Controller
```

Every HTTP request passes through the **Security Filter Chain** before reaching your application.

---

## 4. Security Filter Chain

### What is a Filter?

A **Filter** is a component that intercepts HTTP requests and responses.

Spring Security registers **multiple filters**, each responsible for a specific task:

* Authentication
* Session handling
* Exception handling
* Authorization checks

### Filter Chain Behavior

* Filters are executed **in order**
* Each filter can:

  * Allow the request
  * Modify the request
  * Reject the request

---

## 5. SecurityContext and SecurityContextHolder

### SecurityContext

The **SecurityContext** holds security-related information for the current request.

It contains:

* Authentication object
* Principal
* Authorities

### SecurityContextHolder

The **SecurityContextHolder** stores the `SecurityContext`:

* Thread-local by default
* Accessible anywhere in the application

---

## 6. Authentication Object

The **Authentication** interface represents the authentication state.

It contains:

* Principal (user details)
* Credentials (password or token)
* Authorities
* Authentication status

Two states:

* **Unauthenticated** – before login
* **Authenticated** – after successful authentication

---

## 7. AuthenticationManager

### Purpose

`AuthenticationManager` is responsible for authenticating users.

It:

* Accepts an `Authentication` request
* Delegates authentication to providers
* Returns an authenticated `Authentication` object

Think of it as the **entry point to authentication logic**.

---

## 8. AuthenticationProvider

### Role

`AuthenticationProvider` performs the **actual authentication**.

Examples:

* Username/password authentication
* JWT validation
* LDAP authentication

Responsibilities:

* Validate credentials
* Load user details
* Throw authentication exceptions if needed

---

## 9. UserDetails and UserDetailsService

### UserDetails

Represents user information required by Spring Security.

Contains:

* Username
* Password
* Authorities
* Account status flags

### UserDetailsService

Responsible for loading user data from a source:

* Database
* In-memory store
* LDAP

Spring Security uses it during authentication.

---

## 10. Password Encoding

Spring Security **never stores plain-text passwords**.

### PasswordEncoder

Used to:

* Encode passwords before storing
* Match raw password with encoded password

Common encoders:

* BCrypt (recommended)
* Argon2
* PBKDF2

---

## 11. Authorization Mechanisms

### 11.1 URL-Based Authorization

Controls access based on request paths.

Example:

* `/admin/**` → Admin only
* `/api/**` → Authenticated users

---

### 11.2 Method-Level Authorization

Controls access at the method level.

Examples:

* `@PreAuthorize`
* `@PostAuthorize`
* `@Secured`

Allows fine-grained access control.

---

## 12. Roles vs Authorities

| Roles                 | Authorities              |
| --------------------- | ------------------------ |
| High-level grouping   | Fine-grained permissions |
| Prefixed with `ROLE_` | No mandatory prefix      |
| Easier to manage      | More flexible            |

Internally, roles are treated as authorities.

---

## 13. CSRF Protection

### What is CSRF?

**Cross-Site Request Forgery** is an attack where a malicious site tricks a user into performing actions unknowingly.

### Spring Security CSRF Protection

* Enabled by default for web apps
* Uses CSRF tokens
* Commonly disabled for stateless APIs (JWT)

---

## 14. Session Management

Spring Security supports different session strategies:

* Stateful (HTTP Session)
* Stateless (JWT)
* Concurrent session control
* Session fixation protection

Session policy defines how authentication state is maintained.

---

## 15. Exception Handling

Spring Security provides built-in handling for:

* Authentication failures (401)
* Authorization failures (403)

Handled by:

* AuthenticationEntryPoint
* AccessDeniedHandler

---

## 16. Common Authentication Mechanisms

Spring Security supports:

* Form-based login
* HTTP Basic authentication
* JWT authentication
* OAuth2 / OpenID Connect
* LDAP authentication

---

## 17. Default Behavior (Out of the Box)

When Spring Security is added:

* All endpoints are secured
* Default login form is enabled
* CSRF protection is active
* One default user is created

---

## 18. Typical Security Flow

```
Request →
Filter Chain →
Authentication →
SecurityContext →
Authorization →
Controller →
Response
```

---

## 19. Common Misconceptions

* Spring Security is not just about login forms
* Roles are not stored separately from authorities
* Security is enforced before controllers
* Filters are not interceptors

---

## 20. Interview-Focused Summary

* Spring Security is **filter-based**
* Authentication and authorization are separate
* SecurityContext holds authentication data
* Filters enforce security before controllers
* Stateless security uses tokens instead of sessions
