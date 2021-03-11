package org.coffee.maker.service;

import org.coffee.maker.constant.BeverageStatus;
import org.coffee.maker.constant.IngredientStatus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeverageProcessor {

    //This map will hold all the machine ingredients
    private static ConcurrentHashMap<String, Integer> machineIngredientsMap = new ConcurrentHashMap<>();

    private static void addIngredientToMachine(String ingredientName, Integer ingredientQuantity) {
        if (machineIngredientsMap.containsKey(ingredientName)) {
            machineIngredientsMap.put(ingredientName, machineIngredientsMap.get(ingredientName) + ingredientQuantity);
        } else {
            machineIngredientsMap.put(ingredientName, ingredientQuantity);
        }
    }

    //add the ingredients to machine
    public static void addToMachine(HashMap<String, Integer> ingredientsToBeAdded) {
        ingredientsToBeAdded.entrySet().forEach(
                ingredient -> addIngredientToMachine(ingredient.getKey(), ingredient.getValue()));
    }

    //get ingredients from the machine
    public ConcurrentHashMap<String, Integer> getMachineIngredients() {
        return machineIngredientsMap;

    }


    /*  Check beverageIngredients are present in machine ingredients also Check beverageIngredients quantity is present in machine ingredients */
    public Map<String, IngredientStatus> isAllIngredientsPresentInMachine(Map<String, Integer> beverageIngredients) {
        Map<String, IngredientStatus> ingredientStatusMap = new LinkedHashMap<>();
        ConcurrentHashMap<String, Integer> machineIngredients = getMachineIngredients();

        for (Map.Entry<String, Integer> beverageIngredient : beverageIngredients.entrySet()) {
            if (!machineIngredients.containsKey(beverageIngredient.getKey())) {
                ingredientStatusMap.put(beverageIngredient.getKey(), IngredientStatus.UNAVAILABLE);
            } else if (machineIngredients.containsKey(beverageIngredient.getKey()) &&
                    isIngredientsQuantityNotAvailable(beverageIngredient.getValue(), machineIngredients.get(beverageIngredient.getKey()))) {
                ingredientStatusMap.put(beverageIngredient.getKey(), IngredientStatus.INSUFFICIENT);
            }
        }
        return ingredientStatusMap;
    }


    private boolean isIngredientsQuantityNotAvailable(Integer beverageIngredientQuantity, Integer machineIngredientQuantity) {
        return (machineIngredientQuantity - beverageIngredientQuantity) < 0;

    }

    public void makeBeverage(LinkedHashMap<String, Map<String, Integer>> allBeverages, String beverageName) {
        Map<BeverageStatus, Map<String, IngredientStatus>> beverageStatus = getBeverage(allBeverages.get(beverageName));
        if (beverageStatus.size() == 0) {
            System.out.println(beverageName + " is prepared");
        } else if (beverageStatus.containsKey(BeverageStatus.NOT_PREPARED)) {
            if (beverageStatus.get(BeverageStatus.NOT_PREPARED).containsValue(IngredientStatus.INSUFFICIENT)) {

                System.out.println(beverageName + " cannot be prepared because " +
                        beverageStatus.get(BeverageStatus.NOT_PREPARED).entrySet().iterator().next().getKey() + " is not sufficient");


            } else if (beverageStatus.get(BeverageStatus.NOT_PREPARED).containsValue(IngredientStatus.UNAVAILABLE)) {
                System.out.println(beverageName + " cannot be prepared because " +
                        beverageStatus.get(BeverageStatus.NOT_PREPARED).entrySet().iterator().next().getKey() + " is not available");

            }

        }
    }


    public Map<BeverageStatus, Map<String, IngredientStatus>> getBeverage(Map<String, Integer> beverageIngredients) {
        Map<BeverageStatus, Map<String, IngredientStatus>> beverageStatusMap = new LinkedHashMap<>();
        Map<String, IngredientStatus> ingredientsStatusMap = isAllIngredientsPresentInMachine(beverageIngredients);
        if (ingredientsStatusMap.size() > 0) {
            beverageStatusMap.put(BeverageStatus.NOT_PREPARED, ingredientsStatusMap);
            return beverageStatusMap;
        } else {
            prepareBeverage(beverageIngredients);
            return beverageStatusMap;
        }
    }

    private void prepareBeverage(Map<String, Integer> beverageIngredients) {
        synchronized (this) {
            if (isAllIngredientsPresentInMachine(beverageIngredients).size() == 0) {
                ConcurrentHashMap<String, Integer> machineIngredients = getMachineIngredients();
                for (Map.Entry<String, Integer> beverageIngredient : beverageIngredients.entrySet()) {
                    machineIngredients.put(beverageIngredient.getKey(), machineIngredients.get(beverageIngredient.getKey()) - beverageIngredient.getValue());
                }
            }
        }
    }

}
