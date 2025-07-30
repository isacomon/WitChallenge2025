package com.example.requests;

public class CalculatorRequest {

    private String operation;
    private int value1;
    private int value2;

    public CalculatorRequest() {
    }

    public CalculatorRequest(String operation, int value1, int value2) {
        this.operation = operation;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getOperation() {
        return operation;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }
}
