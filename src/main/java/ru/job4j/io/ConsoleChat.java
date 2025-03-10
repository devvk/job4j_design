package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        List<String> phrases = readPhrases();
        if (phrases.isEmpty()) {
            throw new RuntimeException("Файл с ответами бота пуст.");
        }

        List<String> log = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean canBotListening = true;
        String input = "";
        while (!OUT.equals(input)) {
            System.out.println("Введите фразу или команду (закончить, стоп, продолжить): ");
            input = scanner.nextLine();
            log.add(input);
            if (STOP.equals(input)) {
                canBotListening = false;
            } else if (CONTINUE.equals(input)) {
                canBotListening = true;
            }

            if (canBotListening) {
                String phrase = phrases.get(new Random().nextInt(phrases.size()));
                System.out.println("[Бот]: " + phrase);
                log.add(phrase);
            }
        }
        saveLog(log);
        System.out.print("Программа завершена.");
    }

    private List<String> readPhrases() {
        try (BufferedReader reader = new BufferedReader(new FileReader(botAnswers, StandardCharsets.UTF_8))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + e.getMessage(), e);
        }
    }

    private void saveLog(List<String> log) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, StandardCharsets.UTF_8))) {
            log.forEach(writer::println);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        ConsoleChat consoleChat = new ConsoleChat("data/chat/log.txt", "data/chat/answers.txt");
        consoleChat.run();
    }
}
