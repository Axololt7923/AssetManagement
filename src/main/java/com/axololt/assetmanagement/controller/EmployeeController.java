package com.axololt.assetmanagement.controller;

import com.axololt.assetmanagement.dto.AccessRequest;
import com.axololt.assetmanagement.dto.EmployeeRequest;
import com.axololt.assetmanagement.dto.LoginRequest;
import com.axololt.assetmanagement.entity.Employee;
import com.axololt.assetmanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<Employee> login(
            @RequestBody LoginRequest requestBody) {
        return employeeService.login(requestBody);
    }

    @PostMapping("/add-employee")
    public ResponseEntity<Employee> add(
            @RequestHeader(name = "access-key", required = false) String accessKey,
            @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.createEmployee(employeeRequest, accessKey);
    }

    @PatchMapping("/update-employee")
    public ResponseEntity<Employee> update(
            @RequestHeader(name = "access-key", required = false) String accessKey,
            @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployee(employeeRequest, accessKey);
    }
}
