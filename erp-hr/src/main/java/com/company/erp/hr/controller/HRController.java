package com.company.erp.hr.controller;

import com.company.erp.core.audit.AuditLog;
import com.company.erp.core.audit.AuditService;
import com.company.erp.hr.dto.CreateEmployeeRequest;
import com.company.erp.hr.dto.EmployeeDTO;
import com.company.erp.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hr")
// @PreAuthorize("hasRole('HR_ADMIN') or hasRole('MANAGER')") // Disabled for UI testing
public class HRController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuditService auditService;
    
    @GetMapping("/employees")
    public List<EmployeeDTO> getEmployees() {
        return employeeService.getAllEmployees();
    }
    
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    @AuditLog(action = "CREATE_EMPLOYEE")
    public EmployeeDTO createEmployee(@RequestBody CreateEmployeeRequest request) {
        EmployeeDTO employee = employeeService.createEmployee(request);
        auditService.logAudit("CREATE_EMPLOYEE", "Created employee: " + employee.getFirstName() + " " + employee.getLastName(), employee.getId());
        return employee;
    }
    @PutMapping("/employees/{id}")
    @AuditLog(action = "UPDATE_EMPLOYEE")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody CreateEmployeeRequest request) {
        EmployeeDTO employee = employeeService.updateEmployee(id, request);
        auditService.logAudit("UPDATE_EMPLOYEE", "Updated employee: " + employee.getFirstName() + " " + employee.getLastName(), employee.getId());
        return employee;
    }

    @DeleteMapping("/employees/{id}")
    @AuditLog(action = "DELETE_EMPLOYEE")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        auditService.logAudit("DELETE_EMPLOYEE", "Deleted employee ID: " + id, id);
    }
}
