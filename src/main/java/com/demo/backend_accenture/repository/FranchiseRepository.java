package com.demo.backend_accenture.repository;

import com.demo.backend_accenture.entity.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
