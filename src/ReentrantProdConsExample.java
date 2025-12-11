import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockWorkers {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void producer() throws InterruptedException{


        lock.lock();
        System.out.println("Inside Producer");
        condition.await();
        System.out.println("Again inside producer");
        lock.unlock();
    }

    public void consumer() throws InterruptedException{

        Thread.sleep(1000);
        lock.lock();
        System.out.println("Inside consumer");
        condition.signal();
        System.out.println("after signal");
        lock.unlock();

    }

}



public class ReentrantProdConsExample {

    LockWorkers obj = new LockWorkers();

    void main() throws InterruptedException {

        var t1 = new Thread(()-> {
            try {
                obj.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var t2 = new Thread(()-> {
            try {
                obj.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();

        t1.join();
        t2.join();


    }
}



