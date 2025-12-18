package Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintEvenOdd {
    static int num = 0;
    static final Lock lock = new ReentrantLock();
    static int MAX = 10;

    public static void main(String[] args) throws InterruptedException {

//        AtomicInteger num = new AtomicInteger();
        Condition condition = lock.newCondition();
        Thread t1 = new Thread(() ->{
           while(true){
               lock.lock();
               if (num % 2 == 0) {
                   System.out.println("even: " + num);
                   num++;
                   condition.signalAll();

                   if (num > MAX) return;
               } else {
                   while (num % 2 != 0) {
                       try {
                           condition.await();
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
               }

           }
        });
        Thread t2 = new Thread(() ->{
            while(true){
                lock.lock();
                if (num % 2 != 0) {
                    System.out.println("odd: " + num);
                    num++;
                    condition.signalAll();

                    if (num > MAX) return;
                } else {
                    while (num % 2 == 0) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }
        });

        t1.start();
        t2.start();

        Thread.sleep(500);

        t1.join();
        t2.join();
    }
}
