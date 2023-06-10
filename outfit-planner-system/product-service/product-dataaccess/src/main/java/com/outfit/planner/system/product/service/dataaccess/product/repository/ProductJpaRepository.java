package com.outfit.planner.system.product.service.dataaccess.product.repository;

import com.outfit.planner.system.product.service.dataaccess.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findAllByUsername(String username);
    Optional<ProductEntity> findByIdAndUsername(UUID id, String username);
}
