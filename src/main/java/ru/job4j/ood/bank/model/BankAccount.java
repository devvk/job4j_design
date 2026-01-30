package ru.job4j.ood.bank.model;

import ru.job4j.ood.bank.calc.Calculator;

public abstract class BankAccount {

    private final String id;

    private double balance;

    private final Calculator calculator;

    public BankAccount(String id, double balance, Calculator calculator) {
        this.id = id;
        this.balance = balance;
        this.calculator = calculator;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public Calculator getCommissionCalculator() {
        return calculator;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
