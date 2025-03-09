package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Утилита для архивации папки.
 * Параметры:
 * -d - directory - которую мы хотим архивировать.
 * -e - exclude - исключить файлы с расширением class.
 * -o - output - во что мы архивируем.
 * Пример запуска: -d=C:\projects\job4j_design -e=.class -o=project.zip
 */
public class Zip {

    /**
     * Архивирует список файлов и папок (sources) в ZIP-архив (target),
     * сохраняя структуру директорий.
     *
     * @param sources список файлов и папок для архивирования. Первый элемент
     *                используется как корень для вычисления относительных путей.
     * @param target  путь к создаваемому ZIP-архиву.
     */
    public void packFiles(List<Path> sources, Path target) {
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(target.toString()))) {
            for (Path source : sources) {
                zip.putNextEntry(new ZipEntry(source.toString()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source.toFile()))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (IOException e) {
            System.err.println("Error packing files: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Валидирует входные аргументы командной строки.
     *
     * @param arguments объект аргументов командной строки.
     * @throws IllegalArgumentException если аргументы отсутствуют или заданы некорректно.
     */
    private void validateArguments(ArgsName arguments) {
        String dir = arguments.get("d");
        String exclude = arguments.get("e");
        String output = arguments.get("o");

        if (dir.isEmpty() || exclude.isEmpty() || output.isEmpty()) {
            throw new IllegalArgumentException("Empty arguments!");
        }
        Path path = Path.of(dir);
        if (!Files.exists(path) && !Files.isDirectory(path)) {
            throw new IllegalArgumentException(String.format("Directory '%s' not exist!", path));
        }
        if (!exclude.startsWith(".")) {
            throw new IllegalArgumentException("The file extension must start with a '.' character.");
        }
        if (!output.endsWith(".zip")) {
            throw new IllegalArgumentException("The archive name must end with '.zip'.");
        }
    }

    /**
     * Ищет файлы и создает ZIP-архив.
     *
     * @param args аргументы командной строки.
     */
    public void zipFiles(String[] args) {
        ArgsName arguments = ArgsName.of(args);
        validateArguments(arguments);
        try {
            List<Path> sources = Search.search(Path.of(arguments.get("d")),
                    path -> !path.getFileName().toString().endsWith(arguments.get("e")));
            packFiles(sources, Path.of(arguments.get("o")));
            System.out.println("Archive created successfully: " + arguments.get("o"));
        } catch (FileAlreadyExistsException e) {
            System.err.println("FileAlreadyExistsException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error occurred during file search: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Required 3 arguments: -d=DIR -e=.EXT -o=FILE.zip");
        }
        new Zip().zipFiles(args);
    }
}
