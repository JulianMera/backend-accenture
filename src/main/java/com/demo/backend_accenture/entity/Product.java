package com.demo.backend_accenture.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer stock;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
