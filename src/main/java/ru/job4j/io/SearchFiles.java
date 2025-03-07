package ru.job4j.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.nio.file.FileVisitResult.CONTINUE;

public class SearchFiles implements FileVisitor<Path> {

    private final List<Path> paths = new ArrayList<>();
    private final Predicate<Path> predicate;

    public SearchFiles(Predicate<Path> predicate) {
        this.predicate = predicate;
    }

    public List<Path> getPaths() {
        return paths;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
        if (predicate.test(file)) {
            paths.add(file);
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exception) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path directory, IOException exception) {
        return CONTINUE;
    }
}
