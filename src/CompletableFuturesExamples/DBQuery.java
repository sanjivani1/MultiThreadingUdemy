package CompletableFuturesExamples;

public class DBQuery {

    public static String run() {

        System.out.println("Started DB Query");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("CompletedDB Query");
        return "";
    }
}
