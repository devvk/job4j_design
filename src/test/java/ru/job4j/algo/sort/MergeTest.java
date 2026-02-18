package ru.job4j.algo.sort;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MergeTest {

    @Test
    void whenSortedThenOk() {
        int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
        assertThat(Merge.mergeSort(array)).containsExactly(-13, 2, 3, 4, 4, 6, 8, 10);
    }

    @Test
    void whenEmpty() {
        assertThat(Merge.mergeSort(new int[]{})).containsExactly();
    }

    @Test
    void whenSingle() {
        int[] array = {1};
        assertThat(Merge.mergeSort(array)).containsExactly(1);
    }

    @Test
    void whenTwoSorted() {
        assertThat(Merge.mergeSort(new int[]{1, 2})).containsExactly(1, 2);
    }

    @Test
    void whenTwoReversed() {
        assertThat(Merge.mergeSort(new int[]{2, 1})).containsExactly(1, 2);
    }

    @Test
    void whenOddLength() {
        assertThat(Merge.mergeSort(new int[]{3, 1, 2})).containsExactly(1, 2, 3);
    }

    @Test
    void whenDuplicates() {
        assertThat(Merge.mergeSort(new int[]{2, 1, 2, 1})).containsExactly(1, 1, 2, 2);
    }

    @Test
    void whenNegatives() {
        assertThat(Merge.mergeSort(new int[]{0, -1, -3, 2})).containsExactly(-3, -1, 0, 2);
    }

    @Test
    void whenAlreadySorted() {
        assertThat(Merge.mergeSort(new int[]{1, 2, 3, 4})).containsExactly(1, 2, 3, 4);
    }

    @Test
    void whenReverseSorted() {
        assertThat(Merge.mergeSort(new int[]{4, 3, 2, 1})).containsExactly(1, 2, 3, 4);
    }
}
