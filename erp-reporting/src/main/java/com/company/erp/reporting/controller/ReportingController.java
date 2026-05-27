package com.company.erp.reporting.controller;

import com.company.erp.hr.service.EmployeeService;
import com.company.erp.inventory.repository.ProductRepository;
import com.company.erp.accounting.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reporting")
public class ReportingController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private AccountingService accountingService;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        metrics.put("totalEmployees", employeeService.getAllEmployees().size());
        metrics.put("activeProducts", productRepository.count());
        metrics.put("totalRevenue", accountingService.getTotalRevenue());
        
        return metrics;
    }
}
