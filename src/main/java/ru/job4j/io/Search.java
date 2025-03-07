package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class Search {

    public static void main(String[] args) throws IOException {
        Path rootPath = Path.of(".");
        search(rootPath, path -> path.getFileName().toString().endsWith(".js")).forEach(System.out::println);
    }

    public static List<Path> search(Path path, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(path, searcher);
        return searcher.getPaths();
    }
}
