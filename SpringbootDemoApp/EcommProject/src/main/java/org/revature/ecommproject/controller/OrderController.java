package org.revature.ecommproject.controller;


import org.revature.ecommproject.dto.OrderRequestDTO;
import org.revature.ecommproject.model.Orders;
import org.revature.ecommproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService service;

    @Autowired
    public OrderController(OrderService service){
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Orders> addOrder(@RequestBody OrderRequestDTO order){
        return  new ResponseEntity<>(service.saveOrder(order), HttpStatus.CREATED);
    }

    @GetMapping ResponseEntity<List<Orders>> getAllOrders(){
        return new ResponseEntity<>(service.getAllOrders(),HttpStatus.OK);
    }
}
