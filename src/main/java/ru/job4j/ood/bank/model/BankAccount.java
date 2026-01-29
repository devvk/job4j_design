package ru.job4j.ood.bank.model;

import ru.job4j.ood.bank.calc.CommissionCalculator;

public abstract class BankAccount {

    private final String id;

    private double balance;

    private final CommissionCalculator commissionCalculator;

    public BankAccount(String id, double balance, CommissionCalculator commissionCalculator) {
        this.id = id;
        this.balance = balance;
        this.commissionCalculator = commissionCalculator;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public CommissionCalculator getCommissionCalculator() {
        return commissionCalculator;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
