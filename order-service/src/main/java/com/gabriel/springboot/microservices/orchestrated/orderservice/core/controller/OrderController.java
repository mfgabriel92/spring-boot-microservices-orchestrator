package com.gabriel.springboot.microservices.orchestrated.orderservice.core.controller;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Order;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.dto.OrderRequest;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }
}
