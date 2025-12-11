package DeadlockExample;

//acquire locks in sequential manner

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class WorkerFix{

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();


    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 acquired lock1");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println("Worker2 acquired lock1");

        lock1.unlock();
        lock2.unlock();

    }

    public void worker2() {
        lock1.lock();
        System.out.println("Worker2 acquired lock2");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println("Worker1 acquired lock1");

        lock1.unlock();
        lock2.unlock();

    }
}


public class DeadLlockFix {

    void main() throws InterruptedException{

        WorkerFix worker = new WorkerFix();

        new Thread(worker::worker1, "worker1").start();
        new Thread(worker::worker2, "worker2").start();
    }
}

