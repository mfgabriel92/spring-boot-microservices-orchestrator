package com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.service;

import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.config.exception.ValidationException;
import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.dto.Event;
import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.dto.History;
import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.dto.OrderProducts;
import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.model.Validation;
import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.producer.KafkaProducer;
import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.repository.ProductRepository;
import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.repository.ValidationRepository;
import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.enums.SagaStatus.*;
import static java.time.LocalDateTime.now;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@AllArgsConstructor
public class ProductValidationService {
    private final static String CURRENT_SOURCE = "PRODUCT_VALIDATION_SERVICE";

    private final JsonUtil jsonUtil;
    private final KafkaProducer kafkaProducer;
    private final ProductRepository productRepository;
    private final ValidationRepository validationRepository;

    public void validateExistingProducts(Event event) {
        try {
            checkCurrentValidation(event);
            createValidation(event, true);
            handleSuccess(event);
        } catch (Exception ex) {
            log.error("Error validating existing products: ", ex);
            handleFailureCurrentNotExecuted(event, ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    public void rollbackEvent(Event event) {
        updateValidationToFailure(event);
        event.setStatus(FAILURE);
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Rollback executed on product validation");
        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    private void checkCurrentValidation(Event event) {
        checkProductsNotEmpty(event);
        checkValidationIsUnique(event);

        var products = event.getPayload().getProducts();

        products.forEach(product -> {
            checkProductIsPresentInRequest(product);
            checkExistingProduct(product.getProduct().getCode());
        });
    }

    private void checkExistingProduct(String code) {
        if (!productRepository.existsByCode(code)) {
            throw new ValidationException(String.format("Product %s does not exist!", code));
        }
    }

    private void checkProductIsPresentInRequest(OrderProducts product) {
        if (isEmpty(product.getProduct()) || isEmpty(product.getProduct().getCode())) {
            throw new ValidationException("A product must be informed!");
        }
    }

    private void checkValidationIsUnique(Event event) {
        var payload = event.getPayload();
        var orderId = payload.getId();
        var transactionId = payload.getTransactionId();

        if (validationRepository.existsByOrderIdAndTransactionId(orderId, transactionId)) {
            throw new ValidationException("Validation already exists for this order!");
        }
    }

    private void checkProductsNotEmpty(Event event) {
        var payload = event.getPayload();
        var products = payload.getProducts();

        if (isEmpty(payload) || isEmpty(products)) {
            throw new ValidationException("Event is empty!");
        }

        var payloadId = payload.getId();
        var transactionId = payload.getTransactionId();

        if (isEmpty(payloadId) || isEmpty(transactionId)) {
            throw new ValidationException("Either order ID or transaction ID must be provided");
        }
    }

    private void createValidation(Event event, boolean success) {
        var validation = Validation.builder()
            .orderId(event.getPayload().getId())
            .transactionId(event.getPayload().getTransactionId())
            .success(success)
            .build();
        validationRepository.save(validation);
    }

    private void handleSuccess(Event event) {
        event.setStatus(SUCCESS);
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Product validation was successful");
    }

    private void addHistory(Event event, String message) {
        var history = History.builder()
            .source(event.getSource())
            .status(event.getStatus())
            .message(message)
            .createdAt(now())
            .build();
        event.addHistory(history);
    }

    private void handleFailureCurrentNotExecuted(Event event, String message) {
        event.setStatus(ROLLBACK_PENDING);
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Failed to validate products: ".concat(message));
    }

    private void updateValidationToFailure(Event event) {
        var payload = event.getPayload();
        var payloadId = payload.getId();
        var transactionId = payload.getTransactionId();

        validationRepository.findByOrderIdAndTransactionId(payloadId, transactionId)
            .ifPresentOrElse(validation -> {
                validation.setSuccess(false);
                validationRepository.save(validation);
            }, () -> createValidation(event, false));
    }
}
