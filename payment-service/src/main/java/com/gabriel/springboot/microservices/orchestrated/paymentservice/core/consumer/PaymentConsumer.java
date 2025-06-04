package com.gabriel.springboot.microservices.orchestrated.paymentservice.core.consumer;

import com.gabriel.springboot.microservices.orchestrated.paymentservice.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentConsumer {
    private final JsonUtil jsonUtil;

    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.payment-success}"
    )
    public void consumeSuccessEvent(String payload) {
        log.info("Consuming success from payment-success event with payload {}", payload);
        var event = jsonUtil.toEvent(payload);
        log.info(event.toString());
    }

    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.payment-failure}"
    )
    public void consumeFailureEvent(String payload) {
        log.info("Consuming rollback event from payment-failure with payload {}", payload);
        var event = jsonUtil.toEvent(payload);
        log.info(event.toString());
    }
}
