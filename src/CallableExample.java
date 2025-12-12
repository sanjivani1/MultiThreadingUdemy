import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableExample {

    public static void main(String[] args){

        ExecutorService execute = Executors.newFixedThreadPool(2);
        List<Future<String>> futureList = new ArrayList<>();


        for(int i = 1; i <= 10; i++){
            Future<String> futures = execute.submit(new Task(i));
            futureList.add(futures);
        }

        for(Future<String> future : futureList){
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Task implements Callable<String> {
    private int id;

    Task(int id){
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        return "id is: "+id;
    }
}

