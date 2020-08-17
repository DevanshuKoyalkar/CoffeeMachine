import models.Beverage;
import models.CoffeeMachine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CofeeMachineManager {
    /*
    Used to initialize coffee machine and interact with CoffeeMachine Class
    */
    CoffeeMachine coffeeMachine;

    public void init(){
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject machineObject = (JSONObject) ((JSONObject) jsonParser.parse(new FileReader("input.json"))).get("machine");
            JSONObject outlets = (JSONObject) machineObject.get("outlets");
            JSONObject totalItemsQuantity = (JSONObject) machineObject.get("total_items_quantity");
            JSONObject beverages = (JSONObject) machineObject.get("beverages");

            Integer numOutlets = ((Long) (outlets.get("count_n"))).intValue();
            coffeeMachine = new CoffeeMachine("myMachine", numOutlets);

            totalItemsQuantity.keySet().forEach(keyStr ->
            {
                Object keyvalue = totalItemsQuantity.get(keyStr);
                coffeeMachine.addQuantity(keyStr.toString(), Integer.parseInt(keyvalue.toString()));
            });


            beverages.keySet().forEach(beverageKey ->
            {
                JSONObject beverageDetails = (JSONObject) beverages.get(beverageKey);
                Beverage beverage = new Beverage(beverageKey.toString());

                beverageDetails.keySet().forEach(ingredientKey ->
                {
                    Object ingredientValue = beverageDetails.get(ingredientKey);
                    beverage.addIngredient(ingredientKey.toString(), Integer.parseInt(ingredientValue.toString()));
                });
                coffeeMachine.addBeverage(beverage);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getOutlets(){
        return coffeeMachine.getOutlets();
    }

    public Beverage getRandomBeverage(){
        List<String> beverageKeys = new ArrayList<String>(coffeeMachine.getBeverageMap().keySet());
        Random r = new Random();
        String beverageName = beverageKeys.get(r.nextInt(beverageKeys.size()));
        return getBeverage(beverageName);
    }

    public Beverage getBeverage(String beverageName){
        return coffeeMachine.getBeverage(beverageName);
    }

    public void dispenseBeverage(Beverage beverage){
        coffeeMachine.dispenseBeverage(beverage);
    }
}
