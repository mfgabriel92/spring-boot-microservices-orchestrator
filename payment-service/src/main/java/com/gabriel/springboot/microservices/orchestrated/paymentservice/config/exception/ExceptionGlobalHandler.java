package com.gabriel.springboot.microservices.orchestrated.paymentservice.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ExceptionGlobalHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        var details = ExceptionDetails.builder()
            .status(BAD_REQUEST.value())
            .message(ex.getMessage())
            .build();

        return new ResponseEntity<>(details, BAD_REQUEST);
    }
}
