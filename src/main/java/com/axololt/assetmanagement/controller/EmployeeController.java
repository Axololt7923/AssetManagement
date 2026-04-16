package com.axololt.assetmanagement.controller;

import com.axololt.assetmanagement.dto.AccessRequest;
import com.axololt.assetmanagement.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/employee")
public class EmployeeController {

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest requestBody) {
        //this should be token
        return "login";
    }

    @PostMapping("/add")
    public String add(@RequestBody AccessRequest requestBody,
                      @RequestBody LoginRequest loginRequest) {

        return "add";
    }

}
