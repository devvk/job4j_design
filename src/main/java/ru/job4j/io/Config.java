package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    /**
     * Загружает пары ключ-значение в Map values.
     */
    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path, StandardCharsets.UTF_8))) {
            reader.lines().forEach(line -> {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    return;
                }
                int index = line.indexOf("=");
                if (index == -1) {
                    throw new IllegalArgumentException("Формат должен содержать ключ=значение!");
                }
                String key = line.substring(0, index).trim();
                String value = line.substring(index + 1).trim();
                if (key.isEmpty() || value.isEmpty()) {
                    throw new IllegalArgumentException("Формат должен содержать ключ=значение!");
                }
                values.put(key, value);

            });
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + path, e);
        }
    }

    /**
     * Возвращает значение ключа.
     *
     * @param key ключ.
     * @return значение.
     */
    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path, StandardCharsets.UTF_8))) {
            reader.lines().forEach(output::add);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + path, e);
        }
        return output.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("data/app.properties"));
    }

}