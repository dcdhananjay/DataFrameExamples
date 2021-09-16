package com.example.designpatterns;

public class Singleton2 {

    private static volatile Singleton2 obj;

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        Singleton2 localobj = obj;
        if (localobj == null) {
            synchronized (Singleton2.class) {
                if (localobj == null) {
                    localobj = obj = new Singleton2();
                }
            }
        }
        return localobj;
    }
}
