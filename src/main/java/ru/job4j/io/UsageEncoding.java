package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UsageEncoding {
    public String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            reader.lines()
                    .map(string -> string + System.lineSeparator())
                    .forEach(sb::append);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла.", e);
        }
        return sb.toString();
    }

    public void writeDataInFile(String path, List<String> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, StandardCharsets.UTF_8, true))) {
            data.forEach(writer::println);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи данных в файл.", e);
        }
    }

    public static void main(String[] args) {
        System.out.println("defaultCharset: " + Charset.defaultCharset());
        String path = "data/text.txt";
        UsageEncoding encoding = new UsageEncoding();
        List<String> strings = List.of(
                "Новая строка 1",
                "Новая строка 2",
                "Новая строка 3"
        );
        encoding.writeDataInFile(path, strings);
        String string = encoding.readFile(path);
        System.out.println("Данные из файла: ");
        System.out.println(string);
    }
}
