package com.company.erp.inventory.controller;

import com.company.erp.inventory.service.InventoryService;
import com.company.erp.inventory.dto.InventoryUpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/products")
    public List<com.company.erp.inventory.entity.Product> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    @GetMapping("/products/{productId}")
    public com.company.erp.inventory.entity.Product getProduct(@PathVariable Long productId) {
        return inventoryService.getProduct(productId);
    }

    @PostMapping("/products")
    public com.company.erp.inventory.entity.Product addProduct(@RequestBody com.company.erp.inventory.entity.Product product) {
        return inventoryService.addProduct(product);
    }

    @PutMapping("/products/{productId}/stock")
    public Map<String, Object> updateStock(@PathVariable Long productId, @RequestParam int quantity) throws ExecutionException, InterruptedException {
        InventoryUpdateResult result = inventoryService.updateInventory(productId, quantity).get();
        
        Map<String, Object> response = new HashMap<>();
        response.put("oldStock", result.getOldStock());
        response.put("newStock", result.getNewStock());
        return response;
    }

    @PostMapping("/products/{productId}/deduct")
    public Map<String, Object> deductStock(@PathVariable Long productId, @RequestParam int quantity) {
        try {
            inventoryService.deductStock(productId, quantity);
            return Map.of("status", "SUCCESS", "message", "Stock deducted successfully");
        } catch (IllegalArgumentException e) {
            // Spring by default returns 200 with this Map, but we should return a 400 Bad Request
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/products/{productId}/restore")
    public Map<String, Object> restoreStock(@PathVariable Long productId, @RequestParam int quantity) {
        inventoryService.restoreStock(productId, quantity);
        return Map.of("status", "SUCCESS", "message", "Stock restored successfully");
    }
    @PutMapping("/products/{productId}")
    public com.company.erp.inventory.entity.Product updateProduct(@PathVariable Long productId, @RequestBody com.company.erp.inventory.entity.Product product) {
        return inventoryService.updateProduct(productId, product);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        inventoryService.deleteProduct(productId);
    }
}
