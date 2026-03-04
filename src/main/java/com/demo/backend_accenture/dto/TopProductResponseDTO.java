package com.demo.backend_accenture.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TopProductResponseDTO {
    
    private String productName;
    private String branchName;    
    private Integer stock;
}
