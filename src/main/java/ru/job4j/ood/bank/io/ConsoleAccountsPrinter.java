package ru.job4j.ood.bank.io;

import ru.job4j.ood.bank.model.BankAccount;
import ru.job4j.ood.bank.repository.AccountsRepository;

public class ConsoleAccountsPrinter implements AccountsPrinter {

    private final AccountsRepository repository;

    public ConsoleAccountsPrinter(AccountsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void print() {
        System.out.println("Текущие балансы:");
        for (BankAccount account : repository.findAll()) {
            System.out.println(account.getId() + " -> " + account.getBalance());
        }
    }
}
