package com.demo.backend_accenture.service;

import com.demo.backend_accenture.entity.Branch;
import com.demo.backend_accenture.entity.Franchise;
import com.demo.backend_accenture.repository.BranchRepository;
import com.demo.backend_accenture.repository.FranchiseRepository;
import lombok.*;
import org.springframework.stereotype.*;

import com.demo.backend_accenture.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    public Branch addBranchToFranchise(Long franchiseId, String branchName) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
        .orElseThrow(() -> new ResourceNotFoundException("Franchise not found"));

        Branch branch = Branch.builder()
        .name(branchName)
        .franchise(franchise)
        .build();
        
        return branchRepository.save(branch);
    }

    public Branch updateBranchName(Long branchId, String name) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));
        branch.setName(name);
        return branchRepository.save(branch);
    }
}
