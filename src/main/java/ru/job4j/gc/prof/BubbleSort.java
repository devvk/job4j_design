package ru.job4j.gc.prof;

/**
 * Сортировка пузырьком (Время: O(n2).
 * Сама медленная из представленных.
 * Алгоритм требует многократных проходов по сортируемому массиву,
 * при которых в конце массива формируется отсортированный участок,
 * причём с каждым проходом его длина увеличивается как минимум на единицу.
 */
public class BubbleSort implements Sort {

    @Override
    public boolean sort(Data data) {
        int[] array = data.getClone();
        sort(array);
        return true;
    }

    public void sort(int[] array) {
        int out, in;
        for (out = array.length - 1; out >= 1; out--) {
            for (in = 0; in < out; in++) {
                if (array[in] > array[in + 1]) {
                    swap(array, in, in + 1);
                }
            }
        }
    }

    public void swap(int[] array, int in, int in1) {
        int temp = array[in];
        array[in] = array[in1];
        array[in1] = temp;
    }
}
