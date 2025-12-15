package ru.job4j.kiss.fool;

import java.util.Scanner;

public class Fool {

    public static void main(String[] args) {
        System.out.println("Игра FizzBuzz.");
        var input = new Scanner(System.in);

        int n = 1;
        while (n < 100) {
            // ход компьютера
            System.out.println(fizzBuzz(n));

            // ход игрока
            n++;
            String answer = input.nextLine();
            if (!fizzBuzz(n).equals(answer)) {
                System.out.println("Ошибка! Начинаем сначала.");
                n = 1;
                continue;
            }

            // следующий ход компьютера
            n++;
        }
    }

    protected static String fizzBuzz(int n) {
        if (n % 15 == 0) {
            return "FizzBuzz";
        } else if (n % 3 == 0) {
            return "Fizz";
        } else if (n % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(n);
    }
}
