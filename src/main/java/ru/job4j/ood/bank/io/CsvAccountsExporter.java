package ru.job4j.ood.bank.io;

import ru.job4j.ood.bank.model.BankAccount;
import ru.job4j.ood.bank.repository.AccountsRepository;

public class CsvAccountsExporter implements AccountsExporter {

    private final AccountsRepository repository;

    public CsvAccountsExporter(AccountsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void export(String fileName) {
        System.out.println("Экспорт аккаунтов в CSV-файл: " + fileName);
        for (BankAccount account : repository.findAll()) {
            System.out.println(account.getId() + ";" + account.getBalance());
        }
    }
}
