package org.coffee.maker.service;

import org.coffee.maker.constant.BeverageStatus;
import org.coffee.maker.constant.IngredientStatus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeverageProcessor {

    /*  Check beverageIngredients are present in machine ingredients also Check beverageIngredients quantity is present in machine ingredients */
    public synchronized static Map<String, IngredientStatus> isAllIngredientsPresentInMachine(Map<String, Integer> beverageIngredients) {
        Map<String, IngredientStatus> ingredientStatusMap = new LinkedHashMap<>();
        ConcurrentHashMap<String, Integer> machineIngredients = MachineManager.getMachineIngredients();

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


    private static synchronized boolean isIngredientsQuantityNotAvailable(Integer beverageIngredientQuantity, Integer machineIngredientQuantity) {
        return (machineIngredientQuantity - beverageIngredientQuantity) < 0 ? true : false;

    }

    public static synchronized  Map<BeverageStatus, Map<String, IngredientStatus>> getBeverage(Map<String, Integer> beverageIngredients) {
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

    private static synchronized void prepareBeverage(Map<String, Integer> beverageIngredients) {
        if (isAllIngredientsPresentInMachine(beverageIngredients).size() == 0) {
            ConcurrentHashMap<String, Integer> machineIngredients = MachineManager.getMachineIngredients();
            for (Map.Entry<String, Integer> beverageIngredient : beverageIngredients.entrySet()) {
                machineIngredients.put(beverageIngredient.getKey(), machineIngredients.get(beverageIngredient.getKey()) - beverageIngredient.getValue());
            }
        }
    }
}
