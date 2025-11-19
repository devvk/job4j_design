package ru.job4j.gc.ref;

import java.lang.ref.SoftReference;

/**
 * GC может удалить объект между двумя вызовами ref.get().
 * Сильная ссылка value защищает объект на время выполнения метода.
 */
public class SoftCacheExample {
    private SoftReference<String> ref;

    public String getValue() {
        String value = ref != null ? ref.get() : null;

        if (value == null) {
            value = loadFromDb();
            ref = new SoftReference<>(value);
        }
        return value;
    }

    private String loadFromDb() {
        return "FromDb-" + System.currentTimeMillis();
    }
}
