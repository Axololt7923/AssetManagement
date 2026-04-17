package com.axololt.assetmanagement.webcontroller;

import com.axololt.assetmanagement.entity.Asset;
import com.axololt.assetmanagement.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AssetWebController {

    private final AssetService assetService;

    @GetMapping("/assets")
    public String getAssets(
            @RequestParam(required = false) String assetType,
            @RequestParam(required = false) String assetName,
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(defaultValue = "0") Integer page,
            Model model) {
        
        // Using "web-access" as a placeholder for accessKey since PermitCheck currently returns true for any key
        ResponseEntity<List<Asset>> response = assetService.getAssets(assetType, assetName, employeeId, page, "web-access");
        
        model.addAttribute("assets", response.getBody());
        model.addAttribute("assetType", assetType);
        model.addAttribute("assetName", assetName);
        
        return "assets";
    }
}
