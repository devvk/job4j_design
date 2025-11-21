package ru.job4j.clone;

public class CloneByConstructor {
    int x;
    int y;

    public CloneByConstructor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CloneByConstructor(CloneByConstructor other) {
        this(other.x, other.y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void main(String[] args) {
        CloneByConstructor o1 = new CloneByConstructor(5, 10);
        CloneByConstructor clone = new CloneByConstructor(o1);
        clone.setX(15);
        clone.setY(20);
        System.out.printf("Исходный объект: (%d, %d)%n", o1.getX(), o1.getY());
        System.out.printf("Клонированный объект: (%d, %d)%n", clone.getX(), clone.getY());
    }
}
