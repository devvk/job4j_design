package ru.job4j.collection;

/**
 * Stack на базе связанного списка.
 *
 * @param <T>
 */
public class SimpleStack<T> {

    private final ForwardLinked<T> linked = new ForwardLinked<>();

    /**
     * Удаляет элемент с вершины стека и возвращает его.
     *
     * @return удалённый элемент.
     */
    public T pop() {
        return linked.deleteFirst();
    }

    /**
     * Добавляет элемент на вершину стека.
     *
     * @param value значение для добавления.
     */
    public void push(T value) {
        linked.addFirst(value);
    }
}
