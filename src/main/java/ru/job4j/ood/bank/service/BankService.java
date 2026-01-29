package ru.job4j.ood.bank.service;

import ru.job4j.ood.bank.model.BankAccount;
import ru.job4j.ood.bank.repository.AccountsRepository;

public class BankService {

    private final AccountsRepository repository;

    public BankService(AccountsRepository repository) {
        this.repository = repository;
    }

    public void registerAccount(BankAccount account) {
        repository.register(account);
    }

    public boolean transfer(String fromId, String toId, double amount) {
        if (amount <= 0) {
            return false;
        }

        BankAccount from = repository.findById(fromId);
        BankAccount to = repository.findById(toId);

        if (from == null || to == null) {
            return false;
        }

        double fromBalance = from.getBalance();
        double toBalance = to.getBalance();

        double fee = from.getCommissionCalculator().calculate(amount);
        double total = amount + fee;
        if (fromBalance < total) {
            return false;
        }

        from.setBalance(fromBalance - total);
        to.setBalance(toBalance + amount);
        return true;
    }
}

