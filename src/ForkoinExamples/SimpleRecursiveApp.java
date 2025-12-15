package ForkoinExamples;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static java.util.concurrent.ForkJoinTask.invokeAll;

class RecursiveActionApplication extends RecursiveAction {

    int simulatedTask;

    RecursiveActionApplication(int simulatedTask){
        this.simulatedTask = simulatedTask;
    }


    @Override
    protected void compute() {

        if(simulatedTask > 100){
            System.out.println("Dividing task because task too big..."+simulatedTask);
            RecursiveActionApplication action1 = new RecursiveActionApplication(simulatedTask/2);
            RecursiveActionApplication action2 = new RecursiveActionApplication(simulatedTask/2);

            action1.invoke();
            action2.invoke();
        } else {
            System.out.println("Task too small sequential execution is fine...");
            System.out.println("size of task "+simulatedTask);
        }
    }
}



public class SimpleRecursiveApp {
    static void main() {

        System.out.println(Runtime.getRuntime().availableProcessors());
        ForkJoinPool forkPool = new ForkJoinPool();

        RecursiveActionApplication action = new RecursiveActionApplication(800);
        action.invoke();
//        invokeAll(action);
    }
}
