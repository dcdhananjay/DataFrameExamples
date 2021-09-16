package com.example.designpatterns;

enum Singleton {
    INSTANCE;
    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

public class Singleton4 {

    public static void main(String[] args) {
        Singleton singleton1 = Singleton.INSTANCE;

        System.out.println(singleton1.getValue());
        singleton1.setValue(2);
        System.out.println(singleton1.getValue());

        Singleton singleton2 = Singleton.INSTANCE;
        System.out.println(singleton2.getValue());
        singleton1.setValue(5);
        System.out.println(singleton2.getValue());
        System.out.println(singleton1.getValue());
    }
}
