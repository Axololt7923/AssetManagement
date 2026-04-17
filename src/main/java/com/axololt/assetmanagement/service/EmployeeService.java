package com.axololt.assetmanagement.service;

import com.axololt.assetmanagement.dto.EmployeeRequest;
import com.axololt.assetmanagement.dto.LoginRequest;
import com.axololt.assetmanagement.entity.Department;
import com.axololt.assetmanagement.entity.Employee;
import com.axololt.assetmanagement.repository.DepartmentRepository;
import com.axololt.assetmanagement.repository.EmployeeRepository;
import static com.axololt.assetmanagement.utils.PermitCheck.isPermit;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public ResponseEntity<Employee> login(LoginRequest loginRequest) {
        Optional<Employee> employee = employeeRepository.findAll().stream()
                .filter(e -> e.getEmail().equals(loginRequest.getEmail()) && e.getPassword().equals(loginRequest.getPassword()))
                .findFirst();

        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    public ResponseEntity<Employee> createEmployee(EmployeeRequest employeeRequest, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .email(employeeRequest.getEmail())
                .password(employeeRequest.getPassword())
                .department(department)
                .employeeRole(Employee.EmployeeRole.EMPLOYEE)
                .active(true)
                .build();

        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    public ResponseEntity<Employee> updateEmployee(EmployeeRequest employeeRequest, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        Employee employee = employeeRepository.findAll().stream()
                .filter(e -> e.getEmail().equals(employeeRequest.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employeeRequest.getName() != null) {
            employee.setName(employeeRequest.getName());
        }
        if (employeeRequest.getPassword() != null) {
            employee.setPassword(employeeRequest.getPassword());
        }
        if (employeeRequest.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
        }

        return ResponseEntity.ok(employeeRepository.save(employee));
    }
}
