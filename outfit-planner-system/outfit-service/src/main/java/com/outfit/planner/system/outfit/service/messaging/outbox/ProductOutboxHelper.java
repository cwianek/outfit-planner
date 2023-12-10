package com.outfit.planner.system.outfit.service.messaging.outbox;

import com.outfit.planner.system.kafka.product.avro.model.ProductAvroModel;
import com.outfit.planner.system.outbox.OutboxStatus;
import com.outfit.planner.system.outfit.service.dataaccess.outbox.entity.ProductOutboxMessageEntity;
import com.outfit.planner.system.outfit.service.dataaccess.outbox.repository.ProductOutboxMessageRepository;
import com.outfit.planner.system.outfit.service.mapper.ProductMessagningDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ProductOutboxHelper {

    private final ProductMessagningDataMapper productMessagningDataMapper;
    private final ProductOutboxMessageRepository productOutboxMessageRepository;

    public ProductOutboxHelper(ProductMessagningDataMapper productMessagningDataMapper, ProductOutboxMessageRepository productOutboxMessageRepository) {
        this.productMessagningDataMapper = productMessagningDataMapper;
        this.productOutboxMessageRepository = productOutboxMessageRepository;
    }

    @Transactional
    public void process(ProductAvroModel productAvroModel){
        String messageId = productAvroModel.getMessageId();
        Optional<ProductOutboxMessageEntity> existingEvent = productOutboxMessageRepository.findById(messageId);
        if (existingEvent.isPresent()) {
            log.info("An outbox message with saga id: {} is already processed!", existingEvent.get().getId());
            return;
        }

        ProductOutboxMessageEntity productOutboxMessage = productMessagningDataMapper.productAvroModelToProductOutboxMessageEntity(productAvroModel);
        productOutboxMessage.setOutboxStatus(OutboxStatus.STARTED);
        productOutboxMessageRepository.save(productOutboxMessage);
    }

    @Transactional(readOnly = true)
    public List<ProductOutboxMessageEntity> getProductOutboxMessageByOutboxStatus(OutboxStatus outboxStatus){
        return productOutboxMessageRepository.findByOutboxStatus(outboxStatus);
    }

    @Transactional()
    public ProductOutboxMessageEntity save(ProductOutboxMessageEntity messageEntity){
        return productOutboxMessageRepository.save(messageEntity);
    }

}
