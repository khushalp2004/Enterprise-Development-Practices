package com.company.erp.inventory.controller;

import com.company.erp.inventory.service.InventoryService;
import com.company.erp.inventory.dto.InventoryUpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/products/{productId}/stock")
    public Map<String, Object> updateStock(@PathVariable Long productId, @RequestParam int quantity) throws ExecutionException, InterruptedException {
        InventoryUpdateResult result = inventoryService.updateInventory(productId, quantity).get();
        
        Map<String, Object> response = new HashMap<>();
        response.put("oldStock", result.getOldStock());
        response.put("newStock", result.getNewStock());
        return response;
    }
}
