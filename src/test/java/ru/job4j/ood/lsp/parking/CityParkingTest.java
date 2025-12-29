package ru.job4j.ood.lsp.parking;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.parking.model.Car;
import ru.job4j.ood.lsp.parking.model.Truck;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CityParkingTest {

    @Test
    void carCannotParkWhenNoPlacesTest() {
        Parking parking = new CityParking(0, 0);
        Vehicle v1 = new Car();
        assertFalse(parking.park(v1));
    }

    /*@Test
    void carCanParkWhenEnoughPlacesTest() {
        Parking parking = new CityParking(1, 0);
        Vehicle v1 = new Car();
        assertTrue(parking.park(v1));
    }*/

    @Test
    void truckCannotParkWhenNoPlacesTest() {
        Parking parking = new CityParking(0, 0);
        Vehicle v1 = new Truck(2);
        assertFalse(parking.park(v1));
    }

    /*@Test
    void truckCanParkWhenEnoughTruckPlacesTest() {
        Parking parking = new CityParking(0, 2);
        Vehicle v1 = new Truck(2);
        assertTrue(parking.park(v1));
    }

    @Test
    void truckCanParkWhenEnoughCarPlacesTest() {
        Parking parking = new CityParking(2, 0);
        Vehicle v1 = new Truck(2);
        assertTrue(parking.park(v1));
    }

    @Test
    void secondCarCannotParkIfCapacityIs1Test() {
        Parking parking = new CityParking(1, 0);
        Vehicle v1 = new Car();
        Vehicle v2 = new Car();
        assertTrue(parking.park(v1));
        assertFalse(parking.park(v2));
    }

    @Test
    void secondTruckCannotParkIfCapacityIs3Test() {
        Parking parking = new CityParking(1, 3);
        Vehicle v1 = new Truck(2);
        Vehicle v2 = new Truck(3);
        assertTrue(parking.park(v1));
        assertFalse(parking.park(v2));
    }

    @Test
    void carUnparkedTest() {
        Parking parking = new CityParking(1, 0);
        Vehicle v1 = new Car();
        assertTrue(parking.park(v1));
        assertTrue(parking.unpark(v1));
        assertTrue(parking.park(v1));
    }

    @Test
    void truckUnparkedTest() {
        Parking parking = new CityParking(1, 2);
        Vehicle v1 = new Truck(2);
        assertTrue(parking.park(v1));
        assertTrue(parking.unpark(v1));
        assertTrue(parking.park(v1));
    }*/
}
