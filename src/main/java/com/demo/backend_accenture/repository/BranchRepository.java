package com.demo.backend_accenture.repository;

import com.demo.backend_accenture.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
