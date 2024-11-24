package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Balancer {

    /**
     * Метод равномерно распределяет данные из итератора по переданным ему спискам.
     *
     * @param nodes  список со списками
     * @param source итератор с данными
     */
    public static void split(List<ArrayList<Integer>> nodes, Iterator<Integer> source) {
        if (nodes == null || nodes.isEmpty() || source == null) {
            throw new IllegalArgumentException("Nodes and source cannot be null or empty.");
        }

        int index = 0;
        while (source.hasNext()) {
            List<Integer> currentNode = nodes.get(index++);
            currentNode.add(source.next());
            if (index == nodes.size()) {
                index = 0;
            }
        }
    }
}
