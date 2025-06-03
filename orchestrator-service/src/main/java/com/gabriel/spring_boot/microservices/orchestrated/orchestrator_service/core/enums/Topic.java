package com.gabriel.spring_boot.microservices.orchestrated.orchestrator_service.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Topic {
    START_SAGA("start-saga"),
    BASE_ORCHESTRATOR("orchestrator"),
    FINISH_SUCCESS("finish-success"),
    FINISH_FAILURE("finish-failure"),
    PRODUCT_VALIDATION_SUCCESS("product-validation-success"),
    PRODUCT_VALIDATION_FAILURE("product-validation-failure"),
    PAYMENT_SUCCESS("payment-success"),
    PAYMENT_FAILURE("payment-failure"),
    INVENTORY_SUCCESS("inventory-success"),
    INVENTORY_FAILURE("inventory-failure"),
    NOTIFY_ENDING("notify-ending");

    private String topic;
}
