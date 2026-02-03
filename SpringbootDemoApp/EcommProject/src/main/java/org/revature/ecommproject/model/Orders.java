package org.revature.ecommproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;
    private String ship_addr;
    private Date order_date;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "orders-ref")
    private List<OrderDetails> orderDetailsList;

    public Orders(Long orderid, String ship_addr, Date order_date, List<OrderDetails> orderDetailsList) {
        this.orderid = orderid;
        this.ship_addr = ship_addr;
        this.order_date = order_date;
        this.orderDetailsList = orderDetailsList;
    }

    public Orders(){}

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getShip_addr() {
        return ship_addr;
    }

    public void setShip_addr(String ship_addr) {
        this.ship_addr = ship_addr;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
}
