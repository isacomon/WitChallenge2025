package com.example.calculator.consumer;

import com.example.requests.CalculatorRequest;
import com.example.responses.CalculatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CalculatorConsumer {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorConsumer.class);

    @KafkaListener(topics = "${kafka.topics.calculator.requests}", groupId = "calculator-group")
    @SendTo
    public CalculatorResponse listen(@Payload CalculatorRequest request, @Header(value = "requestId", required = false) String requestId) {
        BigDecimal result = BigDecimal.ZERO;
        logger.info("[{}] Received Calculator Request", requestId);
        switch (request.getOperation()) {
            case "ADD":
                result = request.getValue1().add(request.getValue2());
                break;
            case "MULT":
                result = request.getValue1().multiply(request.getValue2());
                break;
            case "SUB":
                result = request.getValue1().subtract(request.getValue2());
                break;
            case "DIV":
                result = request.getValue1().divide(request.getValue2(), 10, RoundingMode.HALF_UP);
                break;
            default:
                logger.error("[{}] Unsupported operation: {}", requestId, request.getOperation());
        }
        logger.info("[{}] Result is: {}", requestId, result);
        return new CalculatorResponse(result);
    }
}
