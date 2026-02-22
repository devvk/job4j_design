package ru.job4j.algo.slidingwindow;

public class CharacterReplacement {

    /**
     * Возвращает максимальную длину подстроки, которую можно сделать состоящей
     * из одной и той же буквы, заменив не более k символов.
     * 	1.	Двигаем right
     * 	2.	Увеличиваем счётчик буквы
     * 	3.	Обновляем maxFreq
     * 	4.	Проверяем — можем ли мы заменить остальные буквы?
     * 	5.	Если нет — двигаем left
     * 	6.	Сохраняем максимальный размер окна
     *
     * @param s строка символов
     * @param k сколько символов нужно заменить чтобы окно стало однородным
     * @return максимальная длина подстроки
     */
    public static int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int left = 0;
        int maxFreqChar = 0;
        int bestLen = 0;

        for (int right = 0; right < s.length(); right++) {
            int idx = s.charAt(right) - 'A';
            freq[idx]++;
            maxFreqChar = Math.max(maxFreqChar, freq[idx]);

            while ((right - left + 1) - maxFreqChar > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            bestLen = Math.max(bestLen, right - left + 1);
        }

        return bestLen;
    }

    public static void main(String[] args) {
        System.out.println(characterReplacement("ABC", 2));
    }

}