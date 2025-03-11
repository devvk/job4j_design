package ru.job4j.io;

import java.io.Console;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ConsoleExample {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        Console console = System.console();
        if (console == null) {
            System.out.println("Консоль недоступна!");
            return;
        }

        String login = console.readLine("%s", "Введите логин: ");
        console.printf("Ваш логин: %s\n", login);
        char[] password = console.readPassword("%s", "Введите пароль: ");
        Arrays.fill(password, ' ');
    }
}
