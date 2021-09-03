
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue {

    final Lock lock = new ReentrantLock();

    final Condition produceCondtion = lock.newCondition();
    final Condition consumeCondition = lock.newCondition();

    final Object[] array = new Object[5];
    int head, tail;
    int count;

    public void put(Object obj) {
        lock.lock();
        try {
            while (array.length == count) {
                produceCondtion.await();
            }
            array[tail] = obj;
            System.out.println("Adding - " + obj);
            tail++;
            if (tail == array.length) {
                tail = 0;
            }
            count++;
            consumeCondition.signal();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object get() {
        lock.lock();
        try {
            while (count == 0) {
                consumeCondition.await();
            }

            Object obj = array[head];
            System.out.println("Removing - " + obj);
            head++;
            if (head == array.length) {
                head = 0;
            }
            count--;
            produceCondtion.signal();
            return obj;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}
