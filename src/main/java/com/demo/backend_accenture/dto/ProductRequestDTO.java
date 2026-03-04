package com.demo.backend_accenture.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ProductRequestDTO {
    
    @NotBlank
    private String name;

    @NotNull
    private Integer stock;
    
}
