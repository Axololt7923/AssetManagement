package com.axololt.assetmanagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/employee")
public class EmployeeController {

    @PostMapping("/login")
    public String login() {
        //this should be token
        return "login";
    }

    @PostMapping("/add")
    public String add() {

        return "add";
    }

}
