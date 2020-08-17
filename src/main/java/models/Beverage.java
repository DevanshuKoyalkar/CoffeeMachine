package models;

import java.util.HashMap;
import java.util.Map;

public class Beverage {
    /*
    Beverage class stores all the requirements needed to create a beverage
    We use a ingredientMap as it allows us faster retriever while going over the ingredients
     */
    String name;
    Map<String, Integer> ingredientMap;

    public Beverage(String name) {
        this.name = name;
        this.ingredientMap = new HashMap<String, Integer>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getIngredientMap() {
        return ingredientMap;
    }

    /*
        addIngredient: used to initialize ingredients and their respective quantities required
    */
    public void addIngredient(String ingredient, int value) {
        ingredientMap.put(ingredient, value);
    }
}
