package ru.job4j.algo.sort;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuickListTest {

    private record User(Integer id, String name) {
        public int getId() {
            return id;
        }
    }

    @Test
    void whenSortThenOk() {
        User user1 = new User(1, "11");
        User user2 = new User(1, "22");
        User user3 = new User(3, "33");
        User user4 = new User(-4, "44");
        User user5 = new User(4, "55");
        User user6 = new User(6, "66");

        Comparator<User> comparator = Comparator.comparing(User::getId);

        List<User> listUser = new ArrayList<>();
        listUser.add(user3);
        listUser.add(user5);
        listUser.add(user1);
        listUser.add(user4);
        listUser.add(user6);
        listUser.add(user2);

        QuickList.quickSort(listUser, comparator);
        List<Integer> result = listUser.stream().map(User::getId).toList();
        assertThat(result).containsExactly(-4, 1, 1, 3, 4, 6);
    }

    @Test
    void whenReverseOrderThenOk() {
        Comparator<Integer> comparator = Comparator.reverseOrder();
        List<Integer> list = new ArrayList<>();
        list.add(-2);
        list.add(-8);
        list.add(1);
        list.add(0);
        list.add(10);
        list.add(1);
        list.add(8);
        QuickList.quickSort(list, comparator);
        System.out.println(list);
        assertThat(list).containsExactly(10, 8, 1, 1, 0, -2, -8);
    }

    @Test
    void whenEmptyListThenOk() {
        List<Integer> list = new ArrayList<>();
        QuickList.quickSort(list, Comparator.naturalOrder());
        assertThat(list).isEmpty();
    }

    @Test
    void whenSingleElementThenOk() {
        List<Integer> list = new ArrayList<>(List.of(1));
        QuickList.quickSort(list, Comparator.naturalOrder());
        assertThat(list).containsExactly(1);
    }

    @Test
    void whenAlreadySortedThenOk() {
        List<Integer> list = new ArrayList<>(List.of(-5, -4, 0, 4, 5));
        QuickList.quickSort(list, Comparator.naturalOrder());
        assertThat(list).containsExactly(-5, -4, 0, 4, 5);
    }

    @Test
    void whenAllEqualThenOk() {
        List<Integer> list = new ArrayList<>(List.of(3, 3, 3, 3, 3, 3, 3));
        QuickList.quickSort(list, Comparator.naturalOrder());
        assertThat(list).containsExactly(3, 3, 3, 3, 3, 3, 3);
    }

    @Test
    void whenManyDuplicatesThenOk() {
        List<Integer> list = new ArrayList<>(List.of(0, -1, -1, 2, 2, 2, 0, -1));
        QuickList.quickSort(list, Comparator.naturalOrder());
        assertThat(list).containsExactly(-1, -1, -1, 0, 0, 2, 2, 2);
    }
}
