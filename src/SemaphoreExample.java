import java.sql.SQLOutput;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

enum Downloader{

    INSTANCE;

    private Semaphore semaphore = new Semaphore(3, true);

    void download(){

        try {
            semaphore.acquire();
            downloadData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            semaphore.release();
        }
    }

    private void downloadData() {

        try {
            System.out.println("Download data...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class SemaphoreExample {

    static void main() {

        ExecutorService service = Executors.newCachedThreadPool();

        for(int i = 0 ; i < 12; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                   Downloader.INSTANCE.download();
                }
            });
        }

        service.close();

    }
}
