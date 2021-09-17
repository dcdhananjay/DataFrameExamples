package test;


public class Solution {
    public static class ThreadExample{
        
        int count = 1;
        
        public void printOne() throws InterruptedException{
            while(true){
                synchronized(this){
                    while(count==2){
                        wait();
                    }
                    System.out.println("1");
                    count = 2;
                    notify();
                    Thread.sleep(1000);
                }
            }
        }
        public void printTwo() throws InterruptedException{
            while(true){
                synchronized(this){
                    while(count==1){
                        wait();
                    }
                    System.out.println("2");
                    count=1;
                    notify();
                    Thread.sleep(1000);
                }
            }
        }
    }
    
    public static void main(String[] args){
        final ThreadExample exp = new ThreadExample();
        
        Thread t1 = new Thread(()->{
            try{
                exp.printOne();
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            try{
                exp.printTwo();
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}
