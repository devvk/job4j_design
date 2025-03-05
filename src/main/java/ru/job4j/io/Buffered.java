package ru.job4j.io;

import java.io.*;

/**
 * Буферизированные потоки.
 */
public class Buffered {
    public static void main(String[] args) {
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream("data/input.txt"));
             BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("data/output.txt"))) {
            output.write(input.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("data/input.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("data/output.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
