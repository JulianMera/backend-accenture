package com.demo.backend_accenture.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @JsonManagedReference
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Product> products;
}
