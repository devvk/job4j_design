package ru.job4j.ood.lsp.parking.model;

import ru.job4j.ood.lsp.parking.Vehicle;

public class Car implements Vehicle {

    @Override
    public int size() {
        return 1;
    }
}
