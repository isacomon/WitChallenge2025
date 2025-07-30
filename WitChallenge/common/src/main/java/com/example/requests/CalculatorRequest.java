package com.example.requests;

import java.math.BigDecimal;

public class CalculatorRequest {

    private String operation;
    private BigDecimal value1;
    private BigDecimal value2;

    public CalculatorRequest() {
    }

    public CalculatorRequest(String operation, BigDecimal value1, BigDecimal value2) {
        this.operation = operation;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getOperation() {
        return operation;
    }

    public BigDecimal getValue1() {
        return value1;
    }

    public BigDecimal getValue2() {
        return value2;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setValue1(BigDecimal value1) {
        this.value1 = value1;
    }

    public void setValue2(BigDecimal value2) {
        this.value2 = value2;
    }
}
