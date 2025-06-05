package com.gabriel.springboot.microservices.orchestrated.orderservice.core.dto;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.OrderProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<OrderProducts> products;
}
