package ru.job4j.collection.recursion;

public class Recursion {

    public int sum(int summary, int index) {
        if (index > 0) {
            summary += index;
            index--;
            summary = sum(summary, index);
        }
        return summary;
    }

    public int loop(int summary, int index) {
        for (int i = index; i > 0; i--) {
            summary += i;
        }
        return summary;
    }

    /**
     * n! = n × (n-1)!
     * 0! = 1
     */
    public long factorialLoop(int f) {
        long result = 1L;
        if (f > 0) {
            for (int i = 1; i <= f; i++) {
                result = result * i;
            }
        }
        return result;
    }

    /**
     * n! = n × (n-1)!
     * 0! = 1
     */
    public long factorialRecursion(long index) {
        if (index == 0) {
            return 1;
        }
        return index * factorialRecursion(index - 1);
    }

    /**
     * f(n) = f(n-1) + f(n-2)
     * f(0) = 0
     * f(1) = 1
     */
    public long fibonacciLoop(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        long f1 = 1;
        long f2 = 1;
        long result = 0;
        for (int i = 3; i <= n; i++) {
            result = f1 + f2;
            f1 = f2;
            f2 = result;
        }
        return result;
    }

    /**
     * f(n) = f(n-1) + f(n-2)
     * f(0) = 0
     * f(1) = 1
     */
    public long fibonacciRecursion(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return fibonacciRecursion(n - 1) + fibonacciRecursion(n - 2);
    }

    public static void main(String[] args) {
        Recursion recursion = new Recursion();
        System.out.println(recursion.fibonacciLoop(5));
        System.out.println(recursion.fibonacciRecursion(5));
    }
}
