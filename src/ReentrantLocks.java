import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocks {

    static int counter = 0;
    Lock lock = new ReentrantLock(true);

    public synchronized  void increment() throws InterruptedException {

        try{
            lock.lock();
            counter++;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        finally{
            lock.unlock();
        }

    }
}

void main() throws InterruptedException {

    ReentrantLocks lock = new ReentrantLocks();

    var t1 = new Thread(() -> {
       for(int i = 1; i <= 1000; i++){
            try {
                lock.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    var t2 = new Thread(() -> {
        for(int i = 1; i <= 1000; i++){
            try {
                lock.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    t1.start();
    t2.start();

    t1.join();
    t2.join();

    System.out.println("COunter = "+ReentrantLocks.counter);
}
