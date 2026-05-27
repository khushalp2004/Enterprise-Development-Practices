package com.company.erp.accounting.service;

import com.company.erp.core.event.SaleCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountingService {

    private double totalRevenue = 0.0;
    private final List<SaleCompletedEvent> ledger = new ArrayList<>();

    @KafkaListener(topics = "sales-topic", groupId = "accounting-group")
    public void handleSaleCompleted(SaleCompletedEvent event) {
        System.out.println("Accounting received SaleCompletedEvent for Sale ID: " + event.getSaleId());
        totalRevenue += event.getTotalAmount();
        ledger.add(event);
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public List<SaleCompletedEvent> getLedger() {
        return ledger;
    }
}
