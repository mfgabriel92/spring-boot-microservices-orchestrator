package com.gabriel.springboot.microservices.orchestrated.orderservice.core.repository;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
