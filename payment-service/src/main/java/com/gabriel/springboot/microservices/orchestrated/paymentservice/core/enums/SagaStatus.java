package com.gabriel.springboot.microservices.orchestrated.paymentservice.core.enums;

public enum SagaStatus {
    SUCCESS,
    ROLLBACK_PENDING,
    FAILURE
}
