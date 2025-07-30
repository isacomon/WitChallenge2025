package com.example.rest.config;

import com.example.requests.CalculatorRequest;
import com.example.responses.CalculatorResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.topics.calculator.replies}")
    private String replyTopic;

    @Value("${kafka.groups.calculator}")
    private String calculatorGroup;

    @Bean
    public ProducerFactory<String, CalculatorRequest> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public ConsumerFactory<String, CalculatorResponse> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, calculatorGroup);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(CalculatorResponse.class));
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, CalculatorResponse> repliesContainer(
            ConsumerFactory<String, CalculatorResponse> cf) {
        ContainerProperties containerProperties = new ContainerProperties(replyTopic);
        return new ConcurrentMessageListenerContainer<>(cf, containerProperties);
    }

    @Bean
    public ReplyingKafkaTemplate<String, CalculatorRequest, CalculatorResponse> replyingKafkaTemplate(
            ProducerFactory<String, CalculatorRequest> pf,
            ConcurrentMessageListenerContainer<String, CalculatorResponse> repliesContainer) {
        return new ReplyingKafkaTemplate<>(pf, repliesContainer);
    }
}
