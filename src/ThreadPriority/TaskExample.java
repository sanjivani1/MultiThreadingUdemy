package ThreadPriority;
class Task implements Runnable{

    @Override
    public void run(){

        try{
            for(int i = 0; i <= 6; i++){
                System.out.println(Thread.currentThread().getName()+" count: "+i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

public class TaskExample{
    void main(){

        Thread low = new Thread(new Task(), "low Priority");
        Thread normal = new Thread(new Task(), "normal Priority");
        Thread high = new Thread(new Task(), "high Priority");


        low.setPriority(Thread.MIN_PRIORITY);
        normal.setPriority(Thread.NORM_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);

        low.start();
        normal.start();
        high.start();

    }
}