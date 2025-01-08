package ru.job4j.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddAfterWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addAfter(input, 5, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenRemoveIf() {
        ListUtils.removeIf(input, i -> i > 2);
        assertThat(input).hasSize(1).contains(1);
        assertThat(input).doesNotContain(3);
    }

    @Test
    void whenReplaceIf() {
        ListUtils.replaceIf(input, i -> i > 0, 10);
        assertThat(input).hasSize(2).contains(10, 10);
    }

    @Test
    void whenRemoveAllWithHasSizeZero() {
        ListUtils.removeAll(input, List.of(1, 2, 3, 4, 5));
        assertThat(input).hasSize(0);
    }

    @Test
    void whenRemoveAllWithHasSizeTwo() {
        ListUtils.removeAll(input, List.of(22, 1));
        assertThat(input).hasSize(1);
        assertThat(input).contains(3);
    }
}
