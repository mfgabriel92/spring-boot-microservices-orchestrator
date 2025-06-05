package com.gabriel.springboot.microservices.orchestrated.orderservice.core.service;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Event;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Order;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.dto.OrderRequest;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.producer.SagaProducer;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.repository.OrderRepository;
import com.gabriel.springboot.microservices.orchestrated.orderservice.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private static final String TRANSACTION_ID_PATTERN = "%s_%s";

    private final OrderRepository orderRepository;
    private final EventService eventService;
    private final SagaProducer sagaProducer;
    private final JsonUtil jsonUtil;

    public Order createOrder(OrderRequest request) {
        var transactionId = String.format(TRANSACTION_ID_PATTERN, Instant.now().toEpochMilli(), UUID.randomUUID());
        var order = Order.builder()
            .products(request.getProducts())
            .createdAt(LocalDateTime.now())
            .transactionId(transactionId)
            .build();

        orderRepository.save(order);

        var payload = createPayload(order);
        var payloadJson = jsonUtil.toJson(payload);
        sagaProducer.sendEvent(payloadJson);

        return order;
    }

    private Event createPayload(Order order) {
        var event = Event.builder()
            .orderId(order.getId())
            .transactionId(order.getTransactionId())
            .payload(order)
            .createdAt(LocalDateTime.now())
            .build();

        return eventService.save(event);
    }
}
