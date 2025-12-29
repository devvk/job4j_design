package ru.job4j.collection;

import java.util.*;

@SuppressWarnings("unchecked")
public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        container = (T[]) new Object[capacity];
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param value значение элемента.
     */
    @Override
    public void add(T value) {
        if (size == container.length) {
            grow();
        }
        container[size++] = value;
        modCount++;
    }

    /**
     * Увеличивает размер списка.
     */
    private void grow() {
        int newCapacity = container.length > 0 ? container.length * 2 : 1;
        container = Arrays.copyOf(container, newCapacity);
    }

    /**
     * Заменяет элемент по индексу.
     *
     * @param index    индекс элемента.
     * @param newValue новое значение.
     * @return старое значение элемента с индексом index.
     */
    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, size);
        T oldValue = container[index];
        container[index] = newValue;
        return oldValue;
    }

    /**
     * Удаляет элемент по индексу.
     *
     * @param index индекс элемента.
     * @return удаленный элемент.
     */
    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T oldValue = container[index];
        final int newSize = size - 1;
        if (newSize > index) {
            System.arraycopy(container, index + 1, container, index, newSize - index);
        }
        container[--size] = null;
        modCount++;
        return oldValue;
    }

    /**
     * Возвращает элемент по его индексу.
     *
     * @param index индекс элемента.
     * @return элемент.
     */
    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    /**
     * Возвращает размер списка.
     *
     * @return размер списка.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Возвращает итератор.
     *
     * @return итератор.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private int index;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[index++];
            }
        };
    }
}
