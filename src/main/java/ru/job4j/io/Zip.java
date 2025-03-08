package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Утилита для архивации папки.
 * -d - directory - которую мы хотим архивировать.
 * -e - exclude - исключить файлы с расширением class.
 * -o - output - во что мы архивируем.
 * Пример запуска: -d=C:\projects\job4j_design -e=.class -o=project.zip
 */
public class Zip {

    public void packFiles(List<Path> sources, Path target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target.toString())))) {
            for (Path source : sources) {
                zip.putNextEntry(new ZipEntry(source.getFileName().toString()));
                try (BufferedInputStream output = new BufferedInputStream(new FileInputStream(source.toString()))) {
                    zip.write(output.readAllBytes());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while packing the file: " + e.getMessage());
        }
    }

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

    public void zipFiles(String[] args) {
        ArgsName arguments = ArgsName.of(args);
        validateArguments(arguments);
        try {
            List<Path> foundFiles = Search.search(Path.of(arguments.get("d")),
                    path -> path.getFileName() != null
                            && !path.getFileName().toString().endsWith(arguments.get("e")));
            packFiles(foundFiles, Path.of(arguments.get("o")));
            System.out.println("Archive created successfully: " + arguments.get("o"));
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
