package com.axololt.assetmanagement.service;

import com.axololt.assetmanagement.dto.DepartmentRequest;
import com.axololt.assetmanagement.entity.Department;
import com.axololt.assetmanagement.repository.DepartmentRepository;
import static com.axololt.assetmanagement.utils.PermitCheck.isPermit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public ResponseEntity<Department> createDepartment(DepartmentRequest departmentRequest, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        Department department = Department.builder()
                .name(departmentRequest.getName())
                .build();
        return ResponseEntity.ok(departmentRepository.save(department));
    }
}
