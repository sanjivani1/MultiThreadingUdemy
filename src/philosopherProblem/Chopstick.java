package philosopherProblem;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

    int id;
    private final Lock lock;

    public Chopstick(int id){
        this.id = id;
        lock = new ReentrantLock();
    }

    public boolean pickUp(Philosopher philosopher, State state) throws InterruptedException {

        if(lock.tryLock(10, TimeUnit.MILLISECONDS)){
            System.out.println("Philosopher "+philosopher+" picked up "+state.toString()+" "+this);
            return true;
        }
        return false;
    }

    public void putDown(Philosopher philosopher, State state) throws InterruptedException {

        lock.unlock();
        System.out.println("Philosopher "+philosopher+" puts down the "+state.toString()+" "+this);
    }

    @Override
    public String toString() {
        return "Chopstick{" +
                "id=" + id +
                '}';
    }
}
