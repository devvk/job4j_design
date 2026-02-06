package ru.job4j.algo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FindAnagram {

    private static Set<Character> toSet(String str) {
        return str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public static List<Integer> findAnagrams(String str, String pattern) {
        List<Integer> result = new ArrayList<>();
        Set<Character> anagrams = toSet(pattern);
        int windowSize = pattern.length();
        for (int i = 0; i <= str.length() - windowSize; i++) {
            String window = str.substring(i, i + windowSize);
            if (anagrams.equals(toSet(window))) {
                result.add(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> anagramIndices = findAnagrams("cbaebabacd", "abc");
        System.out.println(anagramIndices);
    }
}
