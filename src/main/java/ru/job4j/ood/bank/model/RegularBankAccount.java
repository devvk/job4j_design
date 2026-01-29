package ru.job4j.ood.bank.model;

import ru.job4j.ood.bank.calc.CommissionCalculator;

public class RegularBankAccount extends BankAccount {

    public static final double TRANSFER_FEE_RATE = 0.015;

    public RegularBankAccount(String id, double balance) {
        super(id, balance, new CommissionCalculator(TRANSFER_FEE_RATE));
    }
}
