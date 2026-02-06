package ru.job4j.algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SmallestRangeFinder {

    /**
     * Находит минимальный подмассив с k различными элементами.
     *
     * @param nums отсортированный массив целых чисел
     * @param k    количество различных элементов
     * @return индексы [left, right] (включительно) или null, если диапазона нет
     */
    public static int[] findSmallestRange(int[] nums, int k) {
        int[] result = null;
        int left = 0, minLen = Integer.MAX_VALUE;
        Map<Integer, Integer> unique = new HashMap<>();

        for (int right = 0; right < nums.length; right++) {
            unique.merge(nums[right], 1, Integer::sum);

            while (unique.size() == k) {
                int len = right - left + 1;
                if (len < minLen) {
                    minLen = len;
                    result = new int[]{left, right};
                    // Ранний выход т.к. короче быть не может
                    if (minLen == k) {
                        return result;
                    }
                }
                unique.merge(nums[left], -1, Integer::sum);
                if (unique.get(nums[left]) == 0) {
                    unique.remove(nums[left]);
                }
                left++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9};
        int k = 3;
        int[] result = findSmallestRange(nums, k);
        if (result != null) {
            System.out.println("Наименьший диапазон с " + k + " различными элементами: " + Arrays.toString(result));
        } else {
            System.out.println("Такой диапазон не существует.");
        }
    }
}
