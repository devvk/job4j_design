package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator implements Iterator<Integer> {

    private final int[] data;
    private int index;

    public ArrayIterator(int[] array) {
        this.data = array;
    }

    @Override
    public boolean hasNext() {
        return index < data.length;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data[index++];
    }

    public static void main(String[] args) {
        ArrayIterator iterator = new ArrayIterator(new int[]{1, 2, 3, 4, 5});
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
