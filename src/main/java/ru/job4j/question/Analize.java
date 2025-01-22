package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analize {

    /**
     * Определяет разницу между начальным и измененным состояниями множества.
     *
     * @param previous Начальное множество.
     * @param current  Текущее множество.
     * @return Объект Info с разницей между состояниями.
     */
    public static Info diff(Set<User> previous, Set<User> current) {
        Map<Integer, String> prevMap = new HashMap<>();
        int added = 0, changed = 0;
        for (User user : previous) {
            prevMap.put(user.id(), user.name());
        }
        for (User currUser : current) {
            int currUserId = currUser.id();
            if (!prevMap.containsKey(currUserId)) {
                added++;
            } else {
                if (!prevMap.get(currUserId).equals(currUser.name())) {
                    changed++;
                }
                prevMap.remove(currUserId);
            }
        }
        return new Info(added, changed, prevMap.size());
    }

}
