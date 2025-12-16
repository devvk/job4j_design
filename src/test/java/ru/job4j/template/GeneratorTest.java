package ru.job4j.template;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class GeneratorTest {

    private final Generator generator = generator();

    @Test
    public void whenTemplateHasAllKeysThenReplacePlaceholders() {
        String input = "I am a ${name}, Who are ${subject}?";
        Map<String, String> args = Map.of("name", "Petr Arsentev", "subject", "you");
        String expected = "I am a Petr Arsentev, Who are you?";
        String result = generator.produce(input, args);
        assertEquals(expected, result);
    }

    @Test
    public void whenTemplateHasKeyMissingInMapThenThrowException() {
        String input = "I am a ${name}, Who are ${subject}?";
        Map<String, String> args = Map.of("name", "Petr Arsentev");
        assertThatThrownBy(() -> generator.produce(input, args)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenMapHasExtraKeyNotInTemplateThenThrowException() {
        String input = "I am a ${name}";
        Map<String, String> args = Map.of("name", "Petr Arsentev", "subject", "you");
        assertThatThrownBy(() -> generator.produce(input, args)).isInstanceOf(IllegalArgumentException.class);
    }

    private Generator generator() {
        throw new UnsupportedOperationException();
    }
}
