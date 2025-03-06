package ru.job4j.io;

import java.io.*;

public class Analysis {
    private static final String STATUS_CODE_400 = "400";
    private static final String STATUS_CODE_500 = "500";

    public void unavailable(String source, String target) {
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
            String start = null;
            String line;
            while ((line = reader.readLine()) != null) {
                int index = line.indexOf(" ");
                String statusCode = line.substring(0, index);
                String time = line.substring(index + 1);
                if (isStatusCodeError(statusCode) && start == null) {
                    start = time;
                } else if (!isStatusCodeError(statusCode) && start != null) {
                    writer.write(start + ";" + time + ";");
                    writer.newLine();
                    start = null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при обработке файла!", e);
        }
    }

    private boolean isStatusCodeError(String statusCode) {
        return STATUS_CODE_400.equals(statusCode) || STATUS_CODE_500.equals(statusCode);
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}