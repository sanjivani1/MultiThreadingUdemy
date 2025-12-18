package philosopherProblem;

import java.util.SplittableRandom;
import java.util.random.RandomGenerator;

public class Philosopher implements Runnable{

    private int id;
    private boolean full;
    private Chopstick leftChopstick;
    private Chopstick rightChopstick;
    private int eatingCounter;
    private RandomGenerator random;

    public Philosopher(int id, Chopstick rightChopstick, Chopstick leftChopstick) {
        this.id = id;
        this.eatingCounter = 0;
        this.rightChopstick = rightChopstick;
        this.leftChopstick = leftChopstick;
        this.full = false;
        random = new SplittableRandom();
    }

    @Override
    public void run() {

        try{

            while(!full){

                think();
                if(leftChopstick.pickUp(this, State.LEFT)){
                    if(rightChopstick.pickUp(this, State.RIGHT)){
                        eat();
                        rightChopstick.putDown(this, State.RIGHT);
                    }
                    leftChopstick.putDown(this, State.LEFT);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void think() throws InterruptedException {
        System.out.println(this+" is thinking");
        Thread.sleep(random.nextInt(1000));
    }

    private void eat() throws InterruptedException {
        System.out.println(this+" is eating");
        eatingCounter++;
        Thread.sleep(random.nextInt(1000));
    }

    public void setFull(boolean full) {
        this.full = full;
    }
    public int getEatingCounter(){
        return eatingCounter;
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                '}';
    }
}
