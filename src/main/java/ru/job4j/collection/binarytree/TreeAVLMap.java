package ru.job4j.collection.binarytree;

import java.util.*;

/**
 * Реализация TreeMap на основе самобалансирующегося AVL-дерева.
 * Хранит пары "ключ-значение" и поддерживает балансировку дерева для эффективного поиска,
 * вставки и удаления элементов с временной сложностью O(log n).
 */
public class TreeAVLMap<T extends Comparable<T>, V> {

    private Node root;

    /**
     * Проверяет наличие узла по ключу.
     *
     * @param key ключ, который нужно найти
     * @return true, если узел найден, иначе false
     */
    public boolean contains(T key) {
        return Objects.nonNull(key) && Objects.nonNull(find(root, key));
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
     * Добавляет пару ключ-значение в дерево. Если ключ уже существует, значение обновляется.
     *
     * @param key   ключ для добавления
     * @param value значение, связанное с ключом
     * @return true, если пара успешно добавлена или обновлена, иначе false
     */
    public boolean put(T key, V value) {
        if (key == null || value == null) {
            return false;
        }
        root = insert(root, key, value);
        return true;
    }

    /**
     * Вставляет новый узел в дерево и поддерживает его балансировку.
     *
     * @param node  текущий узел
     * @param key   ключ для добавления
     * @param value значение, связанное с ключом
     * @return корневой узел после вставки и балансировки
     */
    private Node insert(Node node, T key, V value) {
        if (node == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, key, value);
        } else {
            // если ключ уже есть, обновляем значение
            node.value = value;
            return node;
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

    /**
     * Удаляет ключ из дерева.
     *
     * @param key ключ для удаления
     * @return true, если удаление успешно, иначе false
     */
    public boolean remove(T key) {
        if (key == null || root == null) {
            return false;
        }
        if (!contains(key)) {
            return false;
        }
        root = remove(root, key);
        return true;
    }

    /**
     * Удаляет узел с указанным ключом и поддерживает балансировку.
     *
     * @param node    текущий узел
     * @param element ключ для удаления
     * @return корневой узел после удаления и балансировки
     */
    private Node remove(Node node, T element) {
        if (node == null) {
            return null;
        }
        int comparisonResult = element.compareTo(node.key);
        if (comparisonResult < 0) {
            node.left = remove(node.left, element);
        } else if (comparisonResult > 0) {
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
     * Возвращает минимальный ключ в дереве.
     *
     * @return минимальный ключ или {@code null}, если дерево пусто
     */
    public T minimum() {
        return Objects.nonNull(root) ? minimum(root).key : null;
    }

    /**
     * Возвращает узел с минимальным ключом в указанном поддереве.
     *
     * @param node корневой узел поддерева, в котором ищется минимальный ключ
     * @return узел с минимальным ключом или {@code null}, если поддерево пустое
     */
    private Node minimum(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Возвращает максимальный ключ в дереве.
     *
     * @return максимальный ключ или {@code null}, если дерево пусто
     */
    public T maximum() {
        return Objects.nonNull(root) ? maximum(root).key : null;
    }

    /**
     * Возвращает узел с максимальным ключом в указанном поддереве.
     *
     * @param node корневой узел поддерева, в котором ищется максимальный ключ
     * @return узел с максимальным ключом или null, если поддерево пустое
     */
    private Node maximum(Node node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * Возвращает значение по ключу.
     *
     * @param key ключ для поиска
     * @return значение, связанное с ключом, или null, если ключ не найден
     */
    public V get(T key) {
        if (key == null) {
            return null;
        }
        Node n = find(root, key);
        return n == null ? null : n.value;
    }

    /**
     * Возвращает множество всех ключей, содержащихся в дереве.
     *
     * @return множество ключей в дереве
     */
    public Set<T> keySet() {
        Set<T> keys = new HashSet<>();
        collectKeys(root, keys);
        return keys;
    }

    /**
     * Собирает все ключи из указанного поддерева и добавляет их в множество.
     *
     * @param node текущий узел поддерева
     * @param keys множество, куда добавляются ключи
     */
    private void collectKeys(Node node, Set<T> keys) {
        if (node != null) {
            collectKeys(node.left, keys);
            keys.add(node.key);
            collectKeys(node.right, keys);
        }
    }

    /**
     * Возвращает коллекцию всех значений, содержащихся в дереве.
     *
     * @return коллекция значений в дереве
     */
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        Node node = root;
        collectValues(node, values);
        return values;
    }

    /**
     * Собирает все значения из указанного поддерева и добавляет их в коллекцию.
     *
     * @param node   текущий узел поддерева
     * @param values коллекция, куда добавляются значения
     */
    private void collectValues(Node node, Collection<V> values) {
        if (node != null) {
            collectValues(node.left, values);
            values.add(node.value);
            collectValues(node.right, values);
        }
    }

    private class Node {
        private int balanceFactor;
        private T key;
        private V value;
        private int height;
        private Node left;
        private Node right;

        Node(T key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
