package ru.job4j.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CyclicIterator<T> implements Iterator<T> {

    private final List<T> data;
    private Iterator<T> iterator;
    private final boolean isNotEmpty;

    public CyclicIterator(List<T> data) {
        this.data = data;
        this.iterator = data.iterator();
        this.isNotEmpty = !data.isEmpty();
    }

    @Override
    public boolean hasNext() {
        if (isNotEmpty && !iterator.hasNext()) {
            iterator = data.iterator();
        }
        return isNotEmpty;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return iterator.next();
    }
}