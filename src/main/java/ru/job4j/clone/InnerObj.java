package ru.job4j.clone;

public class InnerObj implements Cloneable {
    int num;

    @Override
    protected InnerObj clone() throws CloneNotSupportedException {
        return (InnerObj) super.clone();
    }
}
