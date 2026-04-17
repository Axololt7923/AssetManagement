package com.axololt.assetmanagement.controller;

import com.axololt.assetmanagement.dto.AssetRequest;
import com.axololt.assetmanagement.entity.Asset;
import com.axololt.assetmanagement.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/asset")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("/add-asset")
    public ResponseEntity<Asset> create(
            @RequestHeader(name = "access-key", required = false) String accessKey,
            @RequestBody AssetRequest assetRequest
    ) {
        return assetService.createAsset(assetRequest, accessKey);
    }

    @GetMapping("/list-assets")
    public ResponseEntity<List<Asset>> getAssets(
            @RequestHeader(name = "access-key", required = false) String accessKey,
            @RequestParam(required = false) String assetType,
            @RequestParam(required = false) String assetName,
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return assetService.getAssets(assetType, assetName, employeeId, page, accessKey);
    }

    @GetMapping("/show-asset")
    public ResponseEntity<Asset> getAssetDetails(
            @RequestHeader(name = "access-key", required = false) String accessKey,
            @RequestParam UUID id) {
        return assetService.getAssetDetails(id, accessKey);
    }

    @PatchMapping("/update-asset")
    public ResponseEntity<Asset> updateAsset(
            @RequestHeader(name = "access-key", required = false) String accessKey,
            @RequestParam UUID id,
            @RequestBody AssetRequest assetRequest) {
        return assetService.updateAsset(id, assetRequest, accessKey);
    }

    @DeleteMapping("/remove-asset")
    public ResponseEntity<Void> removeAsset(
            @RequestHeader(name = "access-key", required = false) String accessKey,
            @RequestParam UUID id
    ) {
        return assetService.removeAsset(id, accessKey);
    }
}
