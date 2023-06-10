package com.outfit.planner.system.domain.outbox.scheduler;

import com.outfit.planner.system.domain.outbox.model.ProductOutboxMessage;
import com.outfit.planner.system.domain.ports.output.message.publisher.ProductMessagePublisher;
import com.outfit.planner.system.outbox.OutboxScheduler;
import com.outfit.planner.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductOutboxScheduler implements OutboxScheduler {

    private final ProductOutboxHelper paymentOutboxHelper;
    private final ProductMessagePublisher productMessagePublisher;

    public ProductOutboxScheduler(ProductOutboxHelper paymentOutboxHelper, ProductMessagePublisher productMessagePublisher) {
        this.paymentOutboxHelper = paymentOutboxHelper;
        this.productMessagePublisher = productMessagePublisher;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${product-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${product-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        List<ProductOutboxMessage> outboxMessages =
                paymentOutboxHelper.getProductOutboxMessageByOutboxStatus(
                        OutboxStatus.STARTED);

        if (!CollectionUtils.isEmpty(outboxMessages)) {
            log.info("Received {} OrderPaymentOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream()
                            .map(outboxMessage -> outboxMessage.getId().toString()).collect(Collectors.joining(",")));

            outboxMessages.forEach(outboxMessage -> productMessagePublisher.publish(outboxMessage, this::updateOutboxStatus));

            log.info("{} OrderPaymentOutboxMessage sent to message bus!", outboxMessages.size());
        }

    }

    private void updateOutboxStatus(ProductOutboxMessage productOutboxMessage, OutboxStatus outboxStatus) {
        productOutboxMessage.setOutboxStatus(outboxStatus);
        paymentOutboxHelper.save(productOutboxMessage);
        log.info("OrderPaymentOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
