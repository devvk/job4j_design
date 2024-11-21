package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {

    /**
     * Метод показывает основные операции проверки содержания коллекции.
     */
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array)
                .isNotEmpty()
                .hasSize(5)
                .contains("first", "three", "five")
                /* содержит это в любом порядке, дубликаты не важны: */
                .containsOnly("first", "second", "five", "four", "three")
                /* содержит только это и только в указанном порядке: */
                .containsExactly("first", "second", "three", "four", "five")
                /* содержит только это в любом порядке: */
                .containsExactlyInAnyOrder("five", "first", "four", "second", "three")
                /* содержит хотя бы один из: */
                .containsAnyOf("new", "second", "other")
                /* не содержит ни одного из: */
                .doesNotContain("not", "contains")
                /* начинается с последовательности: */
                .startsWith("first", "second")
                /* заканчивается на последовательность: */
                .endsWith("four", "five")
                /* содержит последовательность: */
                .containsSequence("three", "four")
                .hasSize(5)
                .contains("first", Index.atIndex(0))
                .doesNotContain("first", Index.atIndex(1));
    }

    /**
     * Метод показывает, как можно проверить выполнение элементами коллекции нужных условий.
     */
    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList("first", "second", "three", "four");
        assertThat(list)
                .isNotEmpty()
                .hasSize(4)
                .contains("four")
                .doesNotHaveDuplicates()
                .containsAnyOf("first", "js", "java")
                .doesNotContain("react", Index.atIndex(0))
                /* все элементы выполняют условие */
                .allSatisfy(e -> {
                    assertThat(e.length()).isLessThan(10);
                    assertThat(e.length()).isGreaterThan(0);
                })
                /* хотя бы один элемент выполняет условие */
                .anySatisfy(e -> {
                    assertThat(e.length()).isLessThan(5);
                    assertThat(e.length()).isEqualTo(4);
                })
                .allMatch(e -> e.length() < 10)
                .anyMatch(e -> e.length() >= 2)
                .noneMatch(String::isEmpty);
    }

    /**
     * Метод показывает, что можно выбрать один элемент из коллекции и дальше проверять только его.
     */
    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet("java", "js", "python");
        assertThat(set).isNotEmpty();
        /* первый элемент */
        assertThat(set).first().isEqualTo("java");
        /* элемент по индексу */
        assertThat(set).element(0).isNotNull().isEqualTo("java");
        /* последний элемент */
        assertThat(set).last().isNotNull().isEqualTo("python");
    }

    /**
     * Метод показывает, как можно выбрать из коллекции группу элементов по
     * некоторому условию и дальше работать с отобранной группой.
     */
    @Test
    void checkSetGroup() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet("js", "java", "python");
        assertThat(set).isNotEmpty();
        /* фильтруем источник по предикату и работаем с результатом фильтрации */
        assertThat(set).filteredOn(e -> e.length() == 4).first().isEqualTo("java");
        /* фильтруем с помощью assertThat() и работаем с результатом фильтрации */
        assertThat(set).filteredOnAssertions(e -> assertThat(e.length()).isLessThan(3))
                .hasSize(1)
                .first().isEqualTo("js");
    }

    /**
     * Метод показывает, как AssertJ может работать с Map.
     */
    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("one", "two", "three", "four", "five", "six");
        assertThat(map).hasSize(6)
                /* содержит ключи */
                .containsKeys("two", "six")
                /* содержит значения */
                .containsValues(2, 3, 5)
                /* не содержит ключ */
                .doesNotContainKey("zero")
                /* не содержит значение */
                .doesNotContainValue(100)
                /* содержит пару ключ-значение */
                .containsEntry("two", 1);
    }
}
