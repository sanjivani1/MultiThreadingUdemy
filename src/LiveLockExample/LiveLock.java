package LiveLockExample;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker{

    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void worker1(){

        while(true){
            try {
                if(lock1.tryLock(50, TimeUnit.MILLISECONDS)){
                    System.out.println("Worker 1 got the lock1");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Worker 1 try to acquire lock2");

            if(lock2.tryLock()){
                System.out.println("Worker 1 got the lock2");
                lock2.unlock();
            }
            else {
                System.out.println("Worker 1 unable to get lock2");
                continue;
            }
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {

        while (true) {
            try {
                if (lock2.tryLock(50, TimeUnit.MILLISECONDS)) {
                    System.out.println("Worker 2 got the lock2");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Worker 2 try to acquire lock1");

            if (lock1.tryLock()) {
                System.out.println("Worker 2 got the lock1");
                lock1.unlock();
            }
            else {
                System.out.println("Worker 2 unable to get lock2");
                continue;
            }
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }
}



public class LiveLock {

    static void main() {

        Worker worker = new Worker();

        new Thread(worker::worker1, "worker1").start();
        new Thread(worker::worker2, "worker2").start();
    }
}
