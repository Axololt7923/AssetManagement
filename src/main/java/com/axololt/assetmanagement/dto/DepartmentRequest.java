package com.axololt.assetmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequest {
    @NotBlank
    private String name;
}
