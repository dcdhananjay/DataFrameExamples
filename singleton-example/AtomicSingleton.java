package com.example.designpatterns;

import java.util.concurrent.atomic.AtomicBoolean;

class AtomicSingletonExp {

    private static AtomicSingletonExp instance;
    static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private AtomicSingletonExp() {

    }

    public static AtomicSingletonExp getInstance() {

        if (instance != null) {
            return instance;
        }

        while (instance == null && !isInitialized.compareAndSet(false, true)) {

        }

        if (instance == null) {
            instance = new AtomicSingletonExp();
        }

        return instance;
    }
}

public class AtomicSingleton {

    public static void main(String[] args) {
        for (int i = 1; i <= 10; ++i) {
            Thread t2 = new Thread(() -> {
                AtomicSingletonExp obj = AtomicSingletonExp.getInstance();
                System.out.println(Thread.currentThread().getName() + " - " + obj);
            });
            t2.start();

        }
    }
}
