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
    public Map<String, Object> checkout(@RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        int quantity = Integer.parseInt(request.get("quantity").toString());
        
        com.company.erp.sales.model.Sale sale = salesService.processSale(productId, quantity);
        return Map.of("status", "SUCCESS", "sale", sale, "message", "Sale processed successfully");
    }
}
