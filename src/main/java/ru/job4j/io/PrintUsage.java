package ru.job4j.io;

import java.io.PrintStream;
import java.io.PrintWriter;

public class PrintUsage {
    public static void main(String[] args) {
        try (PrintStream stream = new PrintStream("data/print.txt");
             PrintWriter writer = new PrintWriter("data/writer.txt")) {
            stream.println("Из PrintStream в FileOutputStream");
            stream.write("Запись сделанная через write()\n".getBytes());

            writer.println("New message.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
