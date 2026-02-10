# JWT Authentication in Spring Boot

---

## 1. What is JWT?

**JWT (JSON Web Token)** is a **stateless authentication mechanism** where:

* The server issues a signed token after successful login
* The client sends the token in every subsequent request
* The server validates the token without storing session state

### JWT Structure

```
HEADER.PAYLOAD.SIGNATURE
```

Example:

```text
eyJhbGciOiJIUzI1NiJ9.
eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcwNzAwMDAwMCwiZXhwIjoxNzA3MDAzNjAwfQ.
XYZ_SIGNATURE
```

---

## 2. Why JWT over Session-Based Auth?

| Session-Based         | JWT                      |
| --------------------- | ------------------------ |
| Stateful              | Stateless                |
| Server stores session | No server-side storage   |
| Not scalable          | Horizontally scalable    |
| CSRF issues           | CSRF-safe (with headers) |

---

## 3. High-Level Flow

1. User sends credentials to `/auth/login`
2. Server authenticates credentials
3. Server generates JWT and returns it
4. Client sends JWT in `Authorization` header
5. Spring Security filter validates JWT
6. Request is allowed or rejected

---

## 4. Dependencies

### Maven `pom.xml`

```xml
<dependencies>

    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JPA (optional, for DB-backed users) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <scope>runtime</scope>
    </dependency>

</dependencies>
```

---

## 5. User Entity

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;
}
```

---

## 6. UserDetailsService Implementation

Spring Security uses `UserDetailsService` to load users.

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
```

---

## 7. JWT Utility Class

### Responsibilities

* Generate token
* Validate token
* Extract username

```java
@Component
public class JwtUtil {

    private final String SECRET_KEY = "my-secret-key-123456";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        return extractClaims(token).getExpiration().after(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
```

---

## 8. JWT Authentication Filter

This filter runs **before** Spring Security authentication.

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null &&
                jwtUtil.validateToken(token)) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

---

## 9. Security Configuration (Spring Security 6)

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

---

## 10. Authentication Controller

### Login Request DTO

```java
public class AuthRequest {
    private String username;
    private String password;
}
```

### Login API

```java
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(token);
    }
}
```

---

## 11. Secured Controller Example

```java
@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "JWT secured endpoint";
    }
}
```

---

## 12. Testing with Postman

### Login

```
POST /auth/login
Body:
{
  "username": "user1",
  "password": "password"
}
```

### Secured API

```
GET /api/hello
Header:
Authorization: Bearer <JWT_TOKEN>
```

---

## 13. Common Mistakes

| Issue             | Cause                        |
| ----------------- | ---------------------------- |
| 403 Forbidden     | Missing Authorization header |
| 401 Unauthorized  | Token expired / invalid      |
| Filter not called | Filter order incorrect       |
| Password mismatch | Password not BCrypt-encoded  |

---

## 14. Best Practices

* Store secret key in **environment variables**
* Use **short-lived access tokens**
* Use **refresh tokens** for long sessions
* Add **role claims** if needed
* Always use **HTTPS**

---

## 15. Architecture Summary

```
Controller → Security Filter → JWT Validation → SecurityContext
```

---
