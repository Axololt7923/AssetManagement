package com.axololt.assetmanagement.service;

import com.axololt.assetmanagement.dto.EmployeeRequest;
import com.axololt.assetmanagement.dto.LoginRequest;
import com.axololt.assetmanagement.entity.Department;
import com.axololt.assetmanagement.entity.Employee;
import com.axololt.assetmanagement.repository.DepartmentRepository;
import com.axololt.assetmanagement.repository.EmployeeRepository;
import static com.axololt.assetmanagement.utils.PermitCheck.isPermit;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public ResponseEntity<Employee> login(LoginRequest loginRequest) {
        return employeeRepository.findByEmail(loginRequest.getEmail())
                .filter(e -> e.getPassword().equals(loginRequest.getPassword()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    public ResponseEntity<List<Employee>> getEmployees(String name, String email, UUID departmentId, Integer page, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        Specification<Employee> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }

            if (departmentId != null) {
                predicates.add(criteriaBuilder.equal(root.get("department").get("id"), departmentId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return ResponseEntity.ok(employeeRepository.findAll(spec, PageRequest.of(page, 25)).getContent());
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
        Employee employee = employeeRepository.findByEmail(employeeRequest.getEmail())
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
