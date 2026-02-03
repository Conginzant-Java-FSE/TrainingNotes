package org.revature.ecommproject.dto;

import org.revature.ecommproject.model.OrderDetails;

import java.util.List;

public class OrderRequestDTO {
    private String ship_addr;

    private List<OrderDetails> details;

    public OrderRequestDTO(){}

    public OrderRequestDTO(String ship_addr, List<OrderDetails> details) {
        this.ship_addr = ship_addr;
        this.details = details;
    }

    public String getShip_addr() {
        return ship_addr;
    }

    public void setShip_addr(String ship_addr) {
        this.ship_addr = ship_addr;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }
}
