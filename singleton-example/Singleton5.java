package com.example.designpatterns;

import java.util.concurrent.locks.ReentrantLock;

class Singleton5Exp {

    private static Singleton5Exp obj;
    private static final ReentrantLock lock = new ReentrantLock();

    private Singleton5Exp() {
        System.out.println("Instance created");
    }

    public static Singleton5Exp getInstance() {
        lock.lock();
        if (obj == null) {
            obj = new Singleton5Exp();
        }
        lock.unlock();
        return obj;
    }
}

public class Singleton5 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                Singleton5Exp obj1 = Singleton5Exp.getInstance();
                System.out.println(obj1);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                Singleton5Exp obj2 = Singleton5Exp.getInstance();
                System.out.println(obj2);
            }
        });
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                Singleton5Exp obj3 = Singleton5Exp.getInstance();
                System.out.println(obj3);
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
