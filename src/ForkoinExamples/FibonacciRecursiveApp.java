package ForkoinExamples;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciRecursiveApp {
    static void main() {

        ForkJoinPool pool = new ForkJoinPool();
        FibonacciGenerator generator = new FibonacciGenerator(50);
        System.out.println("Fibonacci number is: "+pool.invoke(generator));
    }
}

class FibonacciGenerator extends RecursiveTask<Integer>{

    int i;

    FibonacciGenerator(int i){
        this.i = i;
    }

    @Override
    protected Integer compute() {

        if(i <= 1){
            return i;
        }

        FibonacciGenerator generator1 = new FibonacciGenerator(i-1);
        FibonacciGenerator generator2 = new FibonacciGenerator(i-2);

        generator1.fork();
//        int res2 = generator2.compute();
//
//        int res1 = generator1.join();
        generator2.fork();
        return generator1.join() + generator2.join();

//        return res1 + res2;
    }
}
