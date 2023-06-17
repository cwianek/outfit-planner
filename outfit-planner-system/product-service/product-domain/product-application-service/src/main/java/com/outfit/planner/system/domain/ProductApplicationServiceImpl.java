package com.outfit.planner.system.domain;

import com.outfit.planner.system.domain.mapper.ProductDataMapper;
import com.outfit.planner.system.domain.outbox.scheduler.ProductOutboxHelper;
import com.outfit.planner.system.domain.ports.input.service.ProductApplicationService;
import com.outfit.planner.system.domain.ports.output.repository.ProductRepository;
import com.outfit.planner.system.outbox.OutboxStatus;
import com.outfit.planner.system.domain.dto.create.GetProductsCriteria;
import com.outfit.planner.system.product.service.domain.ProductDomainService;
import com.outfit.planner.system.product.service.domain.entity.Product;
import com.outfit.planner.system.product.service.domain.event.ProductCreatedEvent;
import com.outfit.planner.system.domain.dto.create.CreateProductRequest;
import com.outfit.planner.system.domain.dto.create.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
public class ProductApplicationServiceImpl implements ProductApplicationService {

    private final ProductDataMapper productDataMapper;

    private final ProductDomainService productDomainService;

    private final ProductRepository productRepository;

    private final ProductOutboxHelper productOutboxHelper;

    public ProductApplicationServiceImpl(ProductDataMapper productDataMapper, ProductDomainService productDomainService, ProductRepository productRepository, ProductOutboxHelper productOutboxHelper) {
        this.productDataMapper = productDataMapper;
        this.productDomainService = productDomainService;
        this.productRepository = productRepository;
        this.productOutboxHelper = productOutboxHelper;
    }

    @Override
    public List<ProductDto> getProducts(GetProductsCriteria criteria) {
        return productRepository.getProducts(criteria).stream()
                .map(productDataMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto createProduct(CreateProductRequest createProductRequest) {
        Product product = this.productDataMapper.createProductRequestToProduct(createProductRequest);
        ProductCreatedEvent productCreatedEvent = productDomainService.validateAndInitiateProduct(product);
        Product savedProduct = productRepository.createProduct(product);

        productOutboxHelper.saveProductOutboxMessage(productCreatedEvent, OutboxStatus.STARTED);

        return productDataMapper.productToProductDto(savedProduct);
    }

    @Override
    public byte[] getImage(String productId) {
        return productRepository.getImage(UUID.fromString(productId))
                .orElse(null);
    }

    @Override
    public byte[] getImage(String productId, String username) {
        return productRepository.getImage(UUID.fromString(productId), username)
                .orElse(null);
    }
}
