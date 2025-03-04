package ru.job4j.io;

import java.io.FileOutputStream;
import java.io.IOException;

public class ResultFile {
    public static void main(String[] args) {
        try (FileOutputStream output = new FileOutputStream("data/multiple.txt")) {
            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 10; j++) {
                    output.write((i + " * " + j + " = " + (i * j)).getBytes());
                    output.write(System.lineSeparator().getBytes());
                }
                output.write(System.lineSeparator().getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл!", e);
        }
    }
}