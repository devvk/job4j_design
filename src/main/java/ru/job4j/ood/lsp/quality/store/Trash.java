package ru.job4j.ood.lsp.quality.store;

import ru.job4j.ood.lsp.quality.model.Food;

import java.time.LocalDate;

public class Trash extends AbstractStore {

    /**
     * Если срок годности вышел (израсходован полностью), продукт должен оказаться в Trash.
     *
     * @param food продукт.
     * @return true, если срок годности израсходован полностью, иначе false.
     */
    @Override
    public boolean accept(Food food, LocalDate now) {
        return food.isExpired(now);
    }
}
