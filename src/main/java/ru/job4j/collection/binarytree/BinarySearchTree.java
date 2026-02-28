package ru.job4j.collection.binarytree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BinarySearchTree<T extends Comparable<T>> {

    private Node root;

    public boolean put(T key) {
        boolean result;
        if (Objects.isNull(root)) {
            root = new Node(key);
            result = true;
        } else {
            result = put(root, key);
        }
        return result;
    }

    /**
     * Добавляет узел по ключу.
     *
     * @param key ключ, который нужно добавить
     * @return true, если узел добавлен, иначе false (если ключ уже существует)
     */
    private boolean put(Node node, T key) {
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            return false;
        }
        if (compare < 0) {
            if (node.left == null) {
                node.left = new Node(key);
                return true;
            }
            return put(node.left, key);
        }
        if (node.right == null) {
            node.right = new Node(key);
            return true;
        } else {
            return put(node.right, key);
        }
    }

    /**
     * Проверяет наличие узла по ключу.
     *
     * @param key ключ, который нужно найти
     * @return true, если узел найден, иначе false
     */
    public boolean contains(T key) {
        return find(root, key) != null;
    }

    /**
     * Поиск узла по ключу.
     *
     * @param node узел, с которого начинается поиск
     * @param key  ключ, который нужно найти
     * @return найденный узел, иначе null
     */
    private Node find(Node node, T key) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            return node;
        }
        if (compare < 0) {
            return find(node.left, key);
        }
        return find(node.right, key);
    }

    /**
     * Удаляет узел по ключу.
     *
     * @param key ключ, который нужно удалить
     * @return true, если узел удалён, иначе false (если ключ не найден)
     */
    public boolean remove(T key) {
        boolean result = false;
        if (Objects.nonNull(key) && Objects.nonNull(root)) {
            result = remove(root, key);
        }
        return result;
    }

    private boolean remove(Node source, T key) {
        boolean result = true;
        Node current = source;
        Node parent = source;
        boolean isLeft = true;
        while (result && !Objects.equals(current.key, key)) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                isLeft = true;
                current = current.left;
            } else if (cmp > 0) {
                isLeft = false;
                current = current.right;
            }
            if (Objects.isNull(current)) {
                result = false;
            }
        }
        if (result) {
            if (Objects.isNull(current.left) && Objects.isNull(current.right)) {
                swap(isLeft, source, parent, current, null);
            } else if (Objects.nonNull(current.left) && Objects.isNull(current.right)) {
                swap(isLeft, source, parent, current, current.left);
            } else if (Objects.isNull(current.left)) {
                swap(isLeft, source, parent, current, current.right);
            } else {
                Node heir = getHeir(current);
                swap(isLeft, source, parent, current, heir);
                // левое поддерево удаляемого узла цепляем наследнику
                heir.left = current.left;
            }
            current.left = null;
            current.right = null;
            current.key = null;
        }
        return result;
    }

    private void swap(boolean isLeft, Node source, Node parent, Node current, Node equal) {
        if (Objects.equals(current, source)) {
            root = equal;
        } else if (isLeft) {
            parent.left = equal;
        } else {
            parent.right = equal;
        }
    }

    private Node getHeir(Node delNode) {
        Node nodeParent = delNode;
        Node node = delNode;
        Node current = delNode.right;
        while (current != null) {
            nodeParent = node;
            node = current;
            current = current.left;
        }
        if (node != delNode.right) {
            nodeParent.left = node.right;
            node.right = delNode.right;
        }
        return node;
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] array = new int[]{2, 1, 10, 6, 14, 4, 8, 12, 16, 11, 9, 13, 15, 17, 3, 5, 7};
        for (int i : array) {
            bst.put(i);
        }
        System.out.println(bst);
        System.out.println(bst.remove(10));
        System.out.println("После удаления узла 10 :");
        System.out.println(bst);
    }

    /**
     * Возвращает список ключей в симметричном порядке (лево -> корень -> право).
     *
     * @return список ключей в отсортированном порядке
     */
    public List<T> inSymmetricalOrder() {
        List<T> list = new ArrayList<>();
        Node node = root;
        return inSymmetricalOrder(node, list);
    }

    /**
     * Выполняет симметричный обход поддерева (лево -> корень -> право).
     *
     * @param localRoot корень поддерева
     * @param list      список для добавления ключей
     * @return список ключей
     */
    private List<T> inSymmetricalOrder(Node localRoot, List<T> list) {
        if (localRoot != null) {
            inSymmetricalOrder(localRoot.left, list);
            list.add(localRoot.key);
            inSymmetricalOrder(localRoot.right, list);
        }
        return list;
    }

    /**
     * Возвращает список ключей в прямом порядке (корень -> левый -> правый).
     *
     * @return список ключей в порядке pre-order
     */
    public List<T> inPreOrder() {
        List<T> list = new ArrayList<>();
        Node node = root;
        return inPreOrder(node, list);
    }

    /**
     * Выполняет прямой обход поддерева (корень -> левый -> правый).
     *
     * @param localRoot корень поддерева
     * @param list      список для накопления результата
     * @return список ключей
     */
    private List<T> inPreOrder(Node localRoot, List<T> list) {
        if (localRoot != null) {
            list.add(localRoot.key);
            inPreOrder(localRoot.left, list);
            inPreOrder(localRoot.right, list);
        }
        return list;
    }

    /**
     * Возвращает список ключей в обратном порядке (левый -> правый -> корень).
     *
     * @return список ключей в порядке post-order
     */
    public List<T> inPostOrder() {
        List<T> list = new ArrayList<>();
        Node node = root;
        return inPostOrder(node, list);
    }

    /**
     * Выполняет обратный обход поддерева (левый -> правый -> корень).
     *
     * @param localRoot корень поддерева
     * @param list      список для накопления результата
     * @return список ключей
     */
    private List<T> inPostOrder(Node localRoot, List<T> list) {
        if (localRoot != null) {
            inPostOrder(localRoot.left, list);
            inPostOrder(localRoot.right, list);
            list.add(localRoot.key);
        }
        return list;
    }

    /**
     * Полностью очищает дерево.
     */
    public void clear() {
        clear(root);
        root = null;
    }

    /**
     * Рекурсивно очищает поддерево, начиная с переданного узла.
     *
     * @param localRoot корень поддерева
     */
    private void clear(Node localRoot) {
        if (localRoot != null) {
            clear(localRoot.left);
            clear(localRoot.right);
            localRoot.left = null;
            localRoot.right = null;
            localRoot.key = null;
        }
    }

    public T minimum() {
        return Objects.nonNull(root) ? minimum(root).key : null;
    }

    /**
     * Возвращает узел с минимальным ключом, начиная поиск с переданного узла.
     *
     * @param node узел, с которого начинается поиск
     * @return узел с минимальным ключом
     */
    private Node minimum(Node node) {
        // минимальный всегда самый левый
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    public T maximum() {
        return Objects.nonNull(root) ? maximum(root).key : null;
    }

    /**
     * Возвращает узел с максимальным ключом,
     * начиная поиск с переданного узла.
     *
     * @param node узел, с которого начинается поиск
     * @return узел с максимальным ключом
     */
    private Node maximum(Node node) {
        // максимальный всегда самый правый
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }

    @Override
    public String toString() {
        return PrintTree.getTreeDisplay(root);
    }

    private class Node implements VisualNode {
        private T key;
        private Node left;
        private Node right;

        public Node(T key) {
            this.key = key;
        }

        @Override
        public VisualNode getLeft() {
            return left;
        }

        @Override
        public VisualNode getRight() {
            return right;
        }

        @Override
        public String getText() {
            return key.toString();
        }
    }
}
