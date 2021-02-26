package org.coffee.maker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coffee.maker.utility.CoffeeMachine;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class BeverageMakerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1() throws IOException {
        CoffeeMachine coffeeMachine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/sample.txt")), CoffeeMachine.class);
        MachineManager.addToMachine(coffeeMachine.getAllIngredients());
        ForkJoinPool forkJoinPool = new ForkJoinPool(coffeeMachine.getOutlet().getTotalOutlets());
        coffeeMachine.getAllBeverages().entrySet().forEach(beverage ->
                forkJoinPool.execute(new BeverageMaker(coffeeMachine.getAllBeverages(), beverage.getKey())));
    }

}
