package ru.job4j.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Заданы время посещения клиентов банка в виде списка интервалов [[begin, end], ...], где
 * begin - время прибытия клиента,
 * end - время ухода клиента.
 * Необходимо вычислить, когда в банке находилось наибольшее количество клиентов.
 */
public class BankMaxLoadTime {

    public static int[] findMaxLoadTime(List<int[]> visitTimes) {
        if (visitTimes.isEmpty()) {
            return new int[]{0, 0};
        }

        List<Event> list = new ArrayList<>(visitTimes.size() * 2);
        // visitTime[пришёл, ушёл]
        for (int[] visitTime : visitTimes) {
            list.add(new Event(visitTime[0], EventType.ARRIVAL));
            list.add(new Event(visitTime[1], EventType.DEPARTURE));
        }
        Collections.sort(list);
        int currentLoad = 0, maxLoad = 0, bestStart = 0, bestEnd = 0;
        boolean inMax = false;
        for (Event event : list) {
            currentLoad += event.type == EventType.ARRIVAL ? 1 : -1;
            if (currentLoad > maxLoad) {
                maxLoad = currentLoad;
                bestStart = event.time;
                inMax = true;
                continue;
            }
            // выход из максимума
            if (inMax && currentLoad < maxLoad) {
                bestEnd = event.time;
                inMax = false;
            }
        }
        return new int[]{bestStart, bestEnd};
    }

    static class Event implements Comparable<Event> {
        int time;
        EventType type;

        Event(int time, EventType type) {
            this.time = time;
            this.type = type;
        }

        @Override
        public int compareTo(Event other) {
            int cmp = Integer.compare(this.time, other.time);
            if (cmp != 0) {
                return cmp;
            }
            // [start, end] (включительно)
            return this.type.compareTo(other.type);
        }
    }

    enum EventType {
        ARRIVAL, DEPARTURE
    }
}
