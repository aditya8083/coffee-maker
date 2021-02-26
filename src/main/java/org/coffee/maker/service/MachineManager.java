package org.coffee.maker.service;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MachineManager {

    private static final MachineManagerImpl machineManagerImpl= new MachineManagerImpl();

    //add the ingredients to machine
    public static void addToMachine(LinkedHashMap<String, Integer> ingredientsToBeAdded) {
        ingredientsToBeAdded.entrySet().forEach(
                ingredient -> machineManagerImpl.addIngredientToMachine(ingredient.getKey(), ingredient.getValue()));
    }

    //get ingredients from the machine
    public static ConcurrentHashMap<String, Integer> getMachineIngredients(){
        return MachineManagerImpl.machineIngredientsMap;
    }

    private static class MachineManagerImpl {
        //This map will hold all the machine ingredients
        private static ConcurrentHashMap<String, Integer> machineIngredientsMap = new ConcurrentHashMap<>();

        private static void addIngredientToMachine(String ingredientName, Integer ingredientQuantity) {
            if (machineIngredientsMap.containsKey(ingredientName)) {
                machineIngredientsMap.put(ingredientName, machineIngredientsMap.get(ingredientName) + ingredientQuantity);
            } else {
                machineIngredientsMap.put(ingredientName, ingredientQuantity);
            }
        }

    }
}