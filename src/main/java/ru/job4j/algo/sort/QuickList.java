package ru.job4j.algo.sort;

import java.util.Comparator;
import java.util.List;

public class QuickList {

    public static <T> void quickSort(List<T> sequence, Comparator<T> comparator) {
        quickSort(sequence, 0, sequence.size() - 1, comparator);
    }

    private static <T> void quickSort(List<T> sequence, int start, int end, Comparator<T> comparator) {
        if (start >= end) {
            return;
        }
        int pivot = breakPartition(sequence, start, end, comparator);
        quickSort(sequence, start, pivot - 1, comparator);
        quickSort(sequence, pivot + 1, end, comparator);
    }

    private static <T> int breakPartition(List<T> list, int start, int end, Comparator<T> comparator) {
        T pivot = list.get(start);
        int left = start + 1;
        int right = end;
        while (true) {
            while (left < end && comparator.compare(list.get(left), pivot) < 0) {
                left++;
            }
            while (comparator.compare(list.get(right), pivot) > 0) {
                right--;
            }
            if (left >= right) {
                break;
            }
            swap(list, left++, right--);
        }
        swap(list, start, right);
        return right;
    }

    private static <T> void swap(List<T> list, int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }
}
