package ru.job4j.ood.bank.io;

public class EmailPromotionalSender implements PromotionalSender {
    @Override
    public void send(String message) {
        System.out.println("Рассылка рекламных писем с текстом: " + message);

    }
}
