package ru.job4j.io.objectstream;

import java.io.Serial;
import java.io.Serializable;

public class Car implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final transient String brand;
    private final String model;
    private final int year;

    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{"
                + "brand(transient)=" + brand
                + ", model=" + model
                + ", year=" + year
                + "}";
    }
}
