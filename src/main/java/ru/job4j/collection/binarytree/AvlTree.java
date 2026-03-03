package ru.job4j.collection.binarytree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AvlTree<T extends Comparable<T>> {

    private Node root;

    public boolean contains(T value) {
        return contains(root, value) != null;
    }

    private Node contains(Node node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = value.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp > 0) {
            return contains(node.right, value);
        }
        return contains(node.left, value);
    }

    public boolean insert(T value) {
        boolean result = false;
        if (Objects.nonNull(value)) {
            root = insert(root, value);
            result = true;
        }
        return result;
    }

    private Node insert(Node node, T value) {
        Node result = new Node(value);
        if (Objects.nonNull(node)) {
            int compare = value.compareTo(node.key);
            if (compare < 0) {
                node.left = insert(node.left, value);
            } else if (compare > 0) {
                node.right = insert(node.right, value);
            } else {
                return node;
            }
            updateHeight(node);
            result = balance(node);
        }
        return result;
    }

    public boolean remove(T element) {
        boolean result = false;
        if (Objects.nonNull(element) && Objects.nonNull(root) && contains(element)) {
            root = remove(root, element);
            result = true;
        }
        return result;
    }

    private Node remove(Node node, T element) {
        if (node == null) {
            return null;
        }
        int compare = element.compareTo(node.key);
        if (compare < 0) {
            node.left = remove(node.left, element);
        } else if (compare > 0) {
            node.right = remove(node.right, element);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.height > node.right.height) {
                    T heir = maximum(node.left).key;
                    node.key = heir;
                    node.left = remove(node.left, heir);
                } else {
                    T heir = minimum(node.right).key;
                    node.key = heir;
                    node.right = remove(node.right, heir);
                }
            }
        }
        updateHeight(node);
        return balance(node);
    }

    /**
     * Обновляет высоту и баланс-фактор узла.
     *
     * @param node узел, для которого нужно обновить данные
     */
    private void updateHeight(Node node) {
        int leftNodeHeight = Objects.isNull(node.left) ? -1 : node.left.height;
        int rightNodeHeight = Objects.isNull(node.right) ? -1 : node.right.height;
        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
        node.balanceFactor = rightNodeHeight - leftNodeHeight;
    }

    /**
     * Балансирует узел, если его баланс-фактор выходит за пределы допустимых значений (-1, 0, 1).
     *
     * @param node узел, который необходимо сбалансировать
     * @return сбалансированный узел
     */
    private Node balance(Node node) {
        Node result = node;
        if (node.balanceFactor < -1) {
            if (node.left.balanceFactor >= 0) {
                result = leftRightCase(node);
            } else {
                result = rightRotation(node);
            }
        } else if (node.balanceFactor > 1) {
            if (node.right.balanceFactor >= 0) {
                result = leftRotation(node);
            } else {
                result = rightLeftCase(node);
            }
        }
        return result;
    }

    /**
     * Обрабатывает случай левый-правый (LR) дисбаланса.
     *
     * @param node узел с дисбалансом
     * @return новый корневой узел после балансировки
     */
    private Node leftRightCase(Node node) {
        node.left = leftRotation(node.left);
        return rightRotation(node);
    }

    /**
     * Обрабатывает случай правый-левый (RL) дисбаланса.
     *
     * @param node узел с дисбалансом
     * @return новый корневой узел после балансировки
     */
    private Node rightLeftCase(Node node) {
        node.right = rightRotation(node.right);
        return leftRotation(node);
    }

    /**
     * Выполняет левый поворот дерева.
     *
     * @param node узел, на котором выполняется поворот
     * @return новый корневой узел после поворота
     */
    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        updateHeight(node);
        updateHeight(newParent);
        return newParent;
    }

    /**
     * Выполняет правый поворот дерева.
     *
     * @param node узел, на котором выполняется поворот
     * @return новый корневой узел после поворота
     */
    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        updateHeight(node);
        updateHeight(newParent);
        return newParent;
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

    @Override
    public String toString() {
        return PrintTree.getTreeDisplay(root);
    }

    private class Node implements VisualNode {
        private int balanceFactor;
        private T key;
        private int height;
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
            return String.valueOf(key);
        }
    }
}
