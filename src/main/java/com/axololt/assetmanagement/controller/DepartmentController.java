package com.axololt.assetmanagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/department")
public class DepartmentController {
    @PostMapping("/add")
    public String create() {
        return "create";
    }
}
