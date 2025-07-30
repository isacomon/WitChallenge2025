package com.example.rest.producer;

import com.example.requests.CalculatorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class CalculatorProducer {
    @Value("${kafka.topics.calculator.requests}")
    private String topic;

    private final KafkaTemplate<String, CalculatorRequest> kafkaTemplate;

    @Autowired
    public CalculatorProducer(KafkaTemplate<String, CalculatorRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCalcRequest(CalculatorRequest request) {
        kafkaTemplate.send(topic, request);
    }
}
