package com.company.erp.hr.service;

import com.company.erp.hr.dto.CreateEmployeeRequest;
import com.company.erp.hr.dto.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final List<EmployeeDTO> employees = new ArrayList<>();
    private long idCounter = 1;

    public EmployeeDTO createEmployee(CreateEmployeeRequest request) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(idCounter++);
        dto.setFirstName(request.getFirstName());
        dto.setLastName(request.getLastName());
        dto.setDepartment(request.getDepartment());
        employees.add(dto);
        return dto;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employees;
    }

    public Object generateReport(String reportType, Object startDate, Object endDate) {
        return new Object();
    }
}
