package com.gabriel.springboot.microservices.orchestrated.orchestratorservice.core.dto;

import com.gabriel.springboot.microservices.orchestrated.orchestratorservice.core.enums.EventSource;
import com.gabriel.springboot.microservices.orchestrated.orchestratorservice.core.enums.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String id;
    private String transactionId;
    private String orderId;
    private EventSource source;
    private SagaStatus status;
    private Order payload;
    private List<History> eventHistory;
}
