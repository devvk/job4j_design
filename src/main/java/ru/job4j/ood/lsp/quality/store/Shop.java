package ru.job4j.ood.lsp.quality.store;

import ru.job4j.ood.lsp.quality.model.Food;

import java.time.LocalDate;

public class Shop extends AbstractStore {

    /**
     * Если срок годности израсходован от 25% до 100%, продукт должен оказаться в Shop;
     *
     * @param food продукт.
     * @return true, если срок годности израсходован от 25% до 100%, иначе false.
     */
    @Override
    public boolean accept(Food food, LocalDate now) {
        int used = food.getUsedPercent(now);
        if (used >= DISCOUNT_MIN_USAGE_PERCENT && used < SHOP_MAX_USAGE_PERCENT) {
            applyDiscount(food);
        }
        return used >= SHOP_MIN_USAGE_PERCENT && used < SHOP_MAX_USAGE_PERCENT;
    }

    private void applyDiscount(Food food) {
        if (food.getDiscount() == 0) {
            food.setDiscount(DISCOUNT_PERCENT);
        }
    }
}
