package ru.job4j.ood.lsp.parking;

public class CityParking implements Parking {

    private final int carsCapacity;
    private final int trucksCapacity;

    public CityParking(int carsCapacity, int trucksCapacity) {
        this.carsCapacity = carsCapacity;
        this.trucksCapacity = trucksCapacity;
    }

    @Override
    public boolean park(Vehicle vehicle) {
        return false;
    }

    @Override
    public boolean unpark(Vehicle vehicle) {
        return false;
    }
}
