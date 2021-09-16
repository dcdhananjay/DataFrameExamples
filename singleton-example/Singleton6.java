package com.example.designpatterns;

class Singleton6Exp implements Runnable {

    private static Singleton6Exp obj;
    private static boolean lockBoolean = false;

    private Singleton6Exp() {
        System.out.println("Instance created");
    }

    public static Singleton6Exp getInstance() {
        if (obj == null) {
            obj = new Singleton6Exp();
        }
        lockBoolean = false;
        return obj;
    }

    @Override
    public void run() {

        while (lockBoolean) {

        }

        lockBoolean = true;
        Singleton6Exp obj1 = Singleton6Exp.getInstance();
        System.out.println(Thread.currentThread().getName() + " - " + obj1);
    }
}

public class Singleton6 {

    public static void main(String[] args) {
        Thread t1 = new Thread(Singleton6Exp.getInstance());
        Thread t2 = new Thread(Singleton6Exp.getInstance());
        Thread t3 = new Thread(Singleton6Exp.getInstance());
        Thread t4 = new Thread(Singleton6Exp.getInstance());
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
