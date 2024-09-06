package org.aome.learningkafka.config;

import org.aome.learningkafka.services.dto.ProductDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
public class KafkaConfig {
    /**
     * Конфигурации можно вводить здесь в классе конфига с помощью бинов
     * или можно просто оставить с такими именами в application.yml
     */
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${spring.kafka.producer.retries}")
    private int retries;
    @Value("${spring.kafka.producer.properties.retry.backoff.ms}")
    private int retryBackoffMs;
    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    private int deliveryTimeoutMs;
    @Value("${spring.kafka.producer.properties.linger.ms}")
    private int lingerMs;
    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    private int requestTimeoutMs;
    @Value("${spring.kafka.producer.acks}")
    private String acks;

    Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, retryBackoffMs);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);

        return props;
    }

    @Bean
    ProducerFactory<String, ProductDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    KafkaTemplate<String, ProductDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    @Bean
    NewTopic newTopic(){
        return TopicBuilder
                .name("product-edited-events-topic")
                .partitions(3)
                .replicas(3)
                .config("min.insync.replicas", "2")
                .build();
    }
}
