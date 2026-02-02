package ru.job4j.ood.bank.model;

import ru.job4j.ood.bank.calc.Calculator;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BankAccount {

    private final String id;
    private BigDecimal balance;
    private final Calculator calculator;

    public BankAccount(String id, BigDecimal balance, Calculator calculator) {
        this.id = Objects.requireNonNull(id);
        this.balance = Objects.requireNonNull(balance);
        this.calculator = Objects.requireNonNull(calculator);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Calculator getCommissionCalculator() {
        return calculator;
    }

    public void deposit(BigDecimal amount) {
        validatePositive(amount);
        balance = balance.add(amount);
    }

    public boolean withdraw(BigDecimal amount) {
        validatePositive(amount);
        if (balance.compareTo(amount) < 0) {
            return false;
        }
        balance = balance.subtract(amount);
        return true;
    }

    private static void validatePositive(BigDecimal amount) {
        Objects.requireNonNull(amount);
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }
    }
}
