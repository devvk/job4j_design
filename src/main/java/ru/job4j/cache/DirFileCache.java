package ru.job4j.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final Path cachingDir;

    public DirFileCache(String cachingDir) {
        Path path = Path.of(cachingDir);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Путь не существует: " + path);
        }
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Это не директория: " + path);
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("Нет прав на чтение: " + path);
        }
        this.cachingDir = path;
    }

    /**
     * Загружает содержимое файла из указанной директории.
     *
     * @param key имя файла (относительно cachingDir)
     * @return содержимое файла
     */
    @Override
    protected String load(String key) {
        String content;
        Path filePath = cachingDir.resolve(key);
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("Путь не существует: " + filePath);
        }
        if (Files.isDirectory(filePath)) {
            throw new IllegalArgumentException("Это не файл, а директория: " + filePath);
        }
        if (!Files.isReadable(filePath)) {
            throw new IllegalArgumentException("Нет прав на чтение: " + filePath);
        }

        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + filePath, e);
        }
        return content;
    }
}
