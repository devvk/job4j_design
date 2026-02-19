package ru.job4j.algo.stack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BracketsTest {

    private final Brackets brackets = new Brackets();

    @Test
    void givenValidBracketsWhenIsValidThenTrue() {
        assertThat(brackets.isValid("()")).isTrue();
        assertThat(brackets.isValid("()[]{}")).isTrue();
        assertThat(brackets.isValid("{[]}")).isTrue();
    }

    @Test
    void givenInvalidBracketsWhenIsValidThenFalse() {
        assertThat(brackets.isValid("(]")).isFalse();
        assertThat(brackets.isValid("([)]")).isFalse();
        assertThat(brackets.isValid("]")).isFalse();
        assertThat(brackets.isValid("{")).isFalse();
        assertThat(brackets.isValid("")).isTrue();
    }
}