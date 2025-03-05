package ru.job4j.io;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ByteArrayStream {
    public static void main(String[] args) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.writeBytes("Message".getBytes());
        System.out.println(outputStream);

        byte[] byteArray = outputStream.toByteArray();
        System.out.println(Arrays.toString(byteArray));
    }
}
