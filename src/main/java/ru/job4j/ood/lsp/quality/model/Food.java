package ru.job4j.ood.lsp.quality.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Food {
    private final String name;
    private final LocalDate createDate;
    private final LocalDate expiryDate;
    private final double price;
    private int discount;

    public Food(String name, LocalDate createDate, LocalDate expiryDate, double price, int discount) {
        if (!expiryDate.isAfter(createDate)) {
            throw new IllegalArgumentException("expiryDate must be after createDate!");
        }
        this.name = name;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
        this.price = price;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Food{"
                + "name=" + name
                + ", createDate=" + createDate
                + ", expiryDate=" + expiryDate
                + ", price=" + price
                + ", discount=" + discount
                + "}";
    }

    public String getName() {
        return name;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    /**
     * Проверяет срок годности (now >= expireDate)
     *
     * @param now текущая дата.
     * @return true, если срок годности истек, иначе false.
     */
    public boolean isExpired(LocalDate now) {
        return !now.isBefore(expiryDate);
    }

    /**
     * Возвращает % оставшегося срока годности.
     *
     * @param now текущая дата.
     * @return % оставшегося срока годности.
     */
    public int getUsedPercent(LocalDate now) {
        long totalDays = ChronoUnit.DAYS.between(createDate, expiryDate);
        long passedDays = ChronoUnit.DAYS.between(createDate, now);

        int expirePercent = (int) (passedDays * 100 / totalDays);
        expirePercent = Math.max(Math.min(expirePercent, 100), 0);

        return expirePercent;
    }
}
