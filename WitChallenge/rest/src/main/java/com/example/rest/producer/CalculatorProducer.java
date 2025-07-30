package com.example.rest.producer;

import com.example.requests.CalculatorRequest;
import com.example.responses.CalculatorResponse;
import com.example.rest.controller.CalculatorController;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class CalculatorProducer {
    @Value("${kafka.topics.calculator.requests}")
    private String requestTopic;

    @Value("${kafka.topics.calculator.replies}")
    private String replyTopic;

    private final ReplyingKafkaTemplate<String, CalculatorRequest, CalculatorResponse> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CalculatorProducer.class);

    @Autowired
    public CalculatorProducer(ReplyingKafkaTemplate<String, CalculatorRequest, CalculatorResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CalculatorResponse sendCalcRequest(CalculatorRequest request) throws Exception {
        ProducerRecord<String, CalculatorRequest> record = new ProducerRecord<>(requestTopic, request);
        record.headers().add(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes());
        record.headers().add("requestId", MDC.get("requestId").getBytes());

        RequestReplyFuture<String, CalculatorRequest, CalculatorResponse> future =
                kafkaTemplate.sendAndReceive(record);
        logger.info("[{}] Sending calculation request...", MDC.get("requestId"));
        return future.get(10, TimeUnit.SECONDS).value();
    }
}
