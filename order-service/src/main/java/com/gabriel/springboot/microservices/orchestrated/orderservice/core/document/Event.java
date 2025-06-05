package com.gabriel.springboot.microservices.orchestrated.orderservice.core.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "events")
public class Event {
    @Id
    private String id;
    private String transactionId;
    private String orderId;
    private String source;
    private String status;
    private Order payload;
    private List<History> eventHistory;
}
