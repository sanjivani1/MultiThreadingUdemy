package Test;

import ConcurrentCollections.BlockingQueueExample.BlockiingQueueExample;

import java.util.LinkedList;
import java.util.Queue;

public class MyBlockingQueue {

    private Queue<Integer> blockingQueue;
    private int capacity;

    public MyBlockingQueue(Queue<Integer> blockingQueue, int capacity){
        this.blockingQueue = blockingQueue;
        this.capacity = capacity;
    }

    public synchronized void enqueue(int n) throws InterruptedException{

        while(blockingQueue.size() == capacity){
            wait();
        }
        blockingQueue.offer(n);
        notifyAll();
    }

    public synchronized int dequeue() throws InterruptedException{

        while(blockingQueue.isEmpty()){
            wait();
        }
        int rem = blockingQueue.poll();
        notifyAll();
        return rem;
    }
}

class Test{
    public static void main(String[] args) throws InterruptedException {

        Queue<Integer> q = new LinkedList<>();
        MyBlockingQueue blockingQueue = new MyBlockingQueue(q, 5);

        Thread t1 = new Thread(()-> {
             for(int i = 0; i < 50; i++){
                 try {
                     System.out.println("Inserted value "+i);
                     blockingQueue.enqueue(i);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
             }
        });

        Thread t2 = new Thread(()-> {
            for(int i = 0; i < 50; i++){
                try {

                    int rem = blockingQueue.dequeue();
                    System.out.println("removed value "+rem);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Finished");

    }
}