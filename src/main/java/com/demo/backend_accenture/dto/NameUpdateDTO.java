package com.demo.backend_accenture.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NameUpdateDTO {

    @NotBlank(message = "Name is required")
    private String name;
}
