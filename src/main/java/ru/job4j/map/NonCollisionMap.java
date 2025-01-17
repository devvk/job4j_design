package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Реализация простой хэш-таблицы без коллизий.
 *
 * @param <K> Ключ.
 * @param <V> Значение.
 */
public class NonCollisionMap<K, V> implements SimpleMap<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    /**
     * Добавляет пару ключ-значение.
     *
     * @param key   Ключ.
     * @param value Значение.
     * @return {@code true}, если пара была добавлена, {@code false} при коллизии.
     */
    @Override
    public boolean put(K key, V value) {
        if (count / (float) capacity >= LOAD_FACTOR) {
            expand();
        }
        boolean result = false;
        int index = getIndex(key);
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
            result = true;
        }
        return result;
    }

    /**
     * Получает значение по ключу.
     *
     * @param key Ключ.
     * @return Значение, соответствующее ключу, или {@code null}, если ключ не найден.
     */
    @Override
    public V get(K key) {
        V result = null;
        int index = getIndex(key);
        if (table[index] != null) {
            MapEntry<K, V> entry = table[index];
            if (keysEqual(key, entry.key)) {
                result = entry.value;
            }
        }
        return result;
    }

    /**
     * Удаляет пару по ключу.
     *
     * @param key Ключ.
     * @return {@code true}, если удаление прошло успешно, иначе {@code false}.
     */
    @Override
    public boolean remove(K key) {
        boolean result = false;
        int index = getIndex(key);
        if (table[index] != null) {
            MapEntry<K, V> entry = table[index];
            if (keysEqual(key, entry.key)) {
                table[index] = null;
                count--;
                modCount++;
                result = true;
            }
        }
        return result;
    }

    /**
     * Проверяет два ключа на равенство.
     *
     * @param key1 Первый ключ.
     * @param key2 Второй ключ.
     * @return {@code true}, если ключи равны, иначе {@code false}.
     */
    private boolean keysEqual(K key1, K key2) {
        return Objects.hashCode(key1) == Objects.hashCode(key2) && Objects.equals(key1, key2);
    }

    /**
     * Возвращает индекс таблицы по ключу.
     *
     * @param key Ключ.
     * @return Индекс.
     */
    private int getIndex(K key) {
        int hash = hash(Objects.hashCode(key));
        return indexFor(hash);
    }

    /**
     * Возвращает итератор.
     *
     * @return Итератор.
     */
    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private int index;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (capacity > 0) {
                    while (index < capacity && table[index] == null) {
                        index++;
                    }
                }
                return index < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    /**
     * Увеличивает емкость хеш-таблицы в два раза и перераспределяет элементы в новую таблицу.
     * Коллизии не обрабатываются в текущей реализации.
     */
    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                int index = getIndex(entry.key);
                newTable[index] = entry;
            }
        }
        table = newTable;
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
