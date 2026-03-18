package ru.job4j.collection.binarytree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvlTreeTest {

    @Test
    void symmetricalOrderIsOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int i = 1; i < 8; i++) {
            tree.insert(i);
        }
        List<Integer> list = tree.inSymmetricalOrder();
        assertThat(list).containsExactly(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void preOrderIsOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int i = 1; i < 8; i++) {
            tree.insert(i);
        }
        List<Integer> list = tree.inPreOrder();
        assertThat(list).containsExactly(4, 2, 1, 3, 6, 5, 7);
    }

    @Test
    void postOrderIsOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int i = 1; i < 8; i++) {
            tree.insert(i);
        }
        List<Integer> list = tree.inPostOrder();
        assertThat(list).containsExactly(1, 3, 2, 5, 7, 6, 4);
    }

    @Test
    void whenContainsIsOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        assertThat(tree.contains(0)).isFalse();
        assertThat(tree.contains(1)).isTrue();
    }

    @Test
    void whenInsertIsOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        assertThat(tree.insert(1)).isTrue();
    }

    @Test
    void whenRemoveIsOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        assertTrue(tree.remove(3));
        assertFalse(tree.contains(3));
    }

    @Test
    void whenAddMaximumIsEndThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 1, 3, 5, 7}) {
            tree.insert(element);
        }
        assertThat(tree.maximum()).isEqualTo(7);
    }

    @Test
    void whenAddMinimumIsEndThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.insert(element);
        }
        assertThat(tree.minimum()).isEqualTo(1);
    }
}
