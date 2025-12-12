package AtomicIntegerExample;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {

    public static AtomicInteger counter = new AtomicInteger(0);

    void main(){

        AtomicIntegerExample atomicIntegerExample = new AtomicIntegerExample();
        var t1 = new Thread(() -> {
            for(int i = 1; i <= 1000000; i++){
                atomicIntegerExample.increment();
            }
        });

        var t2 = new Thread(() -> {
            for(int i = 1; i <= 1000000; i++){
                atomicIntegerExample.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(counter);
    }

    void increment(){
        counter.getAndIncrement();
    }

}
