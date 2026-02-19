package ru.job4j.algo.sort;

import java.util.Arrays;

public class MergeSort {

    public static int[] mergeSort(int[] array) {
        int n = array.length;
        if (n <= 1) {
            return array;
        }
        int[] left = mergeSort(Arrays.copyOfRange(array, 0, n / 2));
        int[] right = mergeSort(Arrays.copyOfRange(array, n / 2, n));
        return merge(left, right);
    }

    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int indexLeft = 0, indexRight = 0;
        for (int i = 0; i < result.length; i++) {
            if (indexLeft >= left.length) {
                result[i] = right[indexRight++];
            } else if (indexRight >= right.length) {
                result[i] = left[indexLeft++];
            } else if (left[indexLeft] <= right[indexRight]) {
                result[i] = left[indexLeft++];
            } else {
                result[i] = right[indexRight++];
            }
        }
        return result;
    }
}
