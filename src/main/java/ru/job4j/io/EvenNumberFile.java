package ru.job4j.io;

import java.io.FileInputStream;
import java.io.IOException;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (FileInputStream input = new FileInputStream("data/even.txt")) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = input.read()) != -1) {
                text.append((char) read);
            }
            isEven(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void isEven(StringBuilder text) {
        String[] lines = text.toString().split(System.lineSeparator());
        for (String line : lines) {
            if (Integer.parseInt(line) % 2 == 0) {
                System.out.println(line + " is even.");
            } else {
                System.out.println(line + " is not even.");
            }
        }
    }
}
