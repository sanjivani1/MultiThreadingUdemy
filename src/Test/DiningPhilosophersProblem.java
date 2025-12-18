package Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophersProblem {
    public static void main(String[] args) throws InterruptedException {

        Philosopher[] philosophers = null;
        Fork[] forks = null;
        ExecutorService executor = null;

        try {
            philosophers = new Philosopher[5];
            forks = new Fork[5];

            for (int i = 0; i < 5; i++) {
                forks[i] = new Fork(i);
            }

            executor = Executors.newVirtualThreadPerTaskExecutor();

            for (int i = 0; i < 5; i++) {
                philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % 5]);
                executor.submit(philosophers[i]);
            }

            Thread.sleep(5000);

            for (Philosopher philosopher : philosophers) {
                philosopher.setFull(true);
            }
        } finally {
            executor.shutdown();
            while(!executor.isTerminated()){
                Thread.sleep(1000);
            }

            for(Philosopher philosopher: philosophers){
                System.out.println("Philosopher "+philosopher.id+" has eaten "+philosopher.getEatingCounter()+" times");
            }
        }

    }
}

class Philosopher implements Runnable{
    public int id;
    public final Fork leftFork;
    public final Fork rightFork;
    private int eatingCounter;
    private boolean full;

    Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.eatingCounter = 0;
        this.full = false;
    }

    public void think() throws InterruptedException {
        System.out.println("Philosopher "+id+" is thinking");
        Thread.sleep(1000);
    }
    public void eat() throws InterruptedException {
        System.out.println("Philosopher "+id+" is eating");
        eatingCounter++;
        Thread.sleep(1000);
    }

    public boolean getFull(){
        return full;
    }
    public void setFull(boolean full){
        this.full = full;
    }
    public int getEatingCounter(){
        return eatingCounter;
    }

    @Override
    public void run() {

        while(!full){

            try {
                think();
                if(leftFork.pickFork(this, Constants.Left_Fork)){
                    if(rightFork.pickFork(this, Constants.Right_Fork)){
                        System.out.println("Philosopher "+this.id+" picked up both forks");
                        eat();
                        rightFork.putDown(this, Constants.Right_Fork);
                    }
                    leftFork.putDown(this, Constants.Left_Fork);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

class Fork{
    private int id;
    private final Lock lock;

    Fork(int id){
        this.id = id;
        lock = new ReentrantLock(true);
    }

    public boolean pickFork(Philosopher philosopher, Constants side) throws InterruptedException {
        if(lock.tryLock(10, TimeUnit.MILLISECONDS)){
            System.out.println("Philosopher "+philosopher.id+" picked up "+ side.toString());
            return true;
        }
        else return false;
    }
    public void putDown(Philosopher philosopher, Constants side){
        System.out.println("Philosopher "+philosopher.id+" putting down "+side.toString());
        lock.unlock();
    }
}

enum Constants {
    Left_Fork,
    Right_Fork
}