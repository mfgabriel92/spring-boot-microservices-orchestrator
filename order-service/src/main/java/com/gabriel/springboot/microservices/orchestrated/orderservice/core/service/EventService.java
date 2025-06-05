package com.gabriel.springboot.microservices.orchestrated.orderservice.core.service;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Event;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event save(Event event) {
        return eventRepository.save(event);
    }
}
