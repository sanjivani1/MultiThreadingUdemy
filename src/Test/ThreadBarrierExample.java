package Test;

public class ThreadBarrierExample {

    public static void main(String[] args) {

        Barrier barrier = new Barrier(3);

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting at the barrier");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();



    }
}

class Barrier{

    private int threadCount = 0;
    private int totalThread;

    Barrier(int totalThread){
        this.totalThread = totalThread;
    }

    public synchronized void await() throws InterruptedException {

        threadCount++;
        if(totalThread > threadCount){
            wait();
        }
        else{
            threadCount = 0;
            notifyAll();
        }

    }
}