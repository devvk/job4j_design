package ru.job4j.ood.lsp.quality;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.quality.model.Food;
import ru.job4j.ood.lsp.quality.store.Shop;
import ru.job4j.ood.lsp.quality.store.Store;
import ru.job4j.ood.lsp.quality.store.Trash;
import ru.job4j.ood.lsp.quality.store.Warehouse;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControlQualityTest {

    private final LocalDate now = LocalDate.of(2025, 12, 19);

    private Store trash;
    private Store warehouse;
    private Store shop;
    private ControlQuality control;

    @BeforeEach
    void init() {
        trash = new Trash();
        warehouse = new Warehouse();
        shop = new Shop();
        control = new ControlQuality(List.of(trash, warehouse, shop));
    }

    @Test
    void whenUsedPercent24ThenGoesToWarehouse() {
        Food food = new Food("Milk", now.minusDays(24), now.plusDays(76), 10, 0);
        control.control(List.of(food), now);

        assertEquals(1, warehouse.size());
        assertEquals(0, shop.size());
        assertEquals(0, trash.size());
    }

    @Test
    void whenUsedPercent25ThenGoesToShop() {
        Food food = new Food("Bread", now.minusDays(25), now.plusDays(75), 10, 0);
        control.control(List.of(food), now);

        assertEquals(1, shop.size());
        assertEquals(0, warehouse.size());
        assertEquals(0, trash.size());
        assertEquals(0, food.getDiscount());
    }

    @Test
    void whenUsedPercent75ThenGoesToShopWithDiscount() {
        Food food = new Food("Bread", now.minusDays(75), now.plusDays(25), 10, 0);
        control.control(List.of(food), now);

        assertEquals(1, shop.size());
        assertEquals(20, food.getDiscount());
    }

    @Test
    void whenUsedPercent100ThenGoesToTrash() {
        Food food = new Food("Meat", now.minusDays(100), now, 10, 0);
        control.control(List.of(food), now);

        assertEquals(1, trash.size());
        assertEquals(0, shop.size());
        assertEquals(0, warehouse.size());
    }

    @Test
    void whenResortThenFoodMovesBetweenStores() {
        Food food = new Food("Milk", now.minusDays(10), now.plusDays(30), 10, 0);
        control.control(List.of(food), now);

        assertEquals(0, warehouse.size());
        assertEquals(1, shop.size());
        assertEquals(0, trash.size());

        control.resort(now.plusDays(40));

        assertEquals(0, warehouse.size());
        assertEquals(0, shop.size());
        assertEquals(1, trash.size());
    }

}
