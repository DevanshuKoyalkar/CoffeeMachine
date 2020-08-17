import models.Beverage;
import models.CoffeeMachine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

public class Driver {
    static CofeeMachineManager cofeeMachineManager;
    static Semaphore semaphore = new Semaphore(1);//mutex

    private static class ParallelTask implements Runnable {
        @Override public void run() {
            try{
                for(int i=0; i< 10; i++){
                    semaphore.acquire();
                    System.out.print(Thread.currentThread().getName() + ": ");
                    Beverage beverage = cofeeMachineManager.getRandomBeverage();////Dispense a random beverage
                    semaphore.release();
                    //Blocking call to make the beverage from the ingredients
                    cofeeMachineManager.dispenseBeverage(beverage);
                }
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        cofeeMachineManager = new CofeeMachineManager();
        cofeeMachineManager.init();

        try {
            int numOutlets = cofeeMachineManager.getOutlets();
            Thread threads[] = new Thread[numOutlets];
            for (int i = 0; i < numOutlets; i++) {
                threads[i] = new Thread(new ParallelTask(), String.format("Dispenser %d", i).toString());
                threads[i].start();
            }
            for (int i = 0; i < numOutlets; i++) {
                threads[i].join();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
