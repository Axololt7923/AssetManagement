package com.axololt.assetmanagement.service;

import com.axololt.assetmanagement.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public void createAsset() {

    }
}
