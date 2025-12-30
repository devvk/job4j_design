package ru.job4j.ood.lsp.parking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.parking.model.Car;
import ru.job4j.ood.lsp.parking.model.Truck;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CityParkingTest {

    private static final int TRUCK_SIZE = 3;

    @Test
    @DisplayName("Невозможно запарковать легковую машину, если нет мест.")
    void carCannotParkWhenNoPlacesTest() {
        Parking parking = new CityParking(0, 1);
        Vehicle car = new Car();
        assertFalse(parking.park(car));
    }

    @Test
    @DisplayName("Невозможно запарковать грузовик, если нет мест.")
    void truckCannotParkWhenNoPlacesTest() {
        Parking parking = new CityParking(0, 0);
        Vehicle truck = new Truck(TRUCK_SIZE);
        assertFalse(parking.park(truck));
    }

    @Test
    @DisplayName("Грузовик не паркуется на легковой парковке, если нет 3 подряд мест.")
    void truckCannotParkWhenCarPlacesNotConsecutiveTest() {
        Parking parking = new CityParking(TRUCK_SIZE, 0);
        Vehicle car1 = new Car();
        Vehicle car2 = new Car();
        Vehicle truck = new Truck(TRUCK_SIZE);
        assertTrue(parking.park(car1));
        assertTrue(parking.park(car2));
        assertFalse(parking.park(truck));
    }

    @Test
    @DisplayName("Успешная парковка легковой машины, если есть место.")
    void carCanParkWhenEnoughPlacesTest() {
        Parking parking = new CityParking(1, 0);
        Vehicle car = new Car();
        assertTrue(parking.park(car));
    }

    @Test
    @DisplayName("Успешная парковка грузовика, если есть место.")
    void truckCanParkWhenEnoughTruckPlacesTest() {
        Parking parking = new CityParking(0, 1);
        Vehicle truck = new Truck(TRUCK_SIZE);
        assertTrue(parking.park(truck));
    }

    @Test
    @DisplayName("Успешная парковка грузовика на парковке для легковых машин.")
    void truckCanParkWhenEnoughCarPlacesTest() {
        Parking parking = new CityParking(TRUCK_SIZE, 0);
        Vehicle truck = new Truck(TRUCK_SIZE);
        assertTrue(parking.park(truck));
    }

    @Test
    @DisplayName("Невозможно запарковать вторую легковую машину, если недостаточно мест.")
    void secondCarCannotParkIfCapacityIs1Test() {
        Parking parking = new CityParking(1, 0);
        Vehicle car1 = new Car();
        Vehicle car2 = new Car();
        assertTrue(parking.park(car1));
        assertFalse(parking.park(car2));
    }

    @Test
    @DisplayName("Невозможно запарковать второй грузовик, если недостаточно мест.")
    void secondTruckCannotParkIfCapacityIs3Test() {
        Parking parking = new CityParking(1, 1);
        Vehicle truck1 = new Truck(TRUCK_SIZE);
        Vehicle truck2 = new Truck(TRUCK_SIZE);
        assertTrue(parking.park(truck1));
        assertFalse(parking.park(truck2));
    }

    @Test
    @DisplayName("Успешное освобождение парковки для легковой машины.")
    void carUnparkedTest() {
        Parking parking = new CityParking(1, 0);
        Vehicle car = new Car();
        assertTrue(parking.park(car));
        assertTrue(parking.unpark(car));
        assertTrue(parking.park(car));
    }

    @Test
    @DisplayName("Успешное освобождение парковки для грузовиков.")
    void truckUnparkedTest() {
        Parking parking = new CityParking(0, 1);
        Vehicle truck = new Truck(TRUCK_SIZE);
        assertTrue(parking.park(truck));
        assertTrue(parking.unpark(truck));
        assertTrue(parking.park(truck));
    }

    @Test
    @DisplayName("Успешное освобождение легковой парковки с грузовиком.")
    void truckUnparkedFromCarsParkingTest() {
        Parking parking = new CityParking(TRUCK_SIZE, 0);
        Vehicle truck = new Truck(TRUCK_SIZE);
        assertTrue(parking.park(truck));
        assertTrue(parking.unpark(truck));
        assertTrue(parking.park(truck));
    }
}
