package models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class CoffeeMachine {
    /*
    Stores all the requirements needed for a having a state of the coffeeMachine
    quantityMap: stores current quantity of all ingredients, map is used for faster access
    beverageMap: map used for faster access
    */
    String name;
    int outlets;
    Map<String, Integer> quantityMap;
    Map<String, Beverage> beverageMap;

    public CoffeeMachine(String name, int outlets) {
        this.name = name;
        this.outlets = outlets;
        this.quantityMap = new HashMap<String, Integer>();
        this.beverageMap = new HashMap<String, Beverage>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOutlets() {
        return outlets;
    }

    public void setOutlets(int outlets) {
        this.outlets = outlets;
    }

    public Map<String, Integer> getQuantityMap() {
        return quantityMap;
    }

    /*
       Used to initialize ingredient quantities, it increases quantity if ingredient is already present
   */
    public void addQuantity(String quantityType, int value) {
        if(quantityMap.containsKey(quantityType)){
            quantityMap.put(quantityType, quantityMap.get(quantityType) + value);
        }
        else{
            quantityMap.put(quantityType, value);
        }
    }

    public Map<String, Beverage> getBeverageMap() {
        return beverageMap;
    }

    public void addBeverage(Beverage beverage) {
        beverageMap.put(beverage.getName(), beverage);
    }

    /*
      Lets us know if a beverage can be made from the existing set of machine ingredients
      We also print if ingredients are not available or sufficient
    */
    public boolean canGetBeverage(String beverageName) {
        Beverage beverage = this.getBeverageMap().get(beverageName);
        Map<String, Integer> ingredientMap = beverage.getIngredientMap();
        boolean canDispenseBeverage = true;

        for(Map.Entry<String, Integer> ingredient: ingredientMap.entrySet()){
            String ingredientKey = ingredient.getKey();
            if(!quantityMap.containsKey(ingredientKey) || quantityMap.get(ingredientKey) < ingredientMap.get(ingredientKey)){
                if(!quantityMap.containsKey(ingredientKey))
                    System.out.println(String.format("%s cannot be prepared because %s is not available", beverage.getName(), ingredientKey));
                else
                    System.out.println(String.format("%s cannot be prepared because %s is not sufficient", beverage.getName(), ingredientKey));
                canDispenseBeverage = false;
                break;
            }
        }
        return canDispenseBeverage;
    }

    /*
      Change quantities of ingredients in the coffeeMachine and return beverage
    */
    public Beverage getBeverage(String beverageName){
        if(canGetBeverage(beverageName)){
            Beverage beverage = this.getBeverageMap().get(beverageName);
            Map<String, Integer> ingredientMap = beverage.getIngredientMap();
            for(Map.Entry<String, Integer> ingredient: ingredientMap.entrySet()){
                String ingredientKey = ingredient.getKey();
                quantityMap.put(ingredientKey, quantityMap.get(ingredientKey) - ingredientMap.get(ingredientKey));
            }
            System.out.println(String.format("%s is prepared", beverage.getName()));
            return beverage;
        }
        else
            return null;
    }

    /*
      Make the beverage from the ingredients and serve to customer, this is a blocking call to stimulate real life cases
    */
    public void dispenseBeverage(Beverage beverage){
        try{
            Thread.sleep(1000);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
