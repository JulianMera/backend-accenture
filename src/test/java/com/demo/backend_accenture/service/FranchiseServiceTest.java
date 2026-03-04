package com.demo.backend_accenture.service;

import com.demo.backend_accenture.dto.FranchiseRequestDTO;
import com.demo.backend_accenture.dto.FranchiseResponseDTO;
import com.demo.backend_accenture.dto.TopProductResponseDTO;
import com.demo.backend_accenture.entity.Franchise;
import com.demo.backend_accenture.entity.Product;
import com.demo.backend_accenture.entity.Branch;
import com.demo.backend_accenture.exception.ResourceNotFoundException;
import com.demo.backend_accenture.repository.FranchiseRepository;
import com.demo.backend_accenture.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    @Test
    void createFranchise_shouldReturnResponseDTO() {
        FranchiseRequestDTO request = new FranchiseRequestDTO();
        request.setName("Franquicia Test");

        Franchise saved = Franchise.builder().id(1L).name("Franquicia Test").build();
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(saved);

        FranchiseResponseDTO result = franchiseService.createFranchise(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Franquicia Test");
        verify(franchiseRepository, times(2)).save(any(Franchise.class));
    }

    @Test
    void getTopProductsByFranchiseId_shouldReturnList() {
        Long franchiseId = 1L;
        Branch branch = Branch.builder().id(1L).name("Sucursal 1").build();
        Product product = Product.builder().id(1L).name("Producto 1").stock(10).branch(branch).build();
        when(productRepository.findTopProductsByFranchiseId(franchiseId)).thenReturn(List.of(product));

        List<TopProductResponseDTO> result = franchiseService.getTopProductsByFranchiseId(franchiseId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductName()).isEqualTo("Producto 1");
        assertThat(result.get(0).getBranchName()).isEqualTo("Sucursal 1");
        assertThat(result.get(0).getStock()).isEqualTo(10);
    }

    @Test
    void updateFranchiseName_whenExists_shouldReturnUpdated() {
        Long franchiseId = 1L;
        String newName = "Franquicia Actualizada";
        Franchise existing = Franchise.builder().id(franchiseId).name("Viejo").build();
        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.of(existing));
        when(franchiseRepository.save(any(Franchise.class))).thenAnswer(inv -> {
            Franchise f = inv.getArgument(0);
            f.setName(newName);
            return f;
        });

        FranchiseResponseDTO result = franchiseService.updateFranchiseName(franchiseId, newName);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(franchiseId);
        assertThat(result.getName()).isEqualTo(newName);
        verify(franchiseRepository).findById(franchiseId);
        verify(franchiseRepository).save(existing);
    }

    @Test
    void updateFranchiseName_whenNotExists_shouldThrow() {
        Long franchiseId = 999L;
        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> franchiseService.updateFranchiseName(franchiseId, "Nombre"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Franchise not found with id: 999");
    }
}
