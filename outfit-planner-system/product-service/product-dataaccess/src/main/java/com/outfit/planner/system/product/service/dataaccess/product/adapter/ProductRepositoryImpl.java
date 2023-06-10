package com.outfit.planner.system.product.service.dataaccess.product.adapter;

import com.outfit.planner.system.product.service.dataaccess.product.entity.ProductEntity;
import com.outfit.planner.system.product.service.dataaccess.product.mapper.ProductDataAccessMapper;
import com.outfit.planner.system.product.service.dataaccess.product.repository.ProductJpaRepository;
import com.outfit.planner.system.product.service.domain.GetProductsCriteria;
import com.outfit.planner.system.product.service.domain.entity.Product;
import com.outfit.planner.system.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;

    private final ProductDataAccessMapper productDataAccessMapper;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository, ProductDataAccessMapper productDataAccessMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productDataAccessMapper = productDataAccessMapper;
    }


    @Override
    public List<Product> getProducts(GetProductsCriteria criteria) {
        return productJpaRepository.findAllByUsername(criteria.getUsername()).stream()
                .map(productDataAccessMapper::productEntityToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Product createProduct(Product product) {
        return productDataAccessMapper.productEntityToProduct(
                productJpaRepository.save(productDataAccessMapper.productToProductEntity(product))
        );
    }

    @Override
    public Optional<byte[]> getImage(UUID id) {
        Optional<ProductEntity> entityOptional = productJpaRepository.findById(id);
        return entityOptional.map(ProductEntity::getImage);
    }

    @Override
    public Optional<byte[]> getImage(UUID id, String username) {
        Optional<ProductEntity> entityOptional = productJpaRepository.findByIdAndUsername(id, username);
        return entityOptional.map(ProductEntity::getImage);
    }
}
