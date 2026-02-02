# Spring Framework MVC 
---

## 1. Architecture Overview

```
Browser
  ↓
DispatcherServlet
  ↓
Controller
  ↓
Service
  ↓
DAO (Hibernate)
  ↓
Database
```

**Technologies used**

* Spring Framework (MVC + Core)
* Hibernate (ORM)
* JSP (View)
* MySQL (DB)
* Apache Tomcat (External Server)

---

## 2. Project Structure (Standard Enterprise Layout)

```
spring-mvc-hibernate/
 ├── src/main/java
 │    └── com.example
 │         ├── config
 │         ├── controller
 │         ├── service
 │         ├── dao
 │         └── model
 ├── src/main/resources
 │    └── hibernate.cfg.xml
 ├── src/main/webapp
 │    ├── WEB-INF
 │    │     ├── views
 │    │     │     └── users.jsp
 │    │     └── web.xml
 │    └── index.jsp
 └── pom.xml
```

---

## 3. Maven Dependencies (`pom.xml`)

```xml
<packaging>war</packaging>

<dependencies>

    <!-- Spring MVC -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.30</version>
    </dependency>

    <!-- Spring ORM -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-orm</artifactId>
        <version>5.3.30</version>
    </dependency>

    <!-- Hibernate -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.6.15.Final</version>
    </dependency>

    <!-- MySQL -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>

    <!-- Servlet API -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <scope>provided</scope>
    </dependency>

    <!-- JSP -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>

</dependencies>
```

---

## 4. `web.xml` (DispatcherServlet Setup)

```xml
<web-app>

    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>
            org.springframework.web.servlet.DispatcherServlet
        </servlet-class>

        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring-mvc-config.xml</param-value>
        </init-param>

        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

---

## 5. Spring MVC Configuration (`spring-mvc-config.xml`)

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx">

    <context:component-scan base-package="com.example"/>

    <mvc:annotation-driven/>

    <!-- View Resolver -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```

---

## 6. Hibernate Configuration (`hibernate.cfg.xml`)

```xml
<hibernate-configuration>
 <session-factory>

    <property name="hibernate.dialect">
        org.hibernate.dialect.MySQL8Dialect
    </property>

    <property name="hibernate.connection.driver_class">
        com.mysql.cj.jdbc.Driver
    </property>

    <property name="hibernate.connection.url">
        jdbc:mysql://localhost:3306/testdb
    </property>

    <property name="hibernate.connection.username">root</property>
    <property name="hibernate.connection.password">root</property>

    <property name="hibernate.show_sql">true</property>
    <property name="hibernate.hbm2ddl.auto">update</property>

 </session-factory>
</hibernate-configuration>
```

---

## 7. Hibernate Utility Class

```java
public class HibernateUtil {

    private static SessionFactory factory;

    static {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
```

---

## 8. Entity Class

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;

    // getters & setters
}
```

---

## 9. DAO Layer

```java
@Repository
public class UserDao {

    public List<User> getAllUsers() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = session.createQuery("from User", User.class).list();
        session.close();

        return users;
    }
}
```

---

## 10. Service Layer

```java
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> fetchUsers() {
        return userDao.getAllUsers();
    }
}
```

---

## 11. Controller Layer

```java
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.fetchUsers());
        return "users";
    }
}
```

---

## 12. JSP View (`users.jsp`)

```jsp
<table border="1">
<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Email</th>
</tr>

<c:forEach items="${users}" var="u">
<tr>
    <td>${u.id}</td>
    <td>${u.name}</td>
    <td>${u.email}</td>
</tr>
</c:forEach>

</table>
```

---

## 13. Build WAR File

```bash
mvn clean package
```

Produces:

```
target/spring-mvc-hibernate.war
```

---

## 14. Deploy on Apache Tomcat

### Steps

1. Download Tomcat
2. Copy WAR to:

   ```
   tomcat/webapps/
   ```
3. Start server:

   ```
   bin/startup.sh (Linux)
   bin/startup.bat (Windows)
   ```
4. Access:

   ```
   http://localhost:8080/spring-mvc-hibernate/users
   ```

---

## 15. Common Issues & Fixes

| Issue                       | Fix                     |
| --------------------------- | ----------------------- |
| 404 error                   | Check servlet mapping   |
| NoSuchBeanDefinition        | Component scan missing  |
| LazyInitializationException | Use OpenSessionInView   |
| JSP not loading             | ViewResolver path wrong |

---

## 16. Summary

* Spring MVC follows Front Controller pattern
* DispatcherServlet handles requests
* Hibernate manages ORM
* WAR deployed to external Tomcat
* Clean separation of Controller → Service → DAO

---
