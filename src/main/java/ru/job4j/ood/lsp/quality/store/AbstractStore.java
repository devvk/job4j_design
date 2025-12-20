package ru.job4j.ood.lsp.quality.store;

import ru.job4j.ood.lsp.quality.model.Food;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStore implements Store {

    protected static final int WAREHOUSE_MAX_USAGE_PERCENT = 25;

    protected static final int DISCOUNT_MIN_USAGE_PERCENT = 75;
    protected static final int DISCOUNT_PERCENT = 20;

    protected static final int SHOP_MIN_USAGE_PERCENT = 25;
    protected static final int SHOP_MAX_USAGE_PERCENT = 100;

    protected List<Food> products = new ArrayList<>();

    @Override
    public void add(Food food) {
        products.add(food);
    }

    @Override
    public List<Food> getAll() {
        return List.copyOf(products);
    }

    @Override
    public int size() {
        return products.size();
    }
}
