package com.demo.backend_accenture.controller;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.demo.backend_accenture.dto.NameUpdateDTO;
import com.demo.backend_accenture.entity.Branch;
import com.demo.backend_accenture.service.BranchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "API de sucursales (branches): alta por franquicia y actualización de nombre")
public class BranchController {
    private final BranchService branchService;

    @Operation(summary = "Añadir sucursal", description = "Crea una nueva sucursal asociada a una franquicia. Body: nombre en texto plano o JSON string.")
    @PostMapping("/franchise/{franchiseId}")
    public Branch addBranch(
        @PathVariable Long franchiseId, 
        @RequestBody String branchName) {

        return branchService.addBranchToFranchise(franchiseId, branchName);
    }

    @Operation(summary = "Actualizar nombre de sucursal", description = "Actualiza el nombre de una sucursal por ID")
    @PutMapping("/{branchId}")
    public Branch updateBranchName(
            @PathVariable Long branchId,
            @RequestBody @Valid NameUpdateDTO nameUpdateDTO) {
        return branchService.updateBranchName(branchId, nameUpdateDTO.getName());
    }
}
