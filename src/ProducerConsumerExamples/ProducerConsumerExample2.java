package ProducerConsumerExamples;

import java.util.ArrayList;
import java.util.List;

class SharedBuffer{
    private List<Integer> buffer = new ArrayList<>();
    private int capacity = 5;

    public synchronized void produce() throws InterruptedException {
        while(buffer.size() == capacity){
            System.out.println("Producer will wait not buffer full");
            wait();
        }

        for(int i = 0; i < capacity; i++){
            buffer.add(i);
            System.out.println("Item added= "+i);
        }
        notify();
    }

    public synchronized void consume() throws InterruptedException{
        while(buffer.isEmpty()){
            System.out.println("Buffer is Empty Consumer waiting");
            wait();
        }

        while(!buffer.isEmpty()){
            System.out.println("removed Item = "+buffer.removeFirst());
            Thread.sleep(500);
        }

        notify();
    }
}

class Producer implements Runnable{

    SharedBuffer sharedBuffer;
    Producer(SharedBuffer sharedBuffer){
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {

        try {
            while(true) {
                this.sharedBuffer.produce();
                Thread.sleep(500);
            }
        } catch (InterruptedException e){
                throw new RuntimeException(e);
        }
    }
}

class Consumer implements Runnable{

    SharedBuffer sharedBuffer;
    Consumer(SharedBuffer sharedBuffer){
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {

        try {
            while(true) {
                this.sharedBuffer.consume();
                Thread.sleep(500);
            }
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}

public class ProducerConsumerExample2 {

    void main(){

        SharedBuffer sharedBuffer = new SharedBuffer();

        var t1 = new Thread(new Producer(sharedBuffer));
        var t2 = new Thread(new Consumer(sharedBuffer));

        t1.start();
        t2.start();
    }
}
