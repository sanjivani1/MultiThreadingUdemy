package CompletableFuturesExamples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFuturesApp {

    static void main() throws ExecutionException, InterruptedException {

        /*ExecutorService service = Executors.newFixedThreadPool(5);
        ExecutorService service1 = Executors.newCachedThreadPool();

        CompletableFuture<String> v = CompletableFuture.supplyAsync(() -> "Hello", service)
                        .thenApplyAsync(String::toUpperCase, service1)
                                .thenApplyAsync(s-> s + " WORLD",service1);

        System.out.println(v.get());


        service1.shutdown();
        service.shutdown();*/

        //example2

        try(ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()){

            CompletableFuture.supplyAsync(DBQuery::run, service)
                    .thenCombine(CompletableFuture
                                    .supplyAsync(RestQuery::run, service),
                                                (res1, res2) -> {
                                                                    return res1 + res2;
                                                }
                        ).thenAccept(System.out::print);
        }
    }
}
