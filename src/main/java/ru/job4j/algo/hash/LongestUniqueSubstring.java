package ru.job4j.algo.hash;

import java.util.HashMap;
import java.util.Map;

public class LongestUniqueSubstring {

    /**
     * Метод находит самую длинную подстроку состоящую из уникальных элементов.
     *
     * @param str строка
     * @return подстрока
     */
    public static String longestUniqueSubstring(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        Map<Character, Integer> lastPos = new HashMap<>();
        int left = 0, bestLen = 0, bestStart = 0;

        for (int right = 0; right < str.length(); right++) {
            char ch = str.charAt(right);

            Integer last = lastPos.get(ch);
            if (last != null && last >= left) {
                // сдвигаем окно
                left = last + 1;
            }
            lastPos.put(ch, right);
            int len = right - left + 1;
            if (len > bestLen) {
                bestLen = len;
                bestStart = left;
            }
        }
        return str.substring(bestStart, bestStart + bestLen);
    }
}
