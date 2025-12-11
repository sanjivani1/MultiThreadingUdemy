import java.time.LocalDateTime;

public class SynchronizationExample {
    public static int counter1 = 0;
    public static int counter2 = 0;
    public static int counter3 = 0;

    final Object lock1 = new Object();
    final Object lock2 = new Object();
    final Object lock3 = new Object();


    public void increment1(){

        synchronized (lock1){
            counter1++;
        }
    }

    public void increment2(){
        synchronized (lock2){
            counter2++;
        }
    }

    public void increment3(){
        synchronized (lock3){
            counter3++;
        }
    }

    void main() throws InterruptedException {

        long start = System.nanoTime();

        var t1 = new Thread(()->{
           for(int i = 0; i < 100000;i++){
               increment1();
           }
        });

        var t2 = new Thread(()->{
            for(int i = 0; i < 100000;i++){
                increment2();
            }
        });

        Thread t3 = new Thread(()->{
            for(int i = 0; i < 100000;i++){
                increment3();
            }
        });

        t1.start();
        t2.start();
        t3.start();

        try{
            t1.join();
            t2.join();
            t3.join();
        }
        catch(RuntimeException e){
            System.out.println(e);
        }

        System.out.println("Counter = "+counter1);
        System.out.println("Counter = "+counter2);
        System.out.println("Counter = "+counter3);

        long micros = (System.nanoTime() - start) / 1000;
        System.out.println("Execution time: " + micros + " µs");
    }
}


