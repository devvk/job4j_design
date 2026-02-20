package ru.job4j.algo.slidingwindow;

public class SlidingWindow {

    public static int findMaxSum(int[] array, int windowSize) {
        if (windowSize > array.length || windowSize <= 0) {
            throw new IllegalArgumentException("Invalid window size!");
        }

        // инициализация первого окна
        int windowSum = 0;
        for (int i = 0; i < windowSize; i++) {
            windowSum += array[i];
        }
        int maxSum = windowSum;

        // скольжение окна по массиву
        for (int i = windowSize; i < array.length; i++) {
            windowSum += array[i] - array[i - windowSize];
            maxSum = Math.max(maxSum, windowSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println(findMaxSum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 3));
    }
}
