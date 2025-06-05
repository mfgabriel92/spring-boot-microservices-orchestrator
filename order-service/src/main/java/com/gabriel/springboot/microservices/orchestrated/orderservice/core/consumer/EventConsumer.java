package com.gabriel.springboot.microservices.orchestrated.orderservice.core.consumer;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.service.EventService;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class EventConsumer {
    private final EventService eventService;
    private final JsonUtil jsonUtil;

    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.notify-ending}"
    )
    public void consumeNotifyEndingEvent(String payload) {
        log.info("Consuming notify ending event with payload {}", payload);
        var event = jsonUtil.toEvent(payload);
        eventService.notifyEnding(event);
    }
}
