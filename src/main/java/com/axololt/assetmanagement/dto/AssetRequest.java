package com.axololt.assetmanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AssetRequest {
    @NotBlank
    private String name;
    private String description;
    private Float price;

    @NotBlank
    @Pattern(regexp = "electronic|furniture", message = "Only electronic or furniture")
    private String assetType;
}
