package ru.job4j.ood.bank.service.result;

public enum TransferStatus {
    SUCCESS,
    INVALID_AMOUNT,
    INVALID_ACCOUNT_ID,
    SAME_ACCOUNT,
    ACCOUNT_NOT_FOUND,
    INSUFFICIENT_FUNDS,
    INTERNAL_ERROR
}
