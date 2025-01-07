package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size;
    private int modCount;
    private Node<T> head;

    /**
     * Добавляет элемент в конец списка.
     *
     * @param value элемент, который нужно добавить.
     */
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * Добавляет первый элемент.
     *
     * @param value элемент, который нужно добавить.
     */
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> second = head;
            head = newNode;
            head.next = second;
        }
        size++;
        modCount++;
    }

    /**
     * Возвращает элемент по индексу.
     *
     * @param index индекс элемента, который нужно получить.
     * @return элемент, находящийся по указанному индексу.
     */
    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    /**
     * Удаляет первый элемент.
     *
     * @return удаленный элемент.
     */
    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node<T> deleted = head;
        head = deleted.next;
        T item = deleted.item;
        deleted.next = null;
        deleted.item = null;
        size--;
        modCount++;
        return item;
    }

    /**
     * Возвращает размер списка.
     *
     * @return размер списка.
     */
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException();
                }
                T item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        public Node(T value, Node<T> next) {
            this.item = value;
            this.next = next;
        }
    }
}
