package ru.job4j.io.scanner;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ScannerExample2 {
    public static void main(String[] args) {
        String data = "empl1@mail.ru, empl2@mail.ru, empl3@mail.ru";
        Scanner scanner = new Scanner(new ByteArrayInputStream(data.getBytes())).useDelimiter(", ");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }
}
