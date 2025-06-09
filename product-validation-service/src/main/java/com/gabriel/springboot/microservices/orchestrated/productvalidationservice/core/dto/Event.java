package com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.dto;

import com.gabriel.springboot.microservices.orchestrated.productvalidationservice.core.enums.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String id;
    private String transactionId;
    private String orderId;
    private String source;
    private SagaStatus status;
    private Order payload;
    private List<History> eventHistory = new ArrayList<>();

    public void addHistory(History history) {
        eventHistory.add(history);
    }
}
