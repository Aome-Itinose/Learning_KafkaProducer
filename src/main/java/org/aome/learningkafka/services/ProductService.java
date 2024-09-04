package org.aome.learningkafka.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aome.learningkafka.services.dto.ProductDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final KafkaTemplate<String, ProductDto> kafkaTemplate;

    public String createAndReturnId(ProductDto product) {
        //Saving product to db
        String productId = UUID.randomUUID().toString();
        try {
                SendResult<String, ProductDto> future = kafkaTemplate.send("product-edited-events-topic", productId, product).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("Product is sent {}", productId);

        return productId;
    }
}
