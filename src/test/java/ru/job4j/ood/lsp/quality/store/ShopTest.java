package ru.job4j.ood.lsp.quality.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.quality.model.Food;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    private final LocalDate now = LocalDate.of(2025, 12, 19);
    private Shop shop;

    @BeforeEach
    void init() {
        shop = new Shop();
    }

    /**
     * used = 25% -> Shop, без скидки
     */
    @Test
    void whenUsedPercent25ThenAcceptWithoutDiscount() {
        Food food = new Food("Bread", now.minusDays(25), now.plusDays(75), 10, 0);

        assertTrue(shop.accept(food, now));
        assertEquals(0, food.getDiscount());
    }

    /**
     * used = 74% -> Shop, без скидки
     */
    @Test
    void whenUsedPercent74ThenAcceptWithoutDiscount() {
        Food food = new Food("Bread", now.minusDays(74), now.plusDays(26), 10, 0);

        assertTrue(shop.accept(food, now));
        assertEquals(0, food.getDiscount());
    }

    /**
     * used = 75% -> Shop, со скидкой (обычно 20%)
     */
    @Test
    void whenUsedPercent75ThenAcceptWithDiscount() {
        Food food = new Food("Bread", now.minusDays(75), now.plusDays(25), 10, 0);

        assertTrue(shop.accept(food, now));
        assertEquals(20, food.getDiscount());
    }

    /**
     * used = 99% -> Shop, со скидкой
     */
    @Test
    void whenUsedPercent99ThenAcceptWithDiscount() {
        Food food = new Food("Bread", now.minusDays(99), now.plusDays(1), 10, 0);

        assertTrue(shop.accept(food, now));
        assertEquals(20, food.getDiscount());
    }

    /**
     * used = 100% (now == expiry) -> уже Trash, Shop не принимает
     */
    @Test
    void whenUsedPercent100ThenNotAccept() {
        Food food = new Food("Bread", now.minusDays(100), now, 10, 0);

        assertFalse(shop.accept(food, now));
    }
}
