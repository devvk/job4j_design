package ru.job4j.ood.bank;

import ru.job4j.ood.bank.io.*;
import ru.job4j.ood.bank.model.RegularBankAccount;
import ru.job4j.ood.bank.model.VipBankAccount;
import ru.job4j.ood.bank.repository.AccountsRepository;
import ru.job4j.ood.bank.repository.InMemoryAccountsRepository;
import ru.job4j.ood.bank.service.BankService;
import ru.job4j.ood.bank.service.result.TransferResult;

import java.math.BigDecimal;

public class BankApp {

    public static void main(String[] args) {
        AccountsRepository repository = new InMemoryAccountsRepository();
        BankService bank = new BankService(repository);

        AccountsPrinter printer = new ConsoleAccountsPrinter(repository);
        AccountsExporter exporter = new CsvAccountsExporter(repository);
        PromotionalSender sender = new EmailPromotionalSender();

        // Регистрируем обычный и VIP аккаунты
        bank.registerAccount(new RegularBankAccount("regular", new BigDecimal("500")));
        bank.registerAccount(new VipBankAccount("vip", new BigDecimal("1000")));

        // Выполняем перевод: VIP отправляет 300 обычному
        TransferResult result = bank.transfer("vip", "regular", new BigDecimal("300"));
        if (result.isSuccess()) {
            System.out.println("Перевод успешно выполнен!");
        } else {
            System.out.println("Не удалось выполнить перевод: " + result.getStatus() + " - " + result.getMessage());
        }

        // Выполняем перевод: Regular отправляет 300 VIP
        TransferResult result2 = bank.transfer("regular", "vip", new BigDecimal("300"));
        if (result2.isSuccess()) {
            System.out.println("Перевод успешно выполнен!");
        } else {
            System.out.println("Не удалось выполнить перевод: " + result2.getStatus() + " - " + result2.getMessage());
        }

        // Смотрим балансы
        printer.print();
        exporter.export("accounts.csv");
        sender.send("Кредит 0% только сегодня!");
    }
}
