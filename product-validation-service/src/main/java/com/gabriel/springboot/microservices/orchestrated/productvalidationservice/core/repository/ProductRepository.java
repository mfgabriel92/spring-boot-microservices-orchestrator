package com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.repository;

import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByCode(String code);
}
