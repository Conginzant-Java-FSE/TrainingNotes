package org.revature.ecommproject.service;

import org.revature.ecommproject.dto.OrderRequestDTO;
import org.revature.ecommproject.model.Orders;
import org.revature.ecommproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository repo;

    @Autowired
    public OrderService(OrderRepository repo){
        this.repo = repo;
    }


    public Orders saveOrder(OrderRequestDTO o){
        Orders orders = new Orders();
        orders.setOrder_date(new Date());
        orders.setShip_addr(o.getShip_addr());
        orders.setOrderDetailsList(o.getDetails());
        return repo.save(orders);
    }

    public List<Orders> getAllOrders(){
        return repo.findAll();
    }

}
