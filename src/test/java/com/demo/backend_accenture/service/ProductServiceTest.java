package com.demo.backend_accenture.service;

import com.demo.backend_accenture.entity.Branch;
import com.demo.backend_accenture.entity.Product;
import com.demo.backend_accenture.exception.ResourceNotFoundException;
import com.demo.backend_accenture.repository.BranchRepository;
import com.demo.backend_accenture.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void addProduct_whenBranchExists_shouldReturnProduct() {
        Long branchId = 1L;
        Branch branch = Branch.builder().id(branchId).name("Sucursal").build();
        Product saved = Product.builder().id(1L).name("Producto").stock(5).branch(branch).build();

        when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));
        when(productRepository.save(any(Product.class))).thenReturn(saved);

        Product result = productService.addProduct(branchId, "Producto", 5);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Producto");
        assertThat(result.getStock()).isEqualTo(5);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void addProduct_whenBranchNotExists_shouldThrow() {
        Long branchId = 999L;
        when(branchRepository.findById(branchId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.addProduct(branchId, "Producto", 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Branch not found");
    }

    @Test
    void updateProductName_whenExists_shouldReturnUpdated() {
        Long productId = 1L;
        String newName = "Producto Actualizado";
        Product existing = Product.builder().id(productId).name("Viejo").stock(10).build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            p.setName(newName);
            return p;
        });

        Product result = productService.updateProductName(productId, newName);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(newName);
        verify(productRepository).findById(productId);
        verify(productRepository).save(existing);
    }

    @Test
    void updateProductName_whenNotExists_shouldThrow() {
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProductName(productId, "Nombre"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product not found with id: 999");
    }

    @Test
    void deleteProduct_shouldCallRepository() {
        Long productId = 1L;
        productService.deleteProduct(productId);
        verify(productRepository).deleteById(productId);
    }
}
