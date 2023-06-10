package com.outfit.planner.system.product.service.dataaccess.product.outbox;

import com.outfit.planner.system.domain.outbox.model.ProductOutboxMessage;
import com.outfit.planner.system.domain.ports.output.repository.ProductOutboxRepository;
import com.outfit.planner.system.outbox.OutboxStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductOutboxRepositoryImpl implements ProductOutboxRepository {
    private final ProductOutboxJpaRepository productOutboxJpaRepository;

    public ProductOutboxRepositoryImpl(ProductOutboxJpaRepository productOutboxJpaRepository) {
        this.productOutboxJpaRepository = productOutboxJpaRepository;
    }

    @Override
    public ProductOutboxMessage save(ProductOutboxMessage productOutboxMessage) {
        ProductOutboxEntity entity = ProductOutboxEntity.builder()
                .id(productOutboxMessage.getId())
                .outboxStatus(productOutboxMessage.getOutboxStatus())
                .payload(productOutboxMessage.getPayload())
                .build();

        ProductOutboxEntity saved = this.productOutboxJpaRepository.save(entity);

        return ProductOutboxMessage.builder()
                .outboxStatus(saved.getOutboxStatus())
                .id(saved.getId())
                .payload(saved.getPayload())
                .build();
    }

    @Override
    public List<ProductOutboxMessage> findByOutboxStatus(OutboxStatus outboxStatus) {
        return productOutboxJpaRepository.findByOutboxStatus(outboxStatus);
    }

}
