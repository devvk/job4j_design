package ru.job4j.algo.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Brackets {

    public boolean isValid(String s) {
        Map<Character, Character> map = Map.of(
                ')', '(',
                '}', '{',
                ']', '['
        );
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
                continue;
            }
            Character expected = map.get(c);
            if (expected == null || stack.isEmpty() || stack.pop() != expected) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
