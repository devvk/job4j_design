package ru.job4j.kiss.fool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FoolTest {

    @Test
    void whenDivisibleBy3ThenFizz() {
        assertThat(Fool.fizzBuzz(3)).isEqualTo("Fizz");
        assertThat(Fool.fizzBuzz(6)).isEqualTo("Fizz");
    }

    @Test
    void whenDivisibleBy5ThenBuzz() {
        assertThat(Fool.fizzBuzz(5)).isEqualTo("Buzz");
        assertThat(Fool.fizzBuzz(10)).isEqualTo("Buzz");
    }

    @Test
    void whenDivisibleBy3And5ThenFizzBuzz() {
        assertThat(Fool.fizzBuzz(15)).isEqualTo("FizzBuzz");
        assertThat(Fool.fizzBuzz(30)).isEqualTo("FizzBuzz");
    }

    @Test
    void whenNotDivisibleBy3And5ThenNumber() {
        assertThat(Fool.fizzBuzz(1)).isEqualTo("1");
        assertThat(Fool.fizzBuzz(2)).isEqualTo("2");
        assertThat(Fool.fizzBuzz(14)).isEqualTo("14");
    }
}