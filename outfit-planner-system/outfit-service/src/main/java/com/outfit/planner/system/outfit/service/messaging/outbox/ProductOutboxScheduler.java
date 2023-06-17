package com.outfit.planner.system.outfit.service.messaging.outbox;

import com.outfit.planner.system.outbox.OutboxScheduler;
import com.outfit.planner.system.outbox.OutboxStatus;
import com.outfit.planner.system.outfit.service.dataaccess.outbox.entity.ProductOutboxMessageEntity;
import com.outfit.planner.system.outfit.service.listener.ProductMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductOutboxScheduler implements OutboxScheduler {

    private final ProductOutboxHelper productOutboxHelper;

    private final ProductMessageListener productMessageListener;

    public ProductOutboxScheduler(ProductOutboxHelper paymentOutboxHelper, ProductMessageListener productMessageListener) {
        this.productOutboxHelper = paymentOutboxHelper;
        this.productMessageListener = productMessageListener;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${outfit-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${outfit-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        List<ProductOutboxMessageEntity> outboxMessages = productOutboxHelper.getProductOutboxMessageByOutboxStatus(
                OutboxStatus.STARTED);

        if (CollectionUtils.isEmpty(outboxMessages)) {
            return;
        }

        log.info("Received {} ProductMessage with ids: {}, sending to message bus!",
                outboxMessages.size(),
                outboxMessages.stream()
                        .map(ProductOutboxMessageEntity::getId)
                        .collect(Collectors.joining(",")));

        outboxMessages.forEach(outboxMessage -> {
            productMessageListener.productCreated(outboxMessage.getPayload());
            updateOutboxStatus(outboxMessage, OutboxStatus.COMPLETED);
        });
    }

    private void updateOutboxStatus(ProductOutboxMessageEntity productOutboxMessage, OutboxStatus outboxStatus) {
        productOutboxMessage.setOutboxStatus(outboxStatus);
        productOutboxHelper.save(productOutboxMessage);
        log.info("ProductOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
