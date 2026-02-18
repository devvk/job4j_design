package ru.job4j.algo.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class IntervalMerger {
    /**
     * Объединяет пересекающиеся интервалы. Сложность: O(n log n).
     * Критерий пересечения: last = [a, b], curr = [c, d] (при a <= c после сортировки).
     * 1. Пересечение есть, если c <= b
     * 2. Тогда новый конец: b = max(b, d)
     * 3. Иначе — это новый независимый интервал.
     *
     * @param intervals массив интервалов
     * @return массив слитых интервалов
     */
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(value -> value[0]));

        List<int[]> result = new ArrayList<>();
        int[] last = intervals[0];
        result.add(last);
        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            if (curr[0] <= last[1]) {
                last[1] = Math.max(last[1], curr[1]);
            } else {
                last = curr;
                result.add(last);
            }
        }
        return result.toArray(new int[result.size()][]);
    }
}
