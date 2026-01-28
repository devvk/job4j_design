package ru.job4j.algo;

import java.util.Arrays;

public class SmallestRangeFinder {

    /**
     * Возвращает массив из двух целых чисел, представляющих наименьший диапазон
     * с k различными элементами в массиве nums.
     *
     * @param nums отсортированный массив целых чисел
     * @param k    минимальное количество различных элементов в диапазоне
     *             массив из двух индексов [left, right] либо null, если такой диапазон не существует
     */
    public static int[] findSmallestRange(int[] nums, int k) {
        int[] result = null;

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3};
        int k = 3;
        int[] result = findSmallestRange(nums, k);
        if (result != null) {
            System.out.println("Наименьший диапазон с " + k + " различными элементами: " + Arrays.toString(result));
        } else {
            System.out.println("Такой диапазон не существует.");
        }
    }
}
