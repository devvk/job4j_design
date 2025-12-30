package ru.job4j.ood.lsp.parking;

import java.util.Objects;

public class CityParking implements Parking {

    private final Vehicle[] cars;
    private final Vehicle[] trucks;

    public CityParking(int carsCapacity, int trucksCapacity) {
        if (carsCapacity < 0 || trucksCapacity < 0) {
            throw new IllegalArgumentException("Capacities must be >= 0");
        }
        this.cars = new Vehicle[carsCapacity];
        this.trucks = new Vehicle[trucksCapacity];
    }

    @Override
    public boolean park(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "Vehicle must not be null");
        return vehicle.size() == 1 ? parkCar(vehicle) : parkTruck(vehicle);
    }

    private boolean parkCar(Vehicle vehicle) {
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] == null) {
                cars[i] = vehicle;
                return true;
            }
        }
        return false;
    }

    private boolean parkTruck(Vehicle vehicle) {
        for (int i = 0; i < trucks.length; i++) {
            if (trucks[i] == null) {
                trucks[i] = vehicle;
                return true;
            }
        }
        int startIndex = -1;
        int counter = 0;
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] == null) {
                if (counter == 0) {
                    startIndex = i;
                }
                if (++counter == vehicle.size()) {
                    for (int j = startIndex; j < startIndex + vehicle.size(); j++) {
                        cars[j] = vehicle;
                    }
                    return true;
                }
            } else {
                counter = 0;
                startIndex = -1;
            }
        }
        return false;
    }

    @Override
    public boolean unpark(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "Vehicle must not be null");
        return vehicle.size() == 1 ? unparkCar(vehicle) : unparkTruck(vehicle);
    }

    private boolean unparkCar(Vehicle vehicle) {
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] == vehicle) {
                cars[i] = null;
                return true;
            }
        }
        return false;
    }

    private boolean unparkTruck(Vehicle vehicle) {
        for (int i = 0; i < trucks.length; i++) {
            if (trucks[i] == vehicle) {
                trucks[i] = null;
                return true;
            }
        }
        int startIndex = -1;
        int counter = 0;
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] == vehicle) {
                if (counter == 0) {
                    startIndex = i;
                }
                if (++counter == vehicle.size()) {
                    for (int j = startIndex; j < startIndex + vehicle.size(); j++) {
                        cars[j] = null;
                    }
                    return true;
                }
            } else {
                counter = 0;
                startIndex = -1;
            }
        }
        return false;
    }
}
