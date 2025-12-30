package ru.job4j.ood.lsp.parking;

import java.util.Objects;
import java.util.function.Predicate;

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
        int vehicleSize = vehicle.size();
        if (vehicleSize < 1) {
            throw new IllegalArgumentException("Vehicle size must be >= 1");
        }
        return vehicleSize == 1 ? parkCar(vehicle) : parkTruck(vehicle);
    }

    @Override
    public boolean unpark(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "Vehicle must not be null");
        int vehicleSize = vehicle.size();
        if (vehicleSize < 1) {
            throw new IllegalArgumentException("Vehicle size must be >= 1");
        }
        return vehicleSize == 1 ? unparkCar(vehicle) : unparkTruck(vehicle);
    }

    private int findFirstNullIndex(Vehicle[] slots) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private boolean parkCar(Vehicle vehicle) {
        int idx = findFirstNullIndex(cars);
        if (idx != -1) {
            cars[idx] = vehicle;
            return true;
        }
        return false;
    }

    private int findFirstVehicleIndex(Vehicle[] slots, Vehicle vehicle) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == vehicle) {
                return i;
            }
        }
        return -1;
    }

    private boolean unparkCar(Vehicle vehicle) {
        int idx = findFirstVehicleIndex(cars, vehicle);
        if (idx != -1) {
            cars[idx] = null;
            return true;
        }
        return false;
    }

    private int findConsecutiveStartIndex(Vehicle[] slots, int needed, Predicate<Vehicle> matcher) {
        if (needed <= 0) {
            return -1;
        }

        int startIndex = -1;
        int counter = 0;
        for (int i = 0; i < slots.length; i++) {
            if (matcher.test(slots[i])) {
                if (counter == 0) {
                    startIndex = i;
                }
                if (++counter == needed) {
                    return startIndex;
                }
            } else {
                counter = 0;
                startIndex = -1;
            }
        }
        return -1;
    }

    private boolean parkTruck(Vehicle vehicle) {
        int idx = findFirstNullIndex(trucks);
        if (idx != -1) {
            trucks[idx] = vehicle;
            return true;
        }

        int vehicleSize = vehicle.size();
        int startIndex = findConsecutiveStartIndex(cars, vehicleSize, Objects::isNull);
        if (startIndex != -1) {
            for (int j = startIndex; j < startIndex + vehicleSize; j++) {
                cars[j] = vehicle;
            }
            return true;
        }

        return false;
    }

    private boolean unparkTruck(Vehicle vehicle) {
        int idx = findFirstVehicleIndex(trucks, vehicle);
        if (idx != -1) {
            trucks[idx] = null;
            return true;
        }

        int vehicleSize = vehicle.size();
        Predicate<Vehicle> isThisTruck = slot -> slot == vehicle;
        int startIndex = findConsecutiveStartIndex(cars, vehicleSize, isThisTruck);
        if (startIndex != -1) {
            for (int j = startIndex; j < startIndex + vehicleSize; j++) {
                cars[j] = null;
            }
            return true;
        }
        return false;
    }
}
