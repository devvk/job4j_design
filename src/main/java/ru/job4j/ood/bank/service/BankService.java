package ru.job4j.ood.bank.service;

import ru.job4j.ood.bank.model.BankAccount;
import ru.job4j.ood.bank.repository.AccountsRepository;
import ru.job4j.ood.bank.service.result.TransferResult;
import ru.job4j.ood.bank.service.result.TransferStatus;

import java.math.BigDecimal;
import java.util.Optional;

public class BankService {

    private final AccountsRepository repository;

    public BankService(AccountsRepository repository) {
        this.repository = repository;
    }

    public void registerAccount(BankAccount account) {
        repository.register(account);
    }

    public TransferResult transfer(String fromId, String toId, BigDecimal amount) {
        if (fromId == null || fromId.isBlank() || toId == null || toId.isBlank()) {
            return TransferResult.failure(TransferStatus.INVALID_ACCOUNT_ID, "Invalid account id");
        }
        if (fromId.equals(toId)) {
            return TransferResult.failure(TransferStatus.SAME_ACCOUNT, "Source and target accounts are the same");
        }
        if (amount == null || amount.signum() <= 0) {
            return TransferResult.failure(TransferStatus.INVALID_AMOUNT, "Invalid amount");
        }

        Optional<BankAccount> fromOpt = repository.findById(fromId);
        Optional<BankAccount> toOpt = repository.findById(toId);
        if (fromOpt.isEmpty() || toOpt.isEmpty()) {
            return TransferResult.failure(TransferStatus.ACCOUNT_NOT_FOUND, "Account not found");
        }

        BankAccount from = fromOpt.get();
        BankAccount to = toOpt.get();

        BigDecimal fee = from.getCommissionCalculator().calculate(amount);
        BigDecimal total = amount.add(fee);

        if (!from.withdraw(total)) {
            return TransferResult.failure(TransferStatus.INSUFFICIENT_FUNDS, "Insufficient funds");
        }

        to.deposit(amount);
        return TransferResult.success();
    }
}

