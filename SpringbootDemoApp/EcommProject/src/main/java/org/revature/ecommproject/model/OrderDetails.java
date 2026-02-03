package org.revature.ecommproject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "orderdetails")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_details_id;
    @ManyToOne
    @JoinColumn(name = "orderid")
    @JsonBackReference(value = "orders-ref")
    private Orders orders;
    @ManyToOne
    @JoinColumn(name = "id")
    @JsonBackReference(value = "product-ref")
    private Products products;

    private int quantity;
    private float discount;

    public OrderDetails(){}

    public OrderDetails(Long order_details_id, Orders orders, Products products, int quantity, float discount) {
        this.order_details_id = order_details_id;
        this.orders = orders;
        this.products = products;
        this.quantity = quantity;
        this.discount = discount;
    }


    public Long getOrder_details_id() {
        return order_details_id;
    }

    public void setOrder_details_id(Long order_details_id) {
        this.order_details_id = order_details_id;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
