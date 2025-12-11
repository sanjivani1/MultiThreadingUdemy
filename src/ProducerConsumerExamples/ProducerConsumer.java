package ProducerConsumerExamples;

class ProducerConsumerExample {

    public synchronized void producer() throws InterruptedException {

        System.out.println("inside prodcer");
        wait();
        System.out.println("Again in producer");
    }

    public synchronized void consumer() throws InterruptedException{
        System.out.println("inside consumer");
        notify();
        System.out.println("Again in consumer");
    }
}
public class ProducerConsumer{

    void main(){

        ProducerConsumerExample obj = new ProducerConsumerExample();

        var t1 = new Thread(()-> {
            try {
                obj.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var t2 = new Thread(()-> {
            try {
                obj.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
    }
}

