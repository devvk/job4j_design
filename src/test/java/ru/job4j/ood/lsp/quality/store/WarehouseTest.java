package ru.job4j.ood.lsp.quality.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.quality.model.Food;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WarehouseTest {

    private final LocalDate now = LocalDate.of(2025, 12, 19);
    private Warehouse warehouse;

    @BeforeEach
    void init() {
        warehouse = new Warehouse();
    }

    /**
     * used = 0% (daysPassed=0, totalDays=100)
     */
    @Test
    void whenUsedPercent0ThenAccept() {
        Food food = new Food("Milk", now, now.plusDays(100), 10, 0);
        assertTrue(warehouse.accept(food, now));
    }

    /**
     * used = 24% (daysPassed=24, totalDays=100)
     */
    @Test
    void whenUsedPercent24ThenAccept() {
        Food food = new Food("Milk", now.minusDays(24), now.plusDays(76), 10, 0);
        assertTrue(warehouse.accept(food, now));
    }

    /**
     * used = 25% (daysPassed=25, totalDays=100) -> уже не Warehouse
     */
    @Test
    void whenUsedPercent25ThenNotAccept() {
        Food food = new Food("Milk", now.minusDays(25), now.plusDays(75), 10, 0);
        assertFalse(warehouse.accept(food, now));
    }
}
