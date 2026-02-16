# Spring Boot Security 

# 1. Security in Microservices Architecture

![Image](https://learn.microsoft.com/en-us/dotnet/architecture/microservices/architect-microservice-container-applications/media/direct-client-to-microservice-communication-versus-the-api-gateway-pattern/api-gateway-azure-api-management.png)

![Image](https://www.xoriant.com/sites/default/files/uploads/2018/08/JWT-Authentication.png)

![Image](https://docs.spring.io/spring-security/reference/_images/servlet/architecture/multi-securityfilterchain.png)

![Image](https://miro.medium.com/1%2AoU_09lWZITVrBko_csgT6A.png)

In a microservices system:

```
Client → API Gateway → User Service → Other Services
```

Security must address:

* Authentication (Who are you?)
* Authorization (What are you allowed to access?)
* Token validation
* Role-based access control
* Secure inter-service communication

---

# 2. Authentication vs Authorization

| Concept        | Meaning                                             |
| -------------- | --------------------------------------------------- |
| Authentication | Verifying identity (username/password, OAuth2, JWT) |
| Authorization  | Checking permissions (ROLE_ADMIN, ROLE_USER)        |

---

# 3. Recommended Architecture for Your Setup

Since you already use:

* `spring-boot-starter-security`
* `spring-boot-starter-oauth2-client`
* `jjwt`
* API Gateway
* Eureka
* Config Server

We will use:

* JWT-based authentication
* Stateless security
* Role-based authorization
* Gateway-level token validation

---

# 4. JWT-Based Security Flow

```
1. Client → /login (User Service)
2. User Service validates credentials
3. User Service generates JWT
4. Client stores JWT
5. Client → API Gateway (Authorization: Bearer token)
6. Gateway validates JWT
7. Gateway forwards request to microservices
8. Services extract roles from token
```

---

# 5. JWT Structure

A JWT contains:

```
Header.Payload.Signature
```

Payload example:

```json
{
  "sub": "john",
  "roles": ["ROLE_USER"],
  "iat": 123456,
  "exp": 123999
}
```

---

# 6. User Service – Authentication Setup

You already have:

```
spring-boot-starter-security
jjwt
```

---

## Step 1 – Create User Entity

```java
@Entity
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String role;
}
```

---

## Step 2 – Create UserDetailsService

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
```

---

## Step 3 – Password Encoder

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

# 7. JWT Utility Class

```java
@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkey";

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
```

---

# 8. Authentication Controller

```java
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                new User(request.getUsername(), request.getPassword(), new ArrayList<>());

        return jwtUtil.generateToken(userDetails);
    }
}
```

---

# 9. Security Configuration (Spring Security 6 Style)

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
}
```

---

# 10. JWT Filter

```java
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(request, response);
    }
}
```

Register filter:

```java
http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
```

---

# 11. API Gateway Security

Gateway should:

* Validate JWT
* Forward authenticated request
* Block invalid tokens

Use:

**Spring Cloud Gateway**

Add dependency:

```xml
spring-boot-starter-oauth2-resource-server
```

application.yml:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          secret-key: mysecretkeymysecretkey
```

Now Gateway validates JWT before forwarding.

---

# 12. Role-Based Authorization Example

Controller:

```java
@GetMapping("/admin/data")
@PreAuthorize("hasRole('ADMIN')")
public String adminData() {
    return "Admin Content";
}
```

Enable method security:

```java
@EnableMethodSecurity
```

---

# 13. Complete Flow

```
Client → POST /auth/login → JWT
Client → Gateway (Bearer token)
Gateway validates
Gateway → User Service
User Service checks role
Response returned
```

---

# 14. Production Best Practices

1. Use asymmetric keys (RSA) instead of shared secret.
2. Store secret in Config Server.
3. Use refresh tokens.
4. Secure Gateway endpoints.
5. Enable CORS properly.
6. Use OAuth2 Authorization Server for enterprise systems.
7. Avoid exposing internal services directly.

---

# 15. Common Interview Questions

1. Difference between OAuth2 and JWT?
2. Why stateless security in microservices?
3. What is SecurityFilterChain?
4. How does JWT validation work?
5. Why disable CSRF in REST APIs?
6. What is Resource Server?
7. Where should token validation happen?
8. How does role-based authorization work?

---

# 16. Summary

In Spring Boot microservices:

Authentication:

* Validate credentials
* Generate JWT

Authorization:

* Role-based checks
* Method-level security

Gateway:

* Validates token
* Forwards authenticated requests

Security becomes:

* Stateless
* Scalable
* Centralized
* Production-ready

---
