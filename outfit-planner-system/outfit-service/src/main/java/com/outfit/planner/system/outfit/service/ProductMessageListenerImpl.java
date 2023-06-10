package com.outfit.planner.system.outfit.service;

import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;
import com.outfit.planner.system.outfit.service.dataaccess.product.repository.ProductRepository;
import com.outfit.planner.system.outfit.service.listener.ProductMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductMessageListenerImpl implements ProductMessageListener {

    private final ProductRepository productRepository;

    public ProductMessageListenerImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void productCreated(ProductEntity productEntity) {
        ProductEntity saved = productRepository.save(productEntity);

        log.info("Product is created in product database with id: {}", saved.getId());
    }
}
