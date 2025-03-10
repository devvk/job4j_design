package ru.job4j.io.scanner;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ScannerExample3 {
    public static void main(String[] args) throws IOException {
        String data = "A 1B FF 110";
        Path tmpFile = Files.createTempFile(data, null);
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(tmpFile.toString()))) {
            writer.write(data.getBytes());
        }
        try (Scanner scanner = new Scanner(tmpFile).useRadix(16)) {
            while (scanner.hasNext()) {
                System.out.print(scanner.nextInt());
                System.out.print(" ");
            }
        }
    }
}
