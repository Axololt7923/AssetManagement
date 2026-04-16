package com.axololt.assetmanagement.controller;

import com.axololt.assetmanagement.entity.Asset;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/api/asset")
public class AssetController {

    @PostMapping("/add")
    public String create() {
        return "create";
    }

    @GetMapping("/list")
    public List<Asset> getAssets(
            @RequestParam(required = false) String assetType,
            @RequestParam(required = false) String assetName,
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return List.of();
    }

    @GetMapping("/details")
    public Asset getAssetDetails(@RequestParam UUID id) {
        return new Asset();
    }

    @PatchMapping("/update")
    public String updateAsset() {
        return "update";
    }

    @PatchMapping("/remove")
    public String removeAsset() {
        return "delete";
    }
}
