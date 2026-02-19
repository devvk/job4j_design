package ru.job4j.algo.sort;

import java.util.Arrays;

public class QuickSort {

    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = partition(array, start, end);
        quickSort(array, start, pivot - 1);
        quickSort(array, pivot + 1, end);
    }

    private static int partition(int[] array, int start, int end) {
        int pivot = array[start];
        int left = start + 1;
        int right = end;

        while (true) {
            // если меньше, пропускаем
            while (left < end && array[left] < pivot) {
                left++;
            }
            // если больше, пропускаем
            while (array[right] > pivot) {
                right--;
            }
            if (left >= right) {
                break;
            }
            swap(array, left++, right--);
        }
        swap(array, start, right);
        return right;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {0, 5, -2, 7, 3, -2};
        System.out.println(Arrays.toString(array));
        quickSort(array);
        System.out.println(Arrays.toString(array));
    }
}
