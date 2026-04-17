package com.axololt.assetmanagement.service;

import com.axololt.assetmanagement.dto.EmployeeRequest;
import com.axololt.assetmanagement.dto.LoginRequest;
import com.axololt.assetmanagement.entity.Department;
import com.axololt.assetmanagement.entity.Employee;
import com.axololt.assetmanagement.repository.DepartmentRepository;
import com.axololt.assetmanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private Department department;
    private EmployeeRequest employeeRequest;
    private final String accessKey = "testKey";

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .id(UUID.randomUUID())
                .name("IT")
                .build();

        employee = Employee.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john@example.com")
                .password("password")
                .department(department)
                .build();

        employeeRequest = new EmployeeRequest();
        employeeRequest.setName("John Doe");
        employeeRequest.setEmail("john@example.com");
        employeeRequest.setPassword("password");
        employeeRequest.setDepartmentId(department.getId());
    }

    @Test
    void login_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@example.com");
        loginRequest.setPassword("password");

        when(employeeRepository.findByEmail("john@example.com")).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> response = employeeService.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    void login_Failure() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@example.com");
        loginRequest.setPassword("wrong");

        when(employeeRepository.findByEmail("john@example.com")).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> response = employeeService.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void createEmployee_Success() {
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeService.createEmployee(employeeRequest, accessKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void getEmployees_WithFilters() {
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(employeeRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        ResponseEntity<List<Employee>> response = employeeService.getEmployees("John", null, department.getId(), 0, accessKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        verify(employeeRepository).findAll(any(Specification.class), eq(PageRequest.of(0, 25)));
    }

    @Test
    void updateEmployee_Success() {
        when(employeeRepository.findByEmail("john@example.com")).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        employeeRequest.setName("John Updated");
        ResponseEntity<Employee> response = employeeService.updateEmployee(employeeRequest, accessKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Updated", response.getBody().getName());
        verify(employeeRepository).save(employee);
    }
}
