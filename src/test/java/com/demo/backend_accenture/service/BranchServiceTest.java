package com.demo.backend_accenture.service;

import com.demo.backend_accenture.entity.Branch;
import com.demo.backend_accenture.entity.Franchise;
import com.demo.backend_accenture.exception.ResourceNotFoundException;
import com.demo.backend_accenture.repository.BranchRepository;
import com.demo.backend_accenture.repository.FranchiseRepository;
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
class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private BranchService branchService;

    @Test
    void addBranchToFranchise_whenFranchiseExists_shouldReturnBranch() {
        Long franchiseId = 1L;
        String branchName = "Sucursal Centro";
        Franchise franchise = Franchise.builder().id(franchiseId).name("Franquicia").build();
        Branch saved = Branch.builder().id(1L).name(branchName).franchise(franchise).build();

        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.of(franchise));
        when(branchRepository.save(any(Branch.class))).thenReturn(saved);

        Branch result = branchService.addBranchToFranchise(franchiseId, branchName);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(branchName);
        assertThat(result.getFranchise()).isEqualTo(franchise);
        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void addBranchToFranchise_whenFranchiseNotExists_shouldThrow() {
        Long franchiseId = 999L;
        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> branchService.addBranchToFranchise(franchiseId, "Sucursal"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Franchise not found");
    }

    @Test
    void updateBranchName_whenExists_shouldReturnUpdated() {
        Long branchId = 1L;
        String newName = "Sucursal Actualizada";
        Branch existing = Branch.builder().id(branchId).name("Viejo").build();
        when(branchRepository.findById(branchId)).thenReturn(Optional.of(existing));
        when(branchRepository.save(any(Branch.class))).thenAnswer(inv -> {
            Branch b = inv.getArgument(0);
            b.setName(newName);
            return b;
        });

        Branch result = branchService.updateBranchName(branchId, newName);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(newName);
        verify(branchRepository).findById(branchId);
        verify(branchRepository).save(existing);
    }

    @Test
    void updateBranchName_whenNotExists_shouldThrow() {
        Long branchId = 999L;
        when(branchRepository.findById(branchId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> branchService.updateBranchName(branchId, "Nombre"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Branch not found with id: 999");
    }
}
