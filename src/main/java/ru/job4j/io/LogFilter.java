package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[parts.length - 2].equals("404")) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + file, e);
        }
        return lines;
    }

    public void saveTo(String file) {
        List<String> data = filter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            data.forEach(writer::println);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при записи файла: " + file, e);
        }
    }

    public static void main(String[] args) {
        new LogFilter("data/log.txt").saveTo("data/404.txt");
    }
}
