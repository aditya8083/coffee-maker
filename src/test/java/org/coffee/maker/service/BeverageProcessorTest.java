package org.coffee.maker.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.coffee.maker.utility.CoffeeMachine;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeverageProcessorTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void checkIsAllIngredientsPresentInMachineMethod() throws IOException, InterruptedException {
        CoffeeMachine coffeeMachine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/sample.txt")), CoffeeMachine.class);
        BeverageProcessor.addToMachine(coffeeMachine.getAllIngredients());
        Map<String, Integer> beverageIngredients = new LinkedHashMap<>();
        beverageIngredients.put("hot_water", 50);
        beverageIngredients.put("hot_green_tea", 50);
        beverageIngredients.put("hot_milk", 550);
        new BeverageProcessor().isAllIngredientsPresentInMachine(beverageIngredients);
    }

    @Test
    public void checkGetBeverageMethod() throws IOException, InterruptedException {
        CoffeeMachine coffeeMachine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/sample.txt")), CoffeeMachine.class);
        BeverageProcessor.addToMachine(coffeeMachine.getAllIngredients());
        Map<String, Integer> beverageIngredients = new LinkedHashMap<>();
        beverageIngredients.put("hot_water", 50);
        beverageIngredients.put("ginger_syrup", 10);
        beverageIngredients.put("hot_milk", 100);
        beverageIngredients.put("sugar_syrup", 10);
        beverageIngredients.put("tea_leaves_syrup", 10);
        new BeverageProcessor().getBeverage(beverageIngredients);
    }

    @Test
    public void checkGetBeverageMethod2() throws IOException, InterruptedException {
        CoffeeMachine coffeeMachine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/sample.txt")), CoffeeMachine.class);
        BeverageProcessor.addToMachine(coffeeMachine.getAllIngredients());
        Map<String, Integer> beverageIngredients = new LinkedHashMap<>();
        beverageIngredients.put("hot_water", 50);
        beverageIngredients.put("hot_milk", 550);
        new BeverageProcessor().getBeverage(beverageIngredients);
    }

}