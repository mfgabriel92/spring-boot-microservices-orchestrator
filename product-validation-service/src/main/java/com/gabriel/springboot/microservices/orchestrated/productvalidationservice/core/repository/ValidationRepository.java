package com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.repository;

import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, UUID> {
    Boolean existsByOrderIdAndTransactionId(String orderId, String transactionId);

    Optional<Validation> findByOrderIdAndTransactionId(String orderId, String transactionId);
}
