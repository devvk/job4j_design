package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameLoadTest {
    @Test
    void checkGetMapIsEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void checkNamesArrayIsEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Names array is empty");
    }

    @Test
    void validateNameContainsEqualsSign() {
        String[] array = {"key:value"};
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(() -> nameLoad.parse(array))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("this name: " + array[0] + " does not contain the symbol '='");

    }

    @Test
    void validateNameStartsWithEqualsSign() {
        String[] array = {"=key:value"};
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(() -> nameLoad.parse(array))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("his name: " + array[0] + " does not contain a key");
    }

    @Test
    void validateNameEndsWithEqualsSign() {
        String[] array = {"key:value="};
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(() -> nameLoad.parse(array))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("this name: " + array[0] + " does not contain a value");
    }

    @Test
    void checkGetMapReturnsValidValue() {
        Map<String, String> expected = Map.of("key", "value",
                "key1", "value1",
                "key2", "value2");
        String[] array = {"key=value", "key1=value1", "key2=value2"};
        NameLoad nameLoad = new NameLoad();
        nameLoad.parse(array);
        Map<String, String> result = nameLoad.getMap();
        assertThat(result).isEqualTo(expected);
    }
}