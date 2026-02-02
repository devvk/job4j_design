package ru.job4j.ood.bank.model;

import ru.job4j.ood.bank.calc.CommissionCalculator;

import java.math.BigDecimal;

public class VipBankAccount extends BankAccount {

    public static final BigDecimal TRANSFER_FEE_RATE = new BigDecimal("0.0");

    public VipBankAccount(String id, BigDecimal balance) {
        super(id, balance, new CommissionCalculator(TRANSFER_FEE_RATE));
    }
}
