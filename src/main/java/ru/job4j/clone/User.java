package ru.job4j.clone;

/**
 * Лучший способ получить копию User — копирующий конструктор + метод copy().
 */
public class User {
    private final String name;
    private final Address address;

    public User(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public User(User other) {
        this.name = other.name;
        this.address = new Address(other.address);
    }

    public User copy() {
        return new User(this);
    }

    @Override
    public String toString() {
        return "User{"
                + "name=" + name
                + ", address=" + address
                + "}";
    }

    public static class Address {
        private String street;
        private int nr;

        public Address(String street, int nr) {
            this.street = street;
            this.nr = nr;
        }

        public Address(Address other) {
            this(other.street, other.nr);
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setNr(int nr) {
            this.nr = nr;
        }

        @Override
        public String toString() {
            return "Address{"
                    + "street=" + street
                    + ", nr=" + nr
                    + "}";
        }
    }

    public static void main(String[] args) {
        User user = new User("Alex", new Address("Berliner Str.", 1000));
        User copy = user.copy();
        copy.address.setStreet("Frankfurter Str.");
        copy.address.setNr(1);

        System.out.println(user);
        System.out.println(copy);
    }
}