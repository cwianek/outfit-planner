package com.outfit.planner.system.outfit.service.messaging;

import com.outfit.planner.system.kafka.consumer.KafkaConsumer;
import com.outfit.planner.system.kafka.product.avro.model.ProductAvroModel;
import com.outfit.planner.system.outfit.service.messaging.outbox.ProductOutboxHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductKafkaListener implements KafkaConsumer<ProductAvroModel> {


    private final ProductOutboxHelper productOutboxHelper;

    public ProductKafkaListener(ProductOutboxHelper productOutboxHelper) {
        this.productOutboxHelper = productOutboxHelper;
    }


    @Override
    @KafkaListener(id = "${kafka-consumer-config.product-group-id}", topics = "${outfit-service.product-topic-name}")
    public void receive(@Payload List<ProductAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of customer create messages received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());


        messages.forEach(productOutboxHelper::process);
    }
}
