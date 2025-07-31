package com.example.calculator.consumer;

import com.example.requests.CalculatorRequest;
import com.example.responses.CalculatorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorConsumerTests {
    private CalculatorConsumer consumer;

    @BeforeEach
    void setup() {
        consumer = new CalculatorConsumer();
    }

    @Test
    void testAddition() {
        CalculatorRequest request = new CalculatorRequest("ADD", BigDecimal.valueOf(4), BigDecimal.valueOf(6));
        CalculatorResponse response = consumer.listen(request, "test-request-123");
        assertEquals(BigDecimal.valueOf(10), response.getResult());
    }

    @Test
    void testMultiplication() {
        CalculatorRequest request = new CalculatorRequest("MULT", BigDecimal.valueOf(2), BigDecimal.valueOf(4));
        CalculatorResponse response = consumer.listen(request, "test-request-123");
        assertEquals(BigDecimal.valueOf(8), response.getResult());
    }

    @Test
    void testSubtraction() {
        CalculatorRequest request = new CalculatorRequest("SUB", BigDecimal.valueOf(9), BigDecimal.valueOf(3));
        CalculatorResponse response = consumer.listen(request, "test-request-123");
        assertEquals(BigDecimal.valueOf(6), response.getResult());
    }

    @Test
    void testDivision() {
        CalculatorRequest request = new CalculatorRequest("DIV", BigDecimal.valueOf(10), BigDecimal.valueOf(4));
        CalculatorResponse response = consumer.listen(request, "test-request-123");
        assertEquals(BigDecimal.valueOf(2.5).setScale(10, RoundingMode.HALF_UP), response.getResult());
    }

    @Test
    void testUnsupportedOperation() {
        CalculatorRequest request = new CalculatorRequest("error", BigDecimal.valueOf(10), BigDecimal.valueOf(4));
        CalculatorResponse response = consumer.listen(request,"test-request-123");
        assertEquals(BigDecimal.ZERO, response.getResult());
    }
}
