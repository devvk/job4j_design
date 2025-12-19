package ru.job4j.ood.lsp.quality.store;

import ru.job4j.ood.lsp.quality.model.Food;

import java.time.LocalDate;

public class Warehouse extends AbstractStore {

    /**
     * Если срок годности израсходован меньше, чем на 25%, продукт должен оказаться в Warehouse;
     *
     * @param food продукт.
     * @return true, если срок годности израсходован меньше, чем на 25%, иначе false.
     */
    @Override
    public boolean accept(Food food, LocalDate now) {
        return food.getUsedPercent(now) < 25;
    }
}
