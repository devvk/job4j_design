package ru.job4j.bits;

public class TwosComplementDemo {
    public static void main(String[] args) {
        int pos = 5;
        int neg = ~5 + 1;

        System.out.println("pos: " + Integer.toBinaryString(pos));
        System.out.println("neg: " + Integer.toBinaryString(neg));
    }
}
