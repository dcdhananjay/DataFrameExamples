package com.example.designpatterns;

class SingletonEx3 {

    private SingletonEx3() {

    }

    private static class SingletonHelper {

        private static final SingletonEx3 INSTANCE = new SingletonEx3();
    }

    public static SingletonEx3 getInstance() {
        return SingletonHelper.INSTANCE;
    }
}

public class Singleton3 {

    public static void main(String[] args) {
        SingletonEx3 obj1 = SingletonEx3.getInstance();

        SingletonEx3 obj2 = SingletonEx3.getInstance();

        if (obj1 == obj2) {
            System.out.println("Objects are same");
        } else {
            System.out.println("Objects are not same");
        }
    }
}
