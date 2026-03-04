package com.demo.backend_accenture.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class BranchRequestDTO {
    @NotBlank
    private String name;
    
}
