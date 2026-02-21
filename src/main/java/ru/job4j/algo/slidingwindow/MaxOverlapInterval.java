package ru.job4j.algo.slidingwindow;

import java.util.*;

public class MaxOverlapInterval {

    /**
     * Находит минимальный отрезок, который пересекается с максимальным количеством интервалов.
     * Алгоритм:
     * 1. Сортируем интервалы по start.
     * 2. Идём слева направо; в heap храним активные интервалы, наверху минимальный end.
     * 3. Для текущего start общий отрезок пересечения активных: [start, минимальный end].
     * 4. Обновляем лучший отрезок по максимуму overlap или при равенстве берём более короткий.
     *
     * @param intervals список интервалов
     * @return [start, end] лучшего отрезка или {-1, -1}, если список пуст
     */
    public static int[] findMaxOverlapInterval(List<Interval> intervals) {
        if (intervals.isEmpty()) {
            return new int[]{-1, -1};
        }
        intervals.sort(Comparator.comparingInt((Interval i) -> i.start).thenComparingInt(i -> i.end));
        // Минимальный end всегда наверху
        var activeIntervals = new PriorityQueue<Interval>(Comparator.comparingInt(i -> i.end));
        int maxOverlap = 1;
        int maxStart = intervals.getFirst().start;
        int maxEnd = intervals.getFirst().end;

        for (Interval interval : intervals) {
            int currentStart = interval.start;
            // Удаляем интервалы, которые уже закончились до currentStart
            while (!activeIntervals.isEmpty() && activeIntervals.peek().end <= currentStart) {
                activeIntervals.poll();
            }
            activeIntervals.add(interval);
            int overlap = activeIntervals.size();
            // Правый край общего пересечения - минимальный end среди активных
            int currentEnd = Objects.requireNonNull(activeIntervals.peek()).end;

            // [currentStart, currentEnd] должен быть ненулевой длины
            if (currentStart < currentEnd) {
                int bestLen = maxEnd - maxStart;
                int curLen = currentEnd - currentStart;
                // Если больше пересечений или одинаково, но меньше длина, обновляем
                if (overlap > maxOverlap || (overlap == maxOverlap && curLen < bestLen)) {
                    maxOverlap = overlap;
                    maxStart = currentStart;
                    maxEnd = currentEnd;
                }
            }
        }
        return new int[]{maxStart, maxEnd};
    }

    public static void main(String[] args) {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 4));
        intervals.add(new Interval(2, 6));
        intervals.add(new Interval(3, 5));
        intervals.add(new Interval(7, 8));

        int[] result = findMaxOverlapInterval(intervals);

        System.out.println("Interval that overlaps the maximum number of intervals: [" + result[0] + ", " + result[1] + "]");
    }
}
