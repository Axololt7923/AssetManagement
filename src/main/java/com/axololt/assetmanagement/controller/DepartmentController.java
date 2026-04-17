package com.axololt.assetmanagement.controller;

import com.axololt.assetmanagement.dto.DepartmentRequest;
import com.axololt.assetmanagement.entity.Department;
import com.axololt.assetmanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("add-department")
    public ResponseEntity<Department> create(
            @RequestHeader(name = "access-key", required = false) String accessKey,
            @RequestBody DepartmentRequest departmentRequest
    ) {
        return departmentService.createDepartment(departmentRequest, accessKey);
    }
}
