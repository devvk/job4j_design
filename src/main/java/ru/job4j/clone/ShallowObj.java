package ru.job4j.clone;

public class ShallowObj implements Cloneable {
    private int number;

    @Override
    protected ShallowObj clone() throws CloneNotSupportedException {
        return (ShallowObj) super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        ShallowObj o1 = new ShallowObj();
        o1.number = 5;
        ShallowObj o2 = o1.clone();
        o2.number = 10;
        System.out.println(o1 + ": " + o1.number);
        System.out.println(o2 + ": " + o2.number);
    }
}
