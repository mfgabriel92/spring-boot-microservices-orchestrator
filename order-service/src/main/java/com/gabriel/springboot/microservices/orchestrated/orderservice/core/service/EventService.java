package com.gabriel.springboot.microservices.orchestrated.orderservice.core.service;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Event;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void notifyEnding(Event event) {
        event.setOrderId(event.getOrderId());
        event.setCreatedAt(LocalDateTime.now());
        save(event);

        log.info(
            "Notifying ending to order {} and transactionId {}",
            event.getOrderId(),
            event.getTransactionId()
        );
    }
}
