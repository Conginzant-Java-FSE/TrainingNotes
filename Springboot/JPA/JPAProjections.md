# Spring Boot Data JPA Projections

## 1. What Are Projections in Spring Data JPA?

Projections allow you to **fetch only required columns** from the database instead of retrieving the entire entity.
They are used to optimize:

* Performance
* Memory usage
* Network payload size

Instead of returning entities, repositories return **custom views of data**.

---

## 2. Why Use Projections?

### Problems Without Projections

* Fetching unnecessary columns
* Increased memory usage
* Slower queries for large tables
* Over-fetching data in read-only APIs

### Benefits of Projections

* Reduced SQL result size
* Faster execution
* Clean API contracts
* Better separation of concerns

---

## 3. Types of Projections in Spring Data JPA

Spring Data JPA supports three main types:

1. Interface-based projections
2. Class-based (DTO) projections
3. Dynamic projections

---

## 4. Sample Domain Model

### Entity

```java
@Entity
@Table(name = "products")
public class Product {

    @Id
    private Long id;

    private String name;
    private String category;
    private Double price;
    private Integer stock;

    // getters and setters
}
```

---

## 5. Interface-Based Projections

### 5.1 Concept

Interface-based projections use **getter method names** to map selected columns.
Spring creates a **proxy implementation** at runtime.

---

### 5.2 Simple Interface Projection

```java
public interface ProductView {
    Long getId();
    String getName();
    Double getPrice();
}
```

---

### 5.3 Repository Method

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<ProductView> findByCategory(String category);
}
```

---

### 5.4 Generated SQL (Conceptual)

```sql
SELECT id, name, price
FROM products
WHERE category = ?
```

Only requested fields are fetched.

---

### 5.5 Open Projections (Using SpEL)

```java
public interface ProductSummary {

    String getName();

    @Value("#{target.price * 0.9}")
    Double getDiscountedPrice();
}
```

> Open projections may fetch the entire entity because SpEL requires full object access.

---

### 5.6 Limitations of Interface Projections

* Read-only
* No complex logic
* SpEL projections may reduce performance

---

## 6. Class-Based (DTO) Projections

### 6.1 Concept

Class-based projections map query results into **custom DTO objects** using constructors.

This approach is explicit and avoids proxies.

---

### 6.2 DTO Class

```java
public class ProductDto {

    private Long id;
    private String name;
    private Double price;

    public ProductDto(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // getters
}
```

---

### 6.3 Repository Using JPQL Constructor Expression

```java
@Query("""
       SELECT new com.example.dto.ProductDto(p.id, p.name, p.price)
       FROM Product p
       WHERE p.category = :category
       """)
List<ProductDto> findProductsByCategory(String category);
```

---

### 6.4 Advantages of Class-Based Projections

* Type-safe
* No proxy usage
* Ideal for complex mappings
* Clear contract for APIs

---

### 6.5 Limitations

* Requires JPQL
* Constructor signature must match query
* Slightly more boilerplate

---

## 7. Comparison: Interface vs Class-Based Projections

| Feature        | Interface Projection | Class-Based Projection |
| -------------- | -------------------- | ---------------------- |
| Implementation | Runtime proxy        | Explicit DTO           |
| Query Type     | Derived queries      | JPQL required          |
| Performance    | Very good            | Very good              |
| Logic Support  | Limited              | More flexible          |
| Read-only      | Yes                  | Yes                    |
| Maintenance    | Easier               | Moderate               |

---

## 8. Dynamic Projections

### 8.1 Concept

Dynamic projections allow you to **decide the projection type at runtime**.

This avoids creating multiple repository methods for different views.

---

### 8.2 Repository Method

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    <T> List<T> findByCategory(String category, Class<T> type);
}
```

---

### 8.3 Projection Interfaces

```java
public interface ProductNameView {
    String getName();
}

public interface ProductPriceView {
    String getName();
    Double getPrice();
}
```

---

### 8.4 Service Layer Usage

```java
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<ProductNameView> getProductNames(String category) {
        return repository.findByCategory(category, ProductNameView.class);
    }

    public List<ProductPriceView> getProductPrices(String category) {
        return repository.findByCategory(category, ProductPriceView.class);
    }
}
```

---

### 8.5 Advantages of Dynamic Projections

* Single repository method
* Flexible response shapes
* Cleaner repository interfaces
* Ideal for API versioning

---

## 9. Nested Projections

### Example: Category Entity

```java
@Entity
public class Category {
    @Id
    private Long id;
    private String name;
}
```

```java
@Entity
public class Product {
    @ManyToOne
    private Category category;
}
```

---

### Nested Projection Interface

```java
public interface ProductCategoryView {
    String getName();
    CategoryInfo getCategory();

    interface CategoryInfo {
        String getName();
    }
}
```

---

## 10. Projections vs Entity Graphs

| Aspect        | Projections      | Entity Graphs        |
| ------------- | ---------------- | -------------------- |
| Purpose       | Column reduction | Relationship loading |
| Data Returned | Partial          | Full entity          |
| Use Case      | Read APIs        | Fetch strategies     |

---

## 11. Common Mistakes

* Using projections for write operations
* Overusing open projections
* Ignoring null handling
* Mixing entity and projection returns in same API

---

## 12. Interview-Focused Key Points

* Projections are read-only
* Interface projections use proxies
* Constructor expressions are mandatory for DTO projections
* Dynamic projections are resolved at runtime
* Open projections may fetch full entities
* Projections reduce SQL payload, not object graph

---

## 13. Best Practices

* Use interface projections for simple views
* Use DTO projections for complex logic
* Use dynamic projections for API flexibility
* Avoid projections in transactional updates
* Keep projection interfaces minimal

---
