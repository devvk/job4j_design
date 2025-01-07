package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> input = new SimpleStack<>();
    private final SimpleStack<T> output = new SimpleStack<>();
    private int inputSize;
    private int outputSize;

    /**
     * Удаляет первый элемент очереди и возвращает его.
     *
     * @return удалённый элемент.
     */
    public T poll() {
        if (inputSize == 0 && outputSize == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        if (outputSize == 0) {
            while (inputSize > 0) {
                output.push(input.pop());
                inputSize--;
                outputSize++;
            }
        }

        T temp = null;
        if (outputSize > 0) {
            temp = output.pop();
            outputSize--;
        }

        return temp;
    }

    /**
     * Добавляет элемент в конец очереди.
     *
     * @param value значение для добавления.
     */
    public void push(T value) {
        input.push(value);
        inputSize++;
    }
}
