package com.demo.backend_accenture.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class StockUpdateDTO {
    @NotNull
    private Integer stock;
    
}
