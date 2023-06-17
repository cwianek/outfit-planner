package com.outfit.planner.system.product.service.messaging.publisher.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit.planner.system.domain.config.ProductServiceConfigData;
import com.outfit.planner.system.domain.outbox.model.ProductOutboxMessage;
import com.outfit.planner.system.kafka.producer.service.KafkaProducer;
import com.outfit.planner.system.kafka.product.avro.model.ProductAvroModel;
import com.outfit.planner.system.outbox.OutboxStatus;
import com.outfit.planner.system.product.service.domain.event.ProductCreatedEvent;
import com.outfit.planner.system.domain.ports.output.message.publisher.ProductMessagePublisher;
import com.outfit.planner.system.product.service.messaging.mapper.ProductMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class ProductCreatedEventKafkaPublisher implements ProductMessagePublisher {

    private final ProductMessagingDataMapper productMessagingDataMapper;

    private final KafkaProducer<String, ProductAvroModel> kafkaProducer;

    private final ProductServiceConfigData productServiceConfigData;

    private final ObjectMapper objectMapper;

    public ProductCreatedEventKafkaPublisher(ProductMessagingDataMapper productMessagingDataMapper, KafkaProducer<String, ProductAvroModel> kafkaProducer, ProductServiceConfigData productServiceConfigData, ObjectMapper objectMapper) {
        this.productMessagingDataMapper = productMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.productServiceConfigData = productServiceConfigData;
        this.objectMapper = objectMapper;
    }


    public <T> T getProductEventPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            log.error("Could not read {} object!", outputType.getName(), e);
            throw new RuntimeException("Could not read " + outputType.getName() + " object!", e);
        }
    }

    private <U> ListenableFutureCallback<SendResult<String, ProductAvroModel>>
    getCallback(String topicName, ProductAvroModel message, U outboxMessage, BiConsumer<U, OutboxStatus> outboxCallback) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
                outboxCallback.accept(outboxMessage, OutboxStatus.FAILED);
            }

            @Override
            public void onSuccess(SendResult<String, ProductAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
                outboxCallback.accept(outboxMessage, OutboxStatus.COMPLETED);
            }
        };
    }

    @Override
    public void publish(ProductOutboxMessage productOutboxMessage, BiConsumer<ProductOutboxMessage, OutboxStatus> outboxCallback) {
        ProductCreatedEvent productCreatedEvent =
                getProductEventPayload(productOutboxMessage.getPayload(),
                        ProductCreatedEvent.class);
        UUID messageId = productOutboxMessage.getId();

        log.info("Received ProductCreatedEvent for product id: {}",
                productCreatedEvent.getProduct().getId());
        try {
            ProductAvroModel productAvroModel = productMessagingDataMapper
                    .productCreatedEventToProductAvroModel(productCreatedEvent, messageId.toString());

            kafkaProducer.send(productServiceConfigData.getProductTopicName(), productAvroModel.getId(),
                    productAvroModel,
                    getCallback(productServiceConfigData.getProductTopicName(), productAvroModel, productOutboxMessage, outboxCallback));

            log.info("ProductCreatedEvent sent to kafka for product id: {}",
                    productAvroModel.getId());
        } catch (Exception e) {
            log.error("Error while sending ProductCreatedEvent to kafka for product id: {}," +
                    " error: {}", productCreatedEvent.getProduct().getId(), e.getMessage());
        }
    }
}
