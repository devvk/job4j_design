package ru.job4j.ood.bank.repository;

import ru.job4j.ood.bank.model.BankAccount;

import java.util.Collection;
import java.util.Optional;

public interface AccountsRepository {

    Collection<BankAccount> findAll();

    void register(BankAccount account);

    Optional<BankAccount> findById(String id);
}
