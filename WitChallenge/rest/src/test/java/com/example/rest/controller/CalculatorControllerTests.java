package com.example.rest.controller;

import com.example.responses.CalculatorResponse;
import com.example.rest.producer.CalculatorProducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculatorControllerTests {
    @Mock
    private CalculatorProducer producer;

    @InjectMocks
    private CalculatorController controller;

    @BeforeEach
    void setup() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calculatorControllerTests_HappyPath_Addition() throws Exception {
        CalculatorResponse response = new CalculatorResponse(BigDecimal.valueOf(5));
        when(producer.sendCalcRequest(any())).thenReturn(response);

        ResponseEntity<CalculatorResponse> result = controller.addition(BigDecimal.valueOf(2), BigDecimal.valueOf(3));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(BigDecimal.valueOf(5), result.getBody().getResult());
    }

    @Test
    void calculatorControllerTests_Exception_Addition() throws Exception {
        when(producer.sendCalcRequest(any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CalculatorResponse> result = controller.addition(BigDecimal.valueOf(2), BigDecimal.valueOf(3));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void calculatorControllerTests_HappyPath_Subtraction() throws Exception {
        CalculatorResponse response = new CalculatorResponse(BigDecimal.valueOf(1));
        when(producer.sendCalcRequest(any())).thenReturn(response);

        ResponseEntity<CalculatorResponse> result = controller.subtraction(BigDecimal.valueOf(3), BigDecimal.valueOf(2));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(BigDecimal.valueOf(1), result.getBody().getResult());
    }

    @Test
    void calculatorControllerTests_Exception_Subtraction() throws Exception {
        when(producer.sendCalcRequest(any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CalculatorResponse> result = controller.subtraction(BigDecimal.valueOf(2), BigDecimal.valueOf(3));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void calculatorControllerTests_HappyPath_Multiplication() throws Exception {
        CalculatorResponse response = new CalculatorResponse(BigDecimal.valueOf(6));
        when(producer.sendCalcRequest(any())).thenReturn(response);

        ResponseEntity<CalculatorResponse> result = controller.multiplication(BigDecimal.valueOf(2), BigDecimal.valueOf(3));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(BigDecimal.valueOf(6), result.getBody().getResult());
    }

    @Test
    void calculatorControllerTests_Exception_Multiplication() throws Exception {
        when(producer.sendCalcRequest(any())).thenThrow(new RuntimeException("Kafka failure"));

        ResponseEntity<CalculatorResponse> result = controller.multiplication(BigDecimal.valueOf(2), BigDecimal.valueOf(3));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void calculatorControllerTests_HappyPath_Division() throws Exception {
        CalculatorResponse response = new CalculatorResponse(BigDecimal.valueOf(2));
        when(producer.sendCalcRequest(any())).thenReturn(response);

        ResponseEntity<CalculatorResponse> result = controller.division(BigDecimal.valueOf(6), BigDecimal.valueOf(3));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(BigDecimal.valueOf(2), result.getBody().getResult());
    }

    @Test
    void calculatorControllerTests_Exception_Division() throws Exception {
        when(producer.sendCalcRequest(any())).thenThrow(new RuntimeException("Kafka error"));

        ResponseEntity<CalculatorResponse> result = controller.division(BigDecimal.valueOf(6), BigDecimal.valueOf(3));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}

