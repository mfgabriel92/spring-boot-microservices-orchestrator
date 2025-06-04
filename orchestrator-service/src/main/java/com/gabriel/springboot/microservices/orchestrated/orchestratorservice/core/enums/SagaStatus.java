package com.gabriel.springboot.microservices.orchestrated.orchestratorservice.core.enums;

public enum SagaStatus {
    SUCCESS,
    ROLLBACK_PENDING,
    FAILURE
}
