package com.example.rest.controller;

import com.example.rest.producer.CalculatorProducer;
import com.example.requests.CalculatorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CalculatorController {
    private final CalculatorProducer producer;

    public CalculatorController(CalculatorProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/sum")
    public void addition(@RequestParam int a, @RequestParam int b) {
        producer.sendCalcRequest(new CalculatorRequest("ADD", a, b));
    }

    @GetMapping("/mult")
    public void multiplication(@RequestParam int a, @RequestParam int b) {
        producer.sendCalcRequest(new CalculatorRequest("MULT", a, b));
    }

    @GetMapping("/sub")
    public void subtraction(@RequestParam int a, @RequestParam int b) {
        producer.sendCalcRequest(new CalculatorRequest("SUB", a, b));
    }

    @GetMapping("/div")
    public void division(@RequestParam int a, @RequestParam int b) {
        producer.sendCalcRequest(new CalculatorRequest("DIV", a, b));
    }
}
