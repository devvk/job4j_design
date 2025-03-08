package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

/**
 * A class for searching files based on a given condition.
 */
public class Search {
    public static void main(String[] args) {
        try {
            validateArgs(args);
            Path startDir = Path.of(args[0]);
            String extension = args[1];

            List<Path> foundFiles = search(startDir,
                    path -> path.getFileName() != null && path.getFileName().toString().endsWith(extension));
            foundFiles.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error occurred during file search: " + e.getMessage());
        }
    }

    /**
     * Validates command-line arguments.
     *
     * @param args command-line arguments
     * @throws IllegalArgumentException if the arguments are invalid
     */
    private static void validateArgs(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Two parameters are required to run the program!");
        }
        Path dir = Path.of(args[0]);
        if (!Files.exists(dir)) {
            throw new IllegalArgumentException(String.format("Directory does not exist: %s", dir.toAbsolutePath()));
        }
        if (!Files.isDirectory(dir)) {
            throw new IllegalArgumentException(String.format("Not a directory: %s", dir.toAbsolutePath()));
        }
        if (!args[1].startsWith(".")) {
            throw new IllegalArgumentException("File extension must start with a dot (e.g., .txt, .java).");
        }
    }

    /**
     * Searches for files that match the given condition.
     *
     * @param startDir  the directory where the search begins
     * @param condition a predicate for filtering files
     * @return a list of found files
     * @throws IOException if an I/O error occurs
     */
    public static List<Path> search(Path startDir, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(startDir, searcher);
        return searcher.getPaths();
    }
}
