package ru.job4j.ood.bank.service.result;

public final class TransferResult {

    private final TransferStatus status;
    private final String message;

    private TransferResult(TransferStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static TransferResult success() {
        return new TransferResult(TransferStatus.SUCCESS, null);
    }

    public static TransferResult failure(TransferStatus status, String message) {
        return new TransferResult(status, message);
    }

    public boolean isSuccess() {
        return status == TransferStatus.SUCCESS;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

