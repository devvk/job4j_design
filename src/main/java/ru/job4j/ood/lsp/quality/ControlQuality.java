package ru.job4j.ood.lsp.quality;

import ru.job4j.ood.lsp.quality.model.Food;
import ru.job4j.ood.lsp.quality.store.Shop;
import ru.job4j.ood.lsp.quality.store.Store;
import ru.job4j.ood.lsp.quality.store.Trash;
import ru.job4j.ood.lsp.quality.store.Warehouse;

import java.time.LocalDate;
import java.util.List;

/**
 * Продукт попадает в сервис ControlQuality.
 * Сервис ControlQuality в зависимости от срока годности
 * перекладывает продукты в хранилища: Trash, Warehouse, Shop.
 */
public class ControlQuality {
    private final List<Store> stores;

    public ControlQuality(List<Store> stores) {
        this.stores = stores;
    }

    public void control(List<Food> products, LocalDate now) {
        for (Food food : products) {
            for (Store store : stores) {
                if (store.accept(food, now)) {
                    store.add(food);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        List<Food> products = List.of(
                new Food("Apple", now, now.plusDays(14), 0.50, 0),
                new Food("Cheese", now.minusDays(5), now.minusDays(3), 4.50, 0),
                new Food("Orange", now.minusDays(2), now.plusDays(8), 0.70, 0),
                new Food("Milk", now.minusDays(3), now.plusDays(1), 0.80, 0),
                new Food("Meat", now.minusDays(2), now.plusDays(3), 10, 0)
        );

        Store shop = new Shop();
        Store trash = new Trash();
        Store warehouse = new Warehouse();
        List<Store> stores = List.of(trash, warehouse, shop);

        ControlQuality quality = new ControlQuality(stores);
        quality.control(products, now);

        System.out.println("SHOP: " + shop.size());
        shop.getAll().forEach(System.out::println);

        System.out.println("TRASH: " + trash.size());
        trash.getAll().forEach(System.out::println);

        System.out.println("WAREHOUSE: " + warehouse.size());
        warehouse.getAll().forEach(System.out::println);
    }
}
