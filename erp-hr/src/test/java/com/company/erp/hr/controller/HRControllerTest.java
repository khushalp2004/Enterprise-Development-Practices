package com.company.erp.hr.controller;

import com.company.erp.core.audit.AuditService;
import com.company.erp.hr.dto.CreateEmployeeRequest;
import com.company.erp.hr.dto.EmployeeDTO;
import com.company.erp.hr.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HRControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private HRController hrController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(hrController).build();
    }

    @Test
    public void testGetEmployees() throws Exception {
        EmployeeDTO emp = new EmployeeDTO();
        emp.setId(1L);
        emp.setFirstName("John");
        emp.setLastName("Doe");
        emp.setDepartment("Engineering");

        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp));

        mockMvc.perform(get("/api/hr/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].department").value("Engineering"));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("Jane");
        request.setLastName("Smith");
        request.setDepartment("Marketing");

        EmployeeDTO createdEmp = new EmployeeDTO();
        createdEmp.setId(2L);
        createdEmp.setFirstName("Jane");
        createdEmp.setLastName("Smith");
        createdEmp.setDepartment("Marketing");

        when(employeeService.createEmployee(any())).thenReturn(createdEmp);

        mockMvc.perform(post("/api/hr/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Jane"));

        verify(employeeService, times(1)).createEmployee(any());
        verify(auditService, times(1)).logAudit(eq("CREATE_EMPLOYEE"), anyString(), eq(2L));
    }
}
