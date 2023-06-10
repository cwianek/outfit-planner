package com.outfit.planner.system.domain.outbox.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit.planner.system.domain.outbox.model.ProductOutboxMessage;
import com.outfit.planner.system.domain.ports.output.repository.ProductOutboxRepository;
import com.outfit.planner.system.outbox.OutboxStatus;
import com.outfit.planner.system.product.service.domain.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class ProductOutboxHelper {

    private final ObjectMapper objectMapper;

    private final ProductOutboxRepository productOutboxRepository;

    public ProductOutboxHelper(ObjectMapper objectMapper, ProductOutboxRepository productOutboxRepository) {
        this.objectMapper = objectMapper;
        this.productOutboxRepository = productOutboxRepository;
    }

    @Transactional
    public void save(ProductOutboxMessage productOutboxMessage) {
        ProductOutboxMessage message = productOutboxRepository.save(productOutboxMessage);
        if (message == null) {
            log.error("Could not save OrderPaymentOutboxMessage with outbox id: {}", productOutboxMessage.getId());
            throw new RuntimeException("Could not save OrderPaymentOutboxMessage with outbox id: " +
                    productOutboxMessage.getId());
        }
        log.info("OrderPaymentOutboxMessage saved with outbox id: {}", productOutboxMessage.getId());
    }

    @Transactional(readOnly = true)
    public List<ProductOutboxMessage> getProductOutboxMessageByOutboxStatus(
            OutboxStatus outboxStatus) {
        return productOutboxRepository.findByOutboxStatus(outboxStatus);
    }


    @Transactional
    public void saveProductOutboxMessage(ProductCreatedEvent productCreatedEvent,
                                         OutboxStatus outboxStatus) {
        save(ProductOutboxMessage.builder()
                .id(UUID.randomUUID())
//                .createdAt(paymentEventPayload.getCreatedAt())
//                .type(ORDER_SAGA_NAME)
                .payload(createPayload(productCreatedEvent))
                .outboxStatus(outboxStatus)
                .build());
    }

    private String createPayload(ProductCreatedEvent productCreatedEvent) {
        try {
            return objectMapper.writeValueAsString(productCreatedEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
