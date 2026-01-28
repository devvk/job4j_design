package ru.job4j.ood;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BankApp {

    public static abstract class BankAccount {
        private final String id;
        private double balance;

        public BankAccount(String id, double balance) {
            this.id = id;
            this.balance = balance;
        }

        public void deposit(double amount) {
            balance += amount;
        }

        public String getId() {
            return id;
        }

        public double getBalance() {
            return balance;
        }

        public void withdraw(double amount) {
            balance -= amount;
        }

        protected abstract double getTransferFee();

        public double fee(double amount) {
            return amount * getTransferFee();
        }
    }

    public static class RegularBankAccount extends BankAccount {

        public static final double TRANSFER_FEE_RATE = 0.015;

        public RegularBankAccount(String id, double balance) {
            super(id, balance);
        }

        @Override
        public double getTransferFee() {
            return TRANSFER_FEE_RATE;
        }
    }

    public static class VipBankAccount extends BankAccount {

        public static final double TRANSFER_FEE_RATE = 0.0;

        public VipBankAccount(String id, double balance) {
            super(id, balance);
        }

        @Override
        public double getTransferFee() {
            return TRANSFER_FEE_RATE;
        }
    }

    public interface AccountsRepository {

        Collection<BankAccount> findAll();

        void register(BankAccount account);

        BankAccount findById(String id);
    }

    public static class InMemoryAccountsRepository implements AccountsRepository {

        private final Map<String, BankAccount> accounts = new HashMap<>();

        @Override
        public Collection<BankAccount> findAll() {
            return accounts.values();
        }

        @Override
        public void register(BankAccount account) {
            accounts.put(account.id, account);
        }

        @Override
        public BankAccount findById(String id) {
            return accounts.get(id);
        }
    }

    public static class BankService {

        private final AccountsRepository repository;

        public BankService(AccountsRepository repository) {
            this.repository = repository;
        }

        public void registerAccount(BankAccount account) {
            repository.register(account);
        }

        public Transfer transfer(String fromId, String toId, double amount) {
            BankAccount from = repository.findById(fromId);
            BankAccount to = repository.findById(toId);

            if (from == null || to == null) {
                throw new IllegalArgumentException("Аккаунт не найден!");
            }

            double fee = from.fee(amount);
            double total = amount + fee;
            if (from.getBalance() < total) {
                throw new IllegalArgumentException("Недостаточно средств на счёте!");
            }

            from.withdraw(total);
            to.deposit(amount);
            return new Transfer(fromId, toId, amount, fee);
        }
    }

    public record Transfer(String fromId, String toId, double amount, double fee) {
    }

    public interface AccountsPrinter {
        void print();
    }

    public static class ConsoleAccountPrinter implements AccountsPrinter {

        private final AccountsRepository repository;

        public ConsoleAccountPrinter(AccountsRepository repository) {
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

    public interface AccountsExporter {
        void export(String fileName);
    }

    public static class CsvAccountsExporter implements AccountsExporter {

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

    public interface PromotionalSender {
        void send(String message);
    }

    public static class EmailPromotionalSender implements PromotionalSender {
        @Override
        public void send(String message) {
            System.out.println("Рассылка рекламных писем с текстом: " + message);

        }
    }

    public static void main(String[] args) {
        AccountsRepository repository = new InMemoryAccountsRepository();
        BankService bank = new BankService(repository);

        AccountsPrinter printer = new ConsoleAccountPrinter(repository);
        AccountsExporter exporter = new CsvAccountsExporter(repository);
        PromotionalSender sender = new EmailPromotionalSender();

        // Регистрируем обычный и VIP аккаунты
        bank.registerAccount(new RegularBankAccount("regular", 500.0));
        bank.registerAccount(new VipBankAccount("vip", 1000.0));

        // Выполняем перевод: VIP отправляет 300 обычному
        try {
            Transfer t = bank.transfer("vip", "regular", 300.0);
            System.out.printf("Перевод %s от: %s к: %s. Комиссия: %s\n",
                    t.amount(), t.fromId(), t.toId(), t.fee());
            System.out.println("SMS: перевод успешно выполнен.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        // Выполняем перевод: Regular отправляет 300 VIP
        try {
            Transfer t = bank.transfer("regular", "vip", 300.0);
            System.out.printf("Перевод %s от: %s к: %s. Комиссия: %s\n",
                    t.amount(), t.fromId(), t.toId(), t.fee());
            System.out.println("SMS: перевод успешно выполнен.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        // Смотрим балансы
        printer.print();
        exporter.export("accounts.csv");
        sender.send("Кредит 0% только сегодня!");
    }
}
