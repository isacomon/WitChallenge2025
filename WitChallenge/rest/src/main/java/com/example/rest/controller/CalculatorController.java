package com.example.rest.controller;

import com.example.responses.CalculatorResponse;
import com.example.rest.producer.CalculatorProducer;
import com.example.requests.CalculatorRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

@RestController
@RequestMapping("/")
public class CalculatorController {
    private final CalculatorProducer producer;
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    public CalculatorController(CalculatorProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/sum")
    public ResponseEntity<CalculatorResponse> addition(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        try {
            logger.info("[{}] Starting addition for {} and {}", MDC.get("requestId"), a, b);
            return new ResponseEntity<>(producer.sendCalcRequest(new CalculatorRequest("ADD", a, b)), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[{}] Error during addition for {} and {}: {}", MDC.get("requestId"), a, b, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mult")
    public ResponseEntity<BigDecimal> multiplication(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        try {
            logger.info("[{}] Starting multiplication for {} and {}", MDC.get("requestId"), a, b);
            return new ResponseEntity<>(producer.sendCalcRequest(new CalculatorRequest("MULT", a, b)).getResult(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[{}] Error during multiplication for {} and {}: {}", MDC.get("requestId"), a, b, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/sub")
    public ResponseEntity<BigDecimal> subtraction(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        try {
            logger.info("[{}] Starting subtraction for {} and {}", MDC.get("requestId"), a, b);
            return new ResponseEntity<>(producer.sendCalcRequest(new CalculatorRequest("SUB", a, b)).getResult(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[{}] Error during subtraction for {} and {}: {}", MDC.get("requestId"), a, b, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/div")
    public ResponseEntity<BigDecimal> division(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        try {
            logger.info("[{}] Starting division for {} and {}", MDC.get("requestId"), a, b);
            return new ResponseEntity<>(producer.sendCalcRequest(new CalculatorRequest("DIV", a, b)).getResult(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[{}] Error during division for {} and {}: {}", MDC.get("requestId"), a, b, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
