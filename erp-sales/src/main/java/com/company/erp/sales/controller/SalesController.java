package com.company.erp.sales.controller;

import com.company.erp.sales.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping("/checkout")
    public Map<String, String> checkout(@RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        int quantity = Integer.parseInt(request.get("quantity").toString());
        double price = Double.parseDouble(request.get("price").toString());
        
        String result = salesService.processSale(productId, quantity, price);
        return Map.of("status", "SUCCESS", "message", result);
    }
}
