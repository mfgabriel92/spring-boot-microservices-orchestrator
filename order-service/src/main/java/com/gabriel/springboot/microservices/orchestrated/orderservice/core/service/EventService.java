package com.gabriel.springboot.microservices.orchestrated.orderservice.core.service;

import com.gabriel.springboot.microservices.orchestrated.orderservice.config.exception.ValidationException;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Event;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.dto.EventFilters;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAllByOrderByCreatedAtDesc();
    }

    public Event findByFilters(EventFilters filters) {
        if (isEmpty(filters.getOrderId()) && isEmpty(filters.getTransactionId())) {
            throw new ValidationException("Either order ID or transaction ID must be provided");
        }

        if (!isEmpty(filters.getOrderId())) {
            return findByOrderId(filters.getOrderId());
        } else {
            return findByTransactionId(filters.getTransactionId());
        }
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void notifyEnding(Event event) {
        event.setOrderId(event.getOrderId());
        event.setCreatedAt(now());
        save(event);

        log.info(
            "Notifying ending to order {} and transactionId {}",
            event.getOrderId(),
            event.getTransactionId()
        );
    }

    private Event findByOrderId(String orderId) {
        return eventRepository.findTop1ByOrderIdOrderByCreatedAtDesc(orderId)
            .orElseThrow(() -> new ValidationException("Order not found by order ID"));
    }

    private Event findByTransactionId(String transactionId) {
        return eventRepository.findTop1ByTransactionIdOrderByCreatedAtDesc(transactionId)
            .orElseThrow(() -> new ValidationException("Order not found by transaction ID"));
    }
}
