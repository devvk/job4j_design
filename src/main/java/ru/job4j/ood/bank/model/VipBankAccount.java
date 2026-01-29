package ru.job4j.ood.bank.model;

import ru.job4j.ood.bank.calc.CommissionCalculator;

public class VipBankAccount extends BankAccount {

    public static final double TRANSFER_FEE_RATE = 0.0;

    public VipBankAccount(String id, double balance) {
        super(id, balance, new CommissionCalculator(TRANSFER_FEE_RATE));
    }
}
