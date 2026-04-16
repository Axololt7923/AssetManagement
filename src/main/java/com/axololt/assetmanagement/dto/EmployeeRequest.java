package com.axololt.assetmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private String name;
    private UUID departmentId;
}
