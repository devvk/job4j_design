package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVReader {
    public static final String PATH = "path";
    public static final String DELIMITER = "delimiter";
    public static final String OUT = "out";
    public static final String FILTER = "filter";

    /**
     * Основной метод обработки CSV файла.
     *
     * @param argsName параметры командной строки.
     */
    public static void handle(ArgsName argsName) throws IOException {
        String delimiter = argsName.get(DELIMITER);
        List<String> result = new ArrayList<>();
        List<String> filters = List.of(argsName.get(FILTER).split(","));

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(argsName.get(PATH))))) {
            String[] headers = scanner.nextLine().split(delimiter);
            result.add(String.join(delimiter, filters));
            Map<String, Integer> headerIndexMap = createIndexMap(headers);

            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(delimiter);
                result.add(filterRow(row, filters, headerIndexMap, delimiter));
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении CSV файла.", e);
        }

        writeOutput(argsName.get(OUT), result);
    }

    /**
     * Возвращает строку, отфильтрованную по указанным столбцам.
     *
     * @param row           массив значений строки.
     * @param filters       список фильтруемых столбцов.
     * @param headerIndexes карта {название столбца -> индекс}.
     * @param delimiter     разделитель.
     * @return строка с выбранными значениями.
     */
    private static String filterRow(String[] row, List<String> filters,
                                    Map<String, Integer> headerIndexes, String delimiter) {
        List<String> result = new ArrayList<>();
        for (String filter : filters) {
            Integer index = headerIndexes.get(filter);
            if (index != null && index < row.length) {
                result.add(row[index]);
            }
        }
        return String.join(delimiter, result);
    }

    /**
     * Записывает данные в файл или выводит в консоль.
     *
     * @param output путь к файлу или "stdout" для вывода в консоль.
     * @param data   список строк для записи.
     */
    private static void writeOutput(String output, List<String> data) {
        try (PrintWriter writer = "stdout".equals(output)
                ? new PrintWriter(System.out) : new PrintWriter(Files.newBufferedWriter(Path.of(output)))) {
            data.forEach(writer::println);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи в файл: " + output, e);
        }
    }

    /**
     * Создает карту заголовков с индексами столбцов.
     *
     * @param headers массив заголовков.
     * @return карта {название столбца -> индекс}.
     */
    private static Map<String, Integer> createIndexMap(String[] headers) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            map.put(headers[i], i);
        }
        return map;
    }

    /**
     * Валидирует аргументы командной строки.
     * -path=data/input.csv -delimiter=, -out=stdout -filter=name,age
     * -path=data/input.csv -delimiter=; -out=data/output.csv -filter=name,age
     *
     * @param args аргументы.
     * @throws IllegalArgumentException если аргумент невалидный.
     */
    private static void validateArgs(ArgsName args) {
        String path = args.get(PATH);
        String out = args.get(OUT);
        if (!Files.exists(Path.of(path))) {
            throw new IllegalArgumentException(String.format("File '%s' does not exist.", path));
        }
        if (!Set.of(",", ";").contains(args.get(DELIMITER))) {
            throw new IllegalArgumentException("Delimiter must be ',' or ';'.");
        }
        if (!"stdout".equals(out) && !Files.exists(Path.of(out).getParent())) {
            throw new IllegalArgumentException("Invalid output path: " + out);
        }
        if (args.get(FILTER).isBlank()) {
            throw new IllegalArgumentException("Filter parameter cannot be empty.");
        }
    }

    public static void main(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Usage: -path=<file> -delimiter=<,|;> -out=<stdout|file> -filter=<filters>");
        }
        ArgsName argsName = ArgsName.of(args);
        validateArgs(argsName);
        try {
            handle(argsName);
        } catch (IOException e) {
            throw new RuntimeException("Error processing CSV file.", e);
        }
    }
}
