package com.axololt.assetmanagement.service;

import com.axololt.assetmanagement.dto.AssetRequest;
import com.axololt.assetmanagement.entity.Asset;
import com.axololt.assetmanagement.entity.Employee;
import com.axololt.assetmanagement.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    private AssetRequest assetRequest;
    private Asset asset;
    private final String accessKey = "testKey";

    @BeforeEach
    void setUp() {
        assetRequest = new AssetRequest();
        assetRequest.setName("Test Asset");
        assetRequest.setDescription("Test Description");
        assetRequest.setPrice(100.0f);
        assetRequest.setAssetType("electronic");

        asset = Asset.builder()
                .id(UUID.randomUUID())
                .name("Test Asset")
                .description("Test Description")
                .price(100.0f)
                .type(Asset.AssetType.electronic)
                .build();
    }

    @Test
    void createAsset_Success() {
        when(assetRepository.save(any(Asset.class))).thenReturn(asset);

        ResponseEntity<Asset> response = assetService.createAsset(assetRequest, accessKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Asset", response.getBody().getName());
        verify(assetRepository).save(any(Asset.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAssets_WithAllFilters() {
        UUID employeeId = UUID.randomUUID();
        Page<Asset> page = new PageImpl<>(List.of(asset));
        when(assetRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        ResponseEntity<List<Asset>> response = assetService.getAssets("electronic", "Test", employeeId, 0, accessKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        verify(assetRepository).findAll(any(Specification.class), eq(PageRequest.of(0, 25)));
    }

    @Test
    void getAssetDetails_Success() {
        UUID id = asset.getId();
        when(assetRepository.findById(id)).thenReturn(Optional.of(asset));

        ResponseEntity<Asset> response = assetService.getAssetDetails(id, accessKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(asset, response.getBody());
    }

    @Test
    void getAssetDetails_NotFound() {
        UUID id = UUID.randomUUID();
        when(assetRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Asset> response = assetService.getAssetDetails(id, accessKey);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateAsset_Success() {
        UUID id = asset.getId();
        when(assetRepository.findById(id)).thenReturn(Optional.of(asset));
        when(assetRepository.save(any(Asset.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assetRequest.setDescription("Updated Description");
        ResponseEntity<Asset> response = assetService.updateAsset(id, assetRequest, accessKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Description", response.getBody().getDescription());
        verify(assetRepository).save(asset);
    }

    @Test
    void updateAsset_NotFound() {
        UUID id = UUID.randomUUID();
        when(assetRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Asset> response = assetService.updateAsset(id, assetRequest, accessKey);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void removeAsset_Success() {
        UUID id = asset.getId();
        when(assetRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = assetService.removeAsset(id, accessKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(assetRepository).deleteById(id);
    }

    @Test
    void removeAsset_NotFound() {
        UUID id = UUID.randomUUID();
        when(assetRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = assetService.removeAsset(id, accessKey);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(assetRepository, never()).deleteById(any());
    }
}
