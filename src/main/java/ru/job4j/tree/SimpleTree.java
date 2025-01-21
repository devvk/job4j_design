package ru.job4j.tree;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * Элементарная структура дерева.
 *
 * @param <E>
 */
public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    /**
     * Добавляет дочерний узел к указанному родителю.
     *
     * @param parent Родительский узел.
     * @param child  Дочерний узел.
     * @return true, если узел добавлен успешно, иначе false.
     */
    @Override
    public boolean add(E parent, E child) {
        Optional<Node<E>> parentNode = findBy(parent);
        boolean result = parentNode.isPresent() && findBy(child).isEmpty();
        if (result) {
            parentNode.get().children.add(new Node<>(child));
        }
        return result;
    }

    /**
     * Поиск узла в дереве по заданному значению.
     *
     * @param value Значение узла.
     * @return {@link Optional} с найденным узлом или пустой {@link Optional}, если узел не найден.
     */
    @Override
    public Optional<Node<E>> findBy(E value) {
        return findByPredicate(e -> e.value.equals(value));
    }

    /**
     * Проверяет, является ли дерево бинарным (> 2 дочерних элементов).
     *
     * @return true, если дерево бинарное, иначе false.
     */
    public boolean isBinary() {
        return findByPredicate(e -> e.children.size() > 2).isEmpty();
    }

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> element = data.poll();
            if (condition.test(element)) {
                result = Optional.of(element);
                break;
            }
            data.addAll(element.children);
        }
        return result;
    }

}