package com.demo.backend_accenture.controller;

import com.demo.backend_accenture.dto.FranchiseRequestDTO;
import com.demo.backend_accenture.dto.FranchiseResponseDTO;
import com.demo.backend_accenture.dto.NameUpdateDTO;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.backend_accenture.dto.TopProductResponseDTO;
import com.demo.backend_accenture.service.FranchiseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
@Tag(name = "Franquicias", description = "API de franquicias: creación, top productos y actualización de nombre")
public class FranchiseController {
    private final FranchiseService franchiseService;

    @Operation(summary = "Crear franquicia", description = "Crea una nueva franquicia con el nombre indicado")
    @PostMapping
    public FranchiseResponseDTO createFranchise
    (@RequestBody @Valid FranchiseRequestDTO franchiseRequestDTO) {
        return franchiseService.createFranchise(franchiseRequestDTO);
    }

    @Operation(summary = "Top productos por franquicia", description = "Obtiene los productos con mayor stock por sucursal para una franquicia")
    @GetMapping("/{franchiseId}/top-products")
    public ResponseEntity<List<TopProductResponseDTO>> getTopProductsByFranchiseId(@PathVariable Long franchiseId) {
        List<TopProductResponseDTO> topProducts = franchiseService.getTopProductsByFranchiseId(franchiseId);
        return ResponseEntity.ok(topProducts);
    }

    @Operation(summary = "Actualizar nombre de franquicia", description = "Actualiza el nombre de una franquicia existente por ID")
    @PutMapping("/{franchiseId}")
    public FranchiseResponseDTO updateFranchiseName(
            @PathVariable Long franchiseId,
            @RequestBody @Valid NameUpdateDTO nameUpdateDTO) {
        return franchiseService.updateFranchiseName(franchiseId, nameUpdateDTO.getName());
    }
}
