package ru.job4j.serialization.json.jsonjava;

import org.json.JSONObject;

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

    public int getId() {
        return id;
    }

    public boolean getSex() {
        return sex;
    }

    public String getLogin() {
        return login;
    }

    public Object getObject() {
        return object;
    }

    public String[] getStatuses() {
        return statuses;
    }

    public static void main(String[] args) {
        final User user = new User(1, true, "user1", new Object(), new String[]{"backend", "java"});
        JSONObject jsonObjectUser = new JSONObject();
        jsonObjectUser.put("id", user.getId());
        jsonObjectUser.put("sex", user.getSex());
        jsonObjectUser.put("login", user.getLogin());
        jsonObjectUser.put("object", user.getObject());
        jsonObjectUser.put("statuses", user.getStatuses());

        System.out.println("POJO User to JSONObject: " + jsonObjectUser);
        System.out.println("POJO User to JSONObject: " + new JSONObject(user));
    }
}
