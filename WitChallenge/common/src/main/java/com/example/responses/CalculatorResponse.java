package com.example.responses;

import java.math.BigDecimal;

public class CalculatorResponse {

    private BigDecimal result;

    public CalculatorResponse() {
    }

    public CalculatorResponse(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
