package com.company.erp.inventory.service;
import com.company.erp.core.exception.ResourceNotFoundException;
import com.company.erp.inventory.dto.InventoryUpdateResult;
import com.company.erp.inventory.entity.Product;
import com.company.erp.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.annotation.KafkaListener;
import com.company.erp.core.event.SaleCompletedEvent;
import java.util.concurrent.CompletableFuture;
@Service
@Transactional
public class InventoryService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Async
    public CompletableFuture<InventoryUpdateResult> updateInventory(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        int currentStock = product.getStockQuantity();
        int updatedStock = currentStock + quantity;
        if (updatedStock < 0) throw new RuntimeException("Insufficient stock available");
        
        product.setStockQuantity(updatedStock);
        product.setLastUpdated(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        
        redisTemplate.opsForValue().set("inventory:" + productId, savedProduct.getStockQuantity(), Duration.ofMinutes(30));
        return CompletableFuture.completedFuture(new InventoryUpdateResult(savedProduct.getId(), currentStock, updatedStock, "SUCCESS"));
    }
    
    @Scheduled(cron = "0 0 2 * * ?")
    public void generateLowStockReport() {
        List<Product> lowStockProducts = productRepository.findLowStockProducts(10);
        if (!lowStockProducts.isEmpty()) {
            System.out.println("Low Stock Alert Generated for " + lowStockProducts.size() + " products.");
        }
    }

    @KafkaListener(topics = "sales-topic", groupId = "inventory-group")
    public void handleSaleCompleted(SaleCompletedEvent event) {
        System.out.println("Inventory received SaleCompletedEvent for Product ID: " + event.getProductId());
        try {
            updateInventory(event.getProductId(), -event.getQuantitySold());
        } catch (Exception e) {
            System.err.println("Failed to update inventory for sale: " + e.getMessage());
        }
    }
}
