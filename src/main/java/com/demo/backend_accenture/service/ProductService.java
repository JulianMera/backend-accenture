package com.demo.backend_accenture.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.demo.backend_accenture.repository.ProductRepository;
import com.demo.backend_accenture.repository.BranchRepository;
import com.demo.backend_accenture.entity.Product;
import com.demo.backend_accenture.entity.Branch;

import com.demo.backend_accenture.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public Product addProduct(Long branchId, String productName, Integer stock) {
        Branch branch = branchRepository.findById(branchId)
        .orElseThrow(() -> new RuntimeException("Branch not found"));

        Product product = Product.builder()
        .name(productName)
        .stock(stock)
        .branch(branch)
        .build();
        
        return productRepository.save(product);
    }

    public Product deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return null;
    }

    public Product updateProduct(Long productId, String productName, Integer stock) {
        Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setStock(stock);
        return productRepository.save(product);
    }

    public Product updateProductName(Long productId, String name) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        product.setName(name);
        return productRepository.save(product);
    }
}
