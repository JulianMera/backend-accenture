package com.demo.backend_accenture.service;

import com.demo.backend_accenture.entity.Franchise;
import com.demo.backend_accenture.dto.FranchiseRequestDTO;
import com.demo.backend_accenture.dto.FranchiseResponseDTO;
import com.demo.backend_accenture.repository.FranchiseRepository;
import com.demo.backend_accenture.repository.ProductRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.demo.backend_accenture.entity.Product;
import com.demo.backend_accenture.dto.TopProductResponseDTO;
import com.demo.backend_accenture.exception.ResourceNotFoundException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final ProductRepository productRepository;

    public FranchiseResponseDTO createFranchise(FranchiseRequestDTO franchiseRequestDTO) {
        Franchise franchise = franchiseRepository.save(Franchise.builder()
        .name(franchiseRequestDTO.getName())
        .build());

        Franchise savedFranchise = franchiseRepository.save(franchise);

        return FranchiseResponseDTO.builder()
        .id(savedFranchise.getId())
        .name(savedFranchise.getName())
        .build();
    }

    public List<TopProductResponseDTO> getTopProductsByFranchiseId(Long franchiseId) {
        List<Product> products = productRepository.findTopProductsByFranchiseId(franchiseId);

        return products.stream()
        .map(product -> TopProductResponseDTO.builder()
        .productName(product.getName())
        .branchName(product.getBranch().getName())
        .stock(product.getStock())
        .build())
        .collect(Collectors.toList());
    }

    public FranchiseResponseDTO updateFranchiseName(Long franchiseId, String name) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found with id: " + franchiseId));
        franchise.setName(name);
        Franchise updated = franchiseRepository.save(franchise);
        return FranchiseResponseDTO.builder()
                .id(updated.getId())
                .name(updated.getName())
                .build();
    }
}
