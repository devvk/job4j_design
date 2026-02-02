package ru.job4j.ood.bank.repository;

import ru.job4j.ood.bank.model.BankAccount;

import java.util.*;

public class InMemoryAccountsRepository implements AccountsRepository {

    private final Map<String, BankAccount> accounts = new HashMap<>();

    @Override
    public Collection<BankAccount> findAll() {
        return List.copyOf(accounts.values());
    }

    @Override
    public void register(BankAccount account) {
        accounts.put(account.getId(), account);
    }

    @Override
    public Optional<BankAccount> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }
}
