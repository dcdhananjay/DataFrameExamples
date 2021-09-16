package com.example.designpatterns;

import java.util.concurrent.Semaphore;

class Singleton7exp {

    private static Singleton7exp obj;

    private Singleton7exp() {
        System.out.println("Instance created");
    }

    public static Singleton7exp getInstance() {
        if (obj == null) {
            obj = new Singleton7exp();
        }
        return obj;
    }
}

class MyThread extends Thread {

    Semaphore sem;

    public MyThread(Semaphore sem) {
        this.sem = sem;
    }

    @Override
    public void run() {
        try {
            sem.acquire();
            Singleton7exp obj1 = Singleton7exp.getInstance();
            System.out.println("Current Thread Name : " + Thread.currentThread().getName() + " - " + obj1);
            sem.release();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}

public class Singleton7 {

    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(1);
        MyThread t1 = new MyThread(sem);
        MyThread t2 = new MyThread(sem);
        t1.start();
        t2.start();
    }
}
