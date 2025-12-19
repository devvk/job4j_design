package ru.job4j.ood.lsp.quality.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.quality.model.Food;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrashTest {

    private final LocalDate now = LocalDate.of(2025, 12, 19);
    private Trash trash;

    @BeforeEach
    void init() {
        trash = new Trash();
    }

    /**
     * Used = 99% -> ещё не Trash
     */
    @Test
    void whenUsedPercent99ThenNotAccept() {
        Food food = new Food("Meat", now.minusDays(99), now.plusDays(1), 10, 0);
        assertFalse(trash.accept(food, now));
    }

    /**
     * used = 100% (now == expiry) -> Trash
     */
    @Test
    void whenUsedPercent100ThenAccept() {
        Food food = new Food("Meat", now.minusDays(100), now, 10, 0);
        assertTrue(trash.accept(food, now));
    }
}
