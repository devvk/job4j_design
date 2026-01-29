package ru.job4j.ood.bank.calc;

public class CommissionCalculator {
    private final double rate;

    public CommissionCalculator(double rate) {
        this.rate = rate;
    }

    public double calculate(double amount) {
        return amount * rate;
    }
}
