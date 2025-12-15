package VirtualThreads;

public class VirtualThreadExample {
    static void main() throws InterruptedException{
        
        //approach 2
        var factory = Thread.ofVirtual().name("virtual-", 0).factory();

        var t1 = factory.newThread(App::run);
        var t2 = factory.newThread(App::run);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        /*//approach 1
        var builder = Thread.ofVirtual().name("virtual-", 0);
        
        var t1 = builder.start(new App());
        var t2 = builder.start(new App());
        
        t1.join();
        t2.join();
         */
    }
}

/*class App implements Runnable {

    public void run() {
        try{
            System.out.println("Virtual Thread started: "+Thread.currentThread().getName());
            Thread.sleep(2000);
            System.out.println("Virtual Thread ended: "+Thread.currentThread().getName());
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}*/
class App {

    public static void run() {
        try{
            System.out.println("Virtual Thread started: "+Thread.currentThread().getName());
            Thread.sleep(2000);
            System.out.println("Virtual Thread ended: "+Thread.currentThread().getName());
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
