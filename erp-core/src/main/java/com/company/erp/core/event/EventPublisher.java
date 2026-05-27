package com.company.erp.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSaleCompletedEvent(SaleCompletedEvent event) {
        kafkaTemplate.send("sales-topic", event);
        System.out.println("Published SaleCompletedEvent for Sale ID: " + event.getSaleId());
    }
}
