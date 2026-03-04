package com.demo.backend_accenture.repository;

import com.demo.backend_accenture.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p
            FROM Product p
            JOIN p.branch b
            JOIN b.franchise f
            WHERE f.id = :franchiseId
            AND p.stock = (
                SELECT MAX(p2.stock)
                FROM Product p2
                WHERE p2.branch.id = b.id
            )
            """)
    List<Product> findTopProductsByFranchiseId(Long franchiseId);
}
