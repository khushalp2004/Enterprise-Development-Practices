package com.company.erp.accounting.controller;

import com.company.erp.accounting.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import com.company.erp.core.event.SaleCompletedEvent;

@RestController
@RequestMapping("/api/accounting")
public class AccountingController {

    @Autowired
    private AccountingService accountingService;

    @GetMapping("/revenue")
    public Map<String, Object> getRevenue() {
        return Map.of("totalRevenue", accountingService.getTotalRevenue());
    }

    @GetMapping("/ledger")
    public List<SaleCompletedEvent> getLedger() {
        return accountingService.getLedger();
    }
}
