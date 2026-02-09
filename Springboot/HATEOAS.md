# Spring Boot HATEOAS

## 1. What is HATEOAS?

**HATEOAS (Hypermedia As The Engine Of Application State)** is a constraint of REST that allows clients to dynamically navigate an API using **hypermedia links** provided in the response.

Instead of hard-coding URLs, the client discovers available actions through links.

### REST without HATEOAS

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 75000
}
```

### REST with HATEOAS

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 75000,
  "_links": {
    "self": { "href": "/products/1" },
    "update": { "href": "/products/1" },
    "delete": { "href": "/products/1" },
    "all-products": { "href": "/products" }
  }
}
```

---

## 2. Why HATEOAS?

### Benefits

* Loose coupling between client and server
* API evolution without breaking clients
* Self-descriptive responses
* Improves API discoverability
* Aligns with true REST maturity (Richardson Level 3)

### When to Use

* Public APIs
* APIs with frequent evolution
* Enterprise APIs with multiple clients
* APIs requiring discoverability

---

## 3. Spring Boot HATEOAS Support

Spring provides **spring-boot-starter-hateoas** which offers:

* `EntityModel`
* `CollectionModel`
* `RepresentationModel`
* `linkTo()` and `methodOn()` utilities

---

## 4. Dependencies

### Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

---

## 5. Sample Use Case

**Product Management API**

* Create product
* Get product by ID
* Get all products
* Update product
* Delete product

---

## 6. Entity

```java
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Double price;

    // getters and setters
}
```

---

## 7. Repository

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

---

## 8. DTO (Recommended)

```java
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
}
```

---

## 9. Why Not Return Entity Directly?

* Prevents exposing internal fields
* Allows independent API evolution
* Works cleanly with HATEOAS links
* Avoids lazy loading issues

---

## 10. Service Layer

```java
@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getProduct(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}
```

---

## 11. Basic HATEOAS Response using EntityModel

### Controller

```java
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public EntityModel<ProductDto> getProduct(@PathVariable Long id) {

        Product product = service.getProduct(id);

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());

        EntityModel<ProductDto> model = EntityModel.of(dto);

        model.add(
            linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel(),
            linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products")
        );

        return model;
    }

    @GetMapping
    public CollectionModel<EntityModel<ProductDto>> getAllProducts() {

        List<EntityModel<ProductDto>> products = service.getAllProducts()
                .stream()
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    dto.setPrice(product.getPrice());

                    return EntityModel.of(
                            dto,
                            linkTo(methodOn(ProductController.class)
                                    .getProduct(product.getId())).withSelfRel()
                    );
                })
                .toList();

        return CollectionModel.of(
                products,
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel()
        );
    }
}
```

---

## 12. Sample Response – Single Resource

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 75000,
  "_links": {
    "self": {
      "href": "http://localhost:8080/products/1"
    },
    "all-products": {
      "href": "http://localhost:8080/products"
    }
  }
}
```

---

## 13. Collection Response

```json
{
  "_embedded": {
    "productDtoList": [
      {
        "id": 1,
        "name": "Laptop",
        "price": 75000,
        "_links": {
          "self": {
            "href": "http://localhost:8080/products/1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/products"
    }
  }
}
```

---

## 14. Adding Action-Based Links

```java
model.add(
    linkTo(methodOn(ProductController.class).updateProduct(id, null))
        .withRel("update"),
    linkTo(methodOn(ProductController.class).deleteProduct(id))
        .withRel("delete")
);
```

This tells the client:

* Product can be updated
* Product can be deleted

---

## 15. RepresentationModel for Custom Responses

```java
public class ProductResponse extends RepresentationModel<ProductResponse> {

    private Long id;
    private String name;
    private Double price;
}
```

Used when:

* You want link support directly inside DTO
* You want cleaner controller logic

---

## 16. Best Practices

1. Use DTOs, not Entities
2. Keep link relation names meaningful
3. Avoid overloading responses with unnecessary links
4. Use HATEOAS mainly for navigational APIs
5. Prefer `methodOn()` over hardcoded URLs

---

## 17. Common Mistakes

* Hardcoding URLs
* Adding HATEOAS for internal-only APIs without need
* Mixing business logic with link building
* Exposing entity relationships directly

---

## 18. Interview Questions

**Q: What problem does HATEOAS solve?**
A: Tight coupling between client and server by enabling dynamic navigation.

**Q: Is HATEOAS mandatory for REST?**
A: No, but it is required for full REST maturity.

**Q: Difference between EntityModel and CollectionModel?**
A: EntityModel represents a single resource, CollectionModel represents a collection.

**Q: Can HATEOAS be used with DTOs?**
A: Yes, and it is the recommended approach.

---

## 19. When NOT to Use HATEOAS

* Internal microservice-to-microservice communication
* Simple CRUD APIs with stable contracts
* Performance-critical low-latency systems

---

## 20. Summary

* HATEOAS enhances REST APIs with discoverability
* Spring Boot provides excellent built-in support
* Use DTOs and structured link building
* Apply selectively based on API consumers

