package ExecuterServiceExamples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable{

    private int id;

    public Task(int id){
        this.id = id;
    }

    @Override
    public void run() {

        System.out.println("Task with id: "+id+" is in work. Thread-id: "+Thread.currentThread().getName());
//        long duration = (long) (Math.random() * 5);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


public class SingleThreadExecuter {

    void main(){

        ExecutorService executer = Executors.newSingleThreadExecutor();

        for(int i = 0; i < 5; i++){
            executer.execute(new Task(i));
        }
    }
}
