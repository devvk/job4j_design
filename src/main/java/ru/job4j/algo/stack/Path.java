package ru.job4j.algo.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Path {

    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.removeLast();
                }
            } else if (!part.isEmpty() && !part.equals(".")) {
                stack.addLast(part);
            }
        }

        if (stack.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();
        for (String dir : stack) {
            sb.append('/').append(dir);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Path path = new Path();
        System.out.println(path.simplifyPath("/a/./b/../../c/"));
    }
}
