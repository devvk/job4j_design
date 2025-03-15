package ru.job4j.serialization.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

public class User {
    private final int id;
    private final boolean sex;
    private final String login;
    private final Object object;
    private final String[] statuses;

    public User(int id, boolean sex, String login, Object object, String[] statuses) {
        this.id = id;
        this.sex = sex;
        this.login = login;
        this.object = object;
        this.statuses = statuses;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", sex=" + sex
                + ", login=" + login
                + ", object=" + object
                + ", statuses=" + Arrays.toString(statuses)
                + "}";
    }

    public static void main(String[] args) {

        Gson gson = new GsonBuilder().create();

        final User u1 = new User(1, true, "user1", new Object(), new String[]{"backend", "java"});
        String u1Json = gson.toJson(u1);
        System.out.println(u1Json);
        final User u2 = gson.fromJson(u1Json, User.class);
        System.out.println(u2);
    }
}
