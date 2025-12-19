package ru.job4j.ood.lsp.quality.store;

import ru.job4j.ood.lsp.quality.model.Food;

import java.time.LocalDate;
import java.util.List;

public interface Store {

    void add(Food food);

    List<Food> getAll();

    int size();

    boolean accept(Food food, LocalDate now);
}
