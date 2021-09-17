package test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Solution2 {
    static class ThreadExample{
        private final Lock lock;
        private final Condition isOneReady;
        private final Condition isTwoReady;
        int count = 1;
        
        public ThreadExample(){
            lock = new ReentrantLock();
            isOneReady = lock.newCondition();
            isTwoReady = lock.newCondition();
        }
        
        public void printOne() throws InterruptedException{
            try{
                while(true){
                    lock.lock();
                    while(count==2){
                        isOneReady.await();
                    }
                    System.out.println("1");
                    count = 2;
                    isTwoReady.signal();
                    Thread.sleep(1000);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                lock.unlock();
            }
        }
        
        public void printTwo() throws InterruptedException{
            try{
                while(true){
                    lock.lock();
                    while(count==1){
                        isTwoReady.await();
                    }
                    System.out.println("2");
                    count = 1;
                    isOneReady.signal();
                    Thread.sleep(1000);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                lock.unlock();
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
