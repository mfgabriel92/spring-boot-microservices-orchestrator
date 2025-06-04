package com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.enums;

public enum SagaStatus {
    SUCCESS,
    ROLLBACK_PENDING,
    FAILURE
}
