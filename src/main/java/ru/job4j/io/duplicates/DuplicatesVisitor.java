package ru.job4j.io.duplicates;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    Map<FileProperty, List<Path>> files = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
        FileProperty key = new FileProperty(file.getFileName().toString(), attributes.size());
        files.computeIfAbsent(key, k -> new LinkedList<>()).add(file);
        return FileVisitResult.CONTINUE;
    }

    public void printDuplicates() {
        files.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(entry -> {
                    FileProperty file = entry.getKey();
                    System.out.println(file);
                    entry.getValue().forEach(System.out::println);
                });
    }
}
