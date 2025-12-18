package Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SimpleReentrantLock {
    static Lock lock = new MyReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Runnable task = () -> {
            lock.lock();
            System.out.println("hold Count "+ MyReentrantLock.holdCount);
            try{
                outer();
            } finally {
                lock.unlock();
                System.out.println("hold Count "+ MyReentrantLock.holdCount);
            }
        };

        new Thread(task).start();
        new Thread(task).start();

    }
    public static void outer() {

        lock.lock();
        System.out.println("hold Count "+ MyReentrantLock.holdCount);
        try {
            inner();

        } finally {
            lock.unlock();
            System.out.println("hold Count "+ MyReentrantLock.holdCount);
        }
    }
    public static void inner() {

        lock.lock();
        try {
            System.out.println("hold Count "+ MyReentrantLock.holdCount);
        } finally{
            lock.unlock();
        }

    }
}

class MyReentrantLock implements Lock {

    private Thread owner = null;
    static int holdCount = 0;

    @Override
    public synchronized void lock() {

        while(owner != null && owner != Thread.currentThread()){

            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        holdCount++;
        owner = Thread.currentThread();
        System.out.println("Lock acquired by "+Thread.currentThread().getName()+" holdCount: "+holdCount);
    }

    @Override
    public synchronized void unlock() {

        if(owner != Thread.currentThread()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        holdCount--;
        if(holdCount == 0){
            owner = null;
            notifyAll();
        }
        System.out.println("Lock acquired by "+Thread.currentThread().getName()+" holdCount: "+holdCount);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}


