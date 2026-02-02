package ru.job4j.ood.bank.calc;

import java.math.BigDecimal;

public class CommissionCalculator implements Calculator {
    private final BigDecimal rate;

    public CommissionCalculator(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(rate);
    }
}
