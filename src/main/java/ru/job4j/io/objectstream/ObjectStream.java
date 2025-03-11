package ru.job4j.io.objectstream;

import java.io.*;

public class ObjectStream {
    public static void main(String[] args) {
        String file = "data/serialized.dat";
        Car car = new Car("Audi", "Q5", 2025);
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
             ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            output.writeObject(car);
            Car serialized = (Car) input.readObject();
            System.out.println(serialized);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Ошибка при работе с файлом!", e);
        }
    }
}
