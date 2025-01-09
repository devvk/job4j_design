package ru.job4j.iterator;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;

public class ListUtils {

    /**
     * Вставляет элемент в список перед элементом с указанным индексом.
     *
     * @param list  Список, в который будет вставлен новый элемент.
     * @param index Индекс элемента, перед которым нужно вставить новый.
     * @param value Элемент, который будет вставлен в список.
     * @param <T>   Тип элементов списка.
     * @throws IndexOutOfBoundsException Если указанный индекс выходит за пределы списка.
     */
    public static <T> void addBefore(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        ListIterator<T> iterator = list.listIterator(index);
        iterator.add(value);
    }

    /**
     * Вставляет элемент в список сразу после элемента с указанным индексом.
     *
     * @param list  Список, в который будет вставлен новый элемент.
     * @param index Индекс элемента, после которого нужно вставить новый.
     * @param value Элемент, который будет вставлен в список.
     * @param <T>   Тип элементов списка.
     * @throws IndexOutOfBoundsException Если указанный индекс выходит за пределы списка.
     */
    public static <T> void addAfter(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        ListIterator<T> iterator = list.listIterator(index + 1);
        iterator.add(value);
    }

    /**
     * Удаляет все элементы из списка, которые соответствуют условию, определённому в фильтре.
     *
     * @param list   Список, из которого будут удалены элементы.
     * @param filter Условие, которому должны соответствовать элементы для их удаления.
     * @param <T>    Тип элементов списка.
     * @throws NullPointerException Если filter равен null.
     */
    public static <T> void removeIf(List<T> list, Predicate<T> filter) {
        if (filter == null) {
            throw new NullPointerException("Predicate cannot be null");
        }
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (filter.test(element)) {
                iterator.remove();
            }
        }
    }

    /**
     * Заменяет элементы в списке, которые соответствуют условию, на новый заданный элемент.
     *
     * @param list   Список, в котором будет выполнена замена элементов.
     * @param filter Условие, которому должны соответствовать элементы для замены.
     * @param value  Новый элемент, который будет вставлен вместо старых.
     * @param <T>    Тип элементов списка.
     * @throws NullPointerException Если filter или value равны null.
     */
    public static <T> void replaceIf(List<T> list, Predicate<T> filter, T value) {
        if (filter == null || value == null) {
            throw new NullPointerException("Predicate and value cannot be null");
        }
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (filter.test(element)) {
                iterator.set(value);
            }
        }
    }

    /**
     * Удаляет все элементы из списка, которые присутствуют в другом списке.
     *
     * @param list     Список, из которого будут удалены элементы.
     * @param elements Список элементов, которые должны быть удалены из первого списка.
     * @param <T>      Тип элементов списка.
     * @throws NullPointerException Если elements равен null.
     */
    public static <T> void removeAll(List<T> list, List<T> elements) {
        if (elements == null) {
            throw new NullPointerException("Elements list cannot be null");
        }
        list.removeIf(elements::contains);
    }
}
