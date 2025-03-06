package ru.job4j.io;

import java.io.File;

public class Dir {
    public static void main(String[] args) {
        File dir = new File("src/main/java/ru/job4j/io/");
        if (!dir.exists()) {
            throw new IllegalArgumentException(String.format("Директория не существует: %s", dir.getAbsoluteFile()));
        }
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(String.format("Это не директория: %s", dir.getAbsoluteFile()));
        }
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("Не удалось прочитать содержимое директории.");
        }
        for (File file : files) {
            System.out.printf("%s [%s]\n", file.getName(), file.length());
        }
    }
}
