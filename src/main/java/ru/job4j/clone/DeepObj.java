package ru.job4j.clone;

/**
 * Глубокое копирование создаёт полностью независимую копию объекта.
 * Изменения в клоне не затрагивают оригинал.
 * <p>
 * Правила глубокого копирования:
 * - Примитивные поля копируются автоматически.
 * - Каждый ссылочный член должен поддерживать клонирование
 * (реализовать Cloneable и переопределить clone()).
 * - Если член не поддерживает клонирование, в clone() нужно вручную
 * создать новый объект и скопировать в него данные.
 */
public class DeepObj implements Cloneable {
    int num;
    InnerObj innerObj;

    @Override
    protected DeepObj clone() throws CloneNotSupportedException {
        DeepObj deepObj = (DeepObj) super.clone();
        deepObj.innerObj = innerObj.clone();
        return deepObj;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        DeepObj o1 = new DeepObj();
        o1.num = 5;
        InnerObj innerObj1 = new InnerObj();
        innerObj1.num = 15;
        o1.innerObj = innerObj1;

        DeepObj o2 = o1.clone();
        o2.num = 25;
        o2.innerObj.num = 35;

        System.out.println("Исходный объект: " + o1.num);
        System.out.println("Исходный объект: " + o1.innerObj.num);
        System.out.println("Клонированный объект: " + o2.num);
        System.out.println("Клонированный объект: " + o2.innerObj.num);
    }
}
