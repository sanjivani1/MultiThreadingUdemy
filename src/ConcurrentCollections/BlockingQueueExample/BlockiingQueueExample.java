package ConcurrentCollections.BlockingQueueExample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockiingQueueExample {
    static void main() {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        var t1 = new Thread(() -> new Worker1(queue).run());
        var t2 = new Thread(() -> new Worker2(queue).run());

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        Worker1 worker1 = new Worker1(queue);
//        Worker2 worker2 = new Worker2(queue);
//
//        new Thread(worker1).start();
//        new Thread(worker2).start();


    }
}

class Worker1 implements Runnable{

    BlockingQueue<Integer> queue;

    Worker1(BlockingQueue<Integer> queue){
        this.queue = queue;
    }

    @Override
    public void run() {

        int counter = 0;
        while(true){
            try {
                queue.put(counter);
                counter++;
                System.out.println("Added to the queue: "+counter);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class Worker2 implements Runnable{

    BlockingQueue<Integer> queue;

    Worker2(BlockingQueue<Integer> queue){
        this.queue = queue;
    }

    @Override
    public void run() {

        while(true){
            try {
                int take = queue.take();
                System.out.println("taken value from queue: "+take);
                take++;
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}