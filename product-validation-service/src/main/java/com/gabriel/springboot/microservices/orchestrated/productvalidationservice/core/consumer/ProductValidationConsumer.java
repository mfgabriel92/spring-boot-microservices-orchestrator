package com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.consumer;

import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductValidationConsumer {
    private final JsonUtil jsonUtil;

    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.product-validation-success}"
    )
    public void consumeSuccessEvent(String payload) {
        log.info("Consuming success from product-validation-success event with payload {}", payload);
        var event = jsonUtil.toEvent(payload);
        log.info(event.toString());
    }

    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.product-validation-failure}"
    )
    public void consumeFailureEvent(String payload) {
        log.info("Consuming rollback event from product-validation-failure with payload {}", payload);
        var event = jsonUtil.toEvent(payload);
        log.info(event.toString());
    }
}
