package com.axololt.assetmanagement.service;

import com.axololt.assetmanagement.dto.AssetRequest;
import com.axololt.assetmanagement.entity.Asset;
import com.axololt.assetmanagement.repository.AssetRepository;
import static com.axololt.assetmanagement.utils.PermitCheck.isPermit;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public ResponseEntity<Asset> createAsset(AssetRequest assetRequest, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        Asset asset = Asset.builder()
                .name(assetRequest.getName())
                .description(assetRequest.getDescription())
                .price(assetRequest.getPrice())
                .type(Asset.AssetType.valueOf(assetRequest.getAssetType()))
                .build();
        return ResponseEntity.ok(assetRepository.save(asset));
    }

    public ResponseEntity<List<Asset>> getAssets(String assetType, String assetName, UUID employeeId, Integer page, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        Specification<Asset> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (assetType != null && !assetType.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("type"), Asset.AssetType.valueOf(assetType)));
            }

            if (assetName != null && !assetName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + assetName.toLowerCase() + "%"));
            }

            if (employeeId != null) {
                predicates.add(criteriaBuilder.equal(root.get("currentEmployee").get("id"), employeeId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return ResponseEntity.ok(assetRepository.findAll(spec, PageRequest.of(page, 25)).getContent());
    }

    public ResponseEntity<Asset> getAssetDetails(UUID id, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        return assetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Asset> updateAsset(UUID id, AssetRequest assetRequest, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        return assetRepository.findById(id)
                .stream().findFirst()
                .map(asset -> {
                    if (assetRequest.getDescription() != null) asset.setDescription(assetRequest.getDescription());
                    if (assetRequest.getPrice() != null) asset.setPrice(assetRequest.getPrice());
                    if (assetRequest.getAssetType() != null) asset.setType(Asset.AssetType.valueOf(assetRequest.getAssetType()));
                    return ResponseEntity.ok(assetRepository.save(asset));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> removeAsset(UUID id, String accessKey) {
        if (!isPermit(accessKey)) {
            return ResponseEntity.status(403).build();
        }
        if (assetRepository.existsById(id)) {
            assetRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
