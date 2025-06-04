package com.gabriel.springboot.microservices.orchestrated.paymentservice.config.exception;

import lombok.Builder;

@Builder
public record ExceptionDetails(int status, String message) {
}
