package ru.job4j.ood.lsp.parking.model;

import ru.job4j.ood.lsp.parking.Vehicle;

public class Truck implements Vehicle {

    private final int size;

    public Truck(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }
}
