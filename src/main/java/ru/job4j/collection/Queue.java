package ru.job4j.collection;

public interface Queue<T> {

    T poll();

    void push(T item);

    boolean isEmpty();
}
