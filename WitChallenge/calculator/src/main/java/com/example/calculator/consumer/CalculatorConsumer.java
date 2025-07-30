package com.example.calculator.consumer;

import com.example.requests.CalculatorRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CalculatorConsumer {
    @KafkaListener(topics = "${kafka.topics.calculator.requests}", groupId = "calculator-group")
    public void listen(CalculatorRequest request) {
        try {
            int result = 0;
            switch (request.getOperation()) {
                case "ADD":
                    result = request.getValue1() + request.getValue2();
                    break;
                case "MUL":
                    result = request.getValue1() * request.getValue2();
                    break;
                default:
                    System.err.println("Unsupported operation: " + request.getOperation());
            }
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Invalid expression: " + request.getOperation());
        }
    }
}
