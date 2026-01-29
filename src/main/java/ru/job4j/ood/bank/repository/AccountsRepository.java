package ru.job4j.ood.bank.repository;

import ru.job4j.ood.bank.model.BankAccount;

import java.util.Collection;

public interface AccountsRepository {

    Collection<BankAccount> findAll();

    void register(BankAccount account);

    BankAccount findById(String id);
}
