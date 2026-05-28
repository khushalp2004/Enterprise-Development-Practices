package com.company.erp.sales.service;

import com.company.erp.core.event.EventPublisher;
import com.company.erp.core.event.SaleCompletedEvent;
import com.company.erp.sales.model.Sale;
import com.company.erp.sales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class SalesService {

    @Autowired
    private EventPublisher eventPublisher;
    
    @Autowired
    private SaleRepository saleRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public Sale processSale(Long productId, int quantity) {
        String inventoryUrl = "http://localhost:8080/api/inventory/products/" + productId;
        ProductDto product = null;
        try {
            // Get current authorization token
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String authHeader = request.getHeader("Authorization");
            
            HttpHeaders headers = new HttpHeaders();
            if (authHeader != null) {
                headers.set("Authorization", authHeader);
            }
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<ProductDto> response = restTemplate.exchange(inventoryUrl, HttpMethod.GET, entity, ProductDto.class);
            product = response.getBody();
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch product details: " + e.getMessage());
        }

        double pricePerItem = product.getPrice();
        double totalAmount = quantity * pricePerItem;
        
        Sale sale = new Sale(productId, quantity, totalAmount, System.currentTimeMillis());
        sale = saleRepository.save(sale);
        
        SaleCompletedEvent event = new SaleCompletedEvent(sale.getId(), productId, quantity, totalAmount);
        eventPublisher.publishSaleCompletedEvent(event);
        
        return sale;
    }

    // Inner class for deserializing the product from inventory
    public static class ProductDto {
        private Long id;
        private double price;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
