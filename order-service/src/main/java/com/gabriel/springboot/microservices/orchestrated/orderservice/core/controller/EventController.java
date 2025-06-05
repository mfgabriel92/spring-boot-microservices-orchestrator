package com.gabriel.springboot.microservices.orchestrated.orderservice.core.controller;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Event;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.dto.EventFilters;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("all")
    public List<Event> findAll() {
        return eventService.findAll();
    }

    @GetMapping
    public Event findByFilters(@RequestBody EventFilters filters) {
        return eventService.findByFilters(filters);
    }
}
