package com.company.erp.sales.service;

import com.company.erp.core.event.EventPublisher;
import com.company.erp.core.event.SaleCompletedEvent;
import com.company.erp.sales.model.Sale;
import com.company.erp.sales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalesService {

    @Autowired
    private EventPublisher eventPublisher;
    
    @Autowired
    private SaleRepository saleRepository;

    @Transactional
    public String processSale(Long productId, int quantity, double pricePerItem) {
        double totalAmount = quantity * pricePerItem;
        
        Sale sale = new Sale(productId, quantity, totalAmount, System.currentTimeMillis());
        sale = saleRepository.save(sale);
        
        SaleCompletedEvent event = new SaleCompletedEvent(sale.getId(), productId, quantity, totalAmount);
        eventPublisher.publishSaleCompletedEvent(event);
        
        return "Sale processed successfully. Total: $" + totalAmount;
    }
}
