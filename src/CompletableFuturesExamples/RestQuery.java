package CompletableFuturesExamples;

public class RestQuery {

    public static String run() {

        System.out.println("Started rest query");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Completed rest query");
        return "";
    }
}
