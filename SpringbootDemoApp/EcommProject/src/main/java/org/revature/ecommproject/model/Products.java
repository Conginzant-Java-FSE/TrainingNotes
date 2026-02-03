package org.revature.ecommproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//1
    private String name;
    private float price;
    private String color;
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "product-ref")
    private List<OrderDetails> orderDetailsList;

    public Products(){

    }

    public Products(Long id, String name, float price, String color, List<OrderDetails> orderDetailsList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.orderDetailsList = orderDetailsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
}
