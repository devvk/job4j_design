package ru.job4j.ood.bank;

import ru.job4j.ood.bank.io.*;
import ru.job4j.ood.bank.model.RegularBankAccount;
import ru.job4j.ood.bank.model.VipBankAccount;
import ru.job4j.ood.bank.repository.AccountsRepository;
import ru.job4j.ood.bank.repository.InMemoryAccountsRepository;
import ru.job4j.ood.bank.service.BankService;

public class BankApp {

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
        if (bank.transfer("vip", "regular", 300.0)) {
            System.out.println("Перевод успешно выполнен.");
        } else {
            System.out.println("Не удалось выполнить перевод!");
        }

        // Выполняем перевод: Regular отправляет 300 VIP
        if (bank.transfer("regular", "vip", 300.0)) {
            System.out.println("Перевод успешно выполнен.");
        } else {
            System.out.println("Не удалось выполнить перевод!");
        }

        // Смотрим балансы
        printer.print();
        exporter.export("accounts.csv");
        sender.send("Кредит 0% только сегодня!");
    }
}
