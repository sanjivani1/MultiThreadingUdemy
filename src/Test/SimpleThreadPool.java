package Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleThreadPool {

    private final int poolSize;
    private final PoolWorker[] poolWorkers;
    private final BlockingQueue<Runnable> taskQueue;

    public SimpleThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.poolWorkers = new PoolWorker[poolSize];
        this.taskQueue = new LinkedBlockingQueue<>();

        for(int i = 0; i < poolSize; i++){
            poolWorkers[i] = new PoolWorker();
            poolWorkers[i].start();
        }
    }

    public void submit(Runnable runnable) throws InterruptedException {
        taskQueue.put(runnable);
    }

    private class PoolWorker extends Thread {

        @Override
        public void run(){
            while(true){
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {

        SimpleThreadPool executor = new SimpleThreadPool(3);

        Runnable run = () -> {
            System.out.println("Started execution by "+ Thread.currentThread().getName());

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Completed execution by: "+Thread.currentThread().getName());
        };

        for(int i = 0; i < 10; i++){
            executor.submit(run);
        }
    }
}
