package com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private String transactionId;
    private List<OrderProducts> products;
    private BigDecimal totalAmount;
    private byte totalItems;
    private LocalDateTime createdAt;
}
