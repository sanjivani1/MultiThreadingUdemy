package philosopherProblem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static void main() throws InterruptedException{

        Chopstick[] chopsticks = null;
        Philosopher[] philosophers = null;
        ExecutorService executer = null;

        try {
            chopsticks = new Chopstick[Constants.NO_OF_CHOPSTICKS];
            philosophers = new Philosopher[Constants.NO_OF_PHILOSOPHERS];

            for(int i = 0; i < Constants.NO_OF_CHOPSTICKS; i++){
                chopsticks[i] = new Chopstick(i);
            }

            executer = Executors.newFixedThreadPool(Constants.NO_OF_PHILOSOPHERS);

            for(int i = 0; i < Constants.NO_OF_PHILOSOPHERS; i++){
                philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i+1)%Constants.NO_OF_PHILOSOPHERS]);
                executer.execute(philosophers[i]);
            }

            Thread.sleep(Constants.DURATION_OF_RUNNING);

            for(Philosopher philosopher : philosophers){
                philosopher.setFull(true);
            }
        } finally{
            executer.shutdown();

            while(!executer.isTerminated()){
                Thread.sleep(1000);
            }

            assert philosophers != null;
            for(Philosopher philosopher : philosophers){
                System.out.println(philosopher+" eat #"+philosopher.getEatingCounter()+" times!");
            }
        }
    }
}
