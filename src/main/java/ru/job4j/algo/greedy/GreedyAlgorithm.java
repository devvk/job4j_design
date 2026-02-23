package ru.job4j.algo.greedy;

import java.util.ArrayList;
import java.util.List;

public class GreedyAlgorithm {
    public static List<Integer> getChange(int amount) {
        int[] coins = {50, 25, 10, 5, 1};
        List<Integer> result = new ArrayList<>();

        for (int coin : coins) {
            while (amount >= coin) {
                amount -= coin;
                result.add(coin);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int amount = 93; /* Пример суммы */
        List<Integer> change = getChange(amount);
        System.out.println("Сдача для " + amount + " : " + change);
    }
}
