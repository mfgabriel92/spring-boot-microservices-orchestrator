package com.gabriel.springboot.microservices.orchestrated.inventoryservice.core.enums;

public enum SagaStatus {
    SUCCESS,
    ROLLBACK_PENDING,
    FAILURE
}
