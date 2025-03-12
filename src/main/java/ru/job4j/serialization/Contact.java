package ru.job4j.serialization;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

public class Contact implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int zipCode;
    private final String phone;

    public Contact(int zipCode, String phone) {
        this.zipCode = zipCode;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{"
                + "zipCode=" + zipCode
                + ", phone=" + phone
                + "}";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Contact contact = (Contact) object;
        return zipCode == contact.zipCode && Objects.equals(phone, contact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, phone);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Contact c1 = new Contact(12345, "1234567890");
        final Contact c2;

        File tempFile = Files.createTempFile(null, null).toFile();
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            output.writeObject(c1);
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(tempFile))) {
            c2 = (Contact) input.readObject();
        }

        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);
        System.out.println(c1.hashCode() == c2.hashCode());
    }
}
