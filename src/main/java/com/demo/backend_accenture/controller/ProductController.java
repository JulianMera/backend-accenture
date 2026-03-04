package com.demo.backend_accenture.controller;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.demo.backend_accenture.dto.NameUpdateDTO;
import com.demo.backend_accenture.dto.ProductRequestDTO;
import com.demo.backend_accenture.entity.Product;
import com.demo.backend_accenture.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API de productos: alta por sucursal, actualización de stock/nombre y eliminación")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Añadir producto", description = "Crea un producto en una sucursal con nombre y stock inicial")
    @PostMapping("/branch/{branchId}")
    public Product addProduct(
            @PathVariable Long branchId,
            @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.addProduct(branchId, productRequestDTO.getName(), productRequestDTO.getStock());
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @PutMapping("/{productId}/stock")
    public Product updateProduct(
            @PathVariable Long productId,
            @RequestBody String productName,
            @RequestBody Integer stock) {
        return productService.updateProduct(productId, productName, stock);
    }

    @Operation(summary = "Actualizar nombre de producto", description = "Actualiza el nombre de un producto por ID")
    @PutMapping("/{productId}/name")
    public Product updateProductName(
            @PathVariable Long productId,
            @RequestBody @Valid NameUpdateDTO nameUpdateDTO) {
        return productService.updateProductName(productId, nameUpdateDTO.getName());
    }
}
