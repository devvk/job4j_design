package ru.job4j.tree;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

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
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> element = data.poll();
            if (element.value.equals(value)) {
                result = Optional.of(element);
                break;
            }
            data.addAll(element.children);
        }
        return result;
    }

}
