package ru.job4j.ood.bank.service;

import ru.job4j.ood.bank.model.BankAccount;
import ru.job4j.ood.bank.repository.AccountsRepository;

import java.util.Optional;

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

        Optional<BankAccount> from = repository.findById(fromId);
        Optional<BankAccount> to = repository.findById(toId);

        if (from.isEmpty() || to.isEmpty()) {
            return false;
        }

        double fromBalance = from.get().getBalance();
        double toBalance = to.get().getBalance();

        double fee = from.get().getCommissionCalculator().calculate(amount);
        double total = amount + fee;
        if (fromBalance < total) {
            return false;
        }

        from.get().setBalance(fromBalance - total);
        to.get().setBalance(toBalance + amount);
        return true;
    }
}

