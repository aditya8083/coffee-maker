package org.coffee.maker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coffee.maker.utility.CoffeeMachine;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class BeverageMakerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1() throws IOException, InterruptedException {
        CoffeeMachine coffeeMachine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/sample.txt")), CoffeeMachine.class);
        BeverageProcessor.addToMachine(coffeeMachine.getAllIngredients());
        ForkJoinPool forkJoinPool = new ForkJoinPool(coffeeMachine.getOutlet().getTotalOutlets());
        for (Map.Entry<String, Map<String, Integer>> entry : coffeeMachine.getAllBeverages().entrySet()) {
            BeverageProcessor beverageProcessor = new BeverageProcessor();
            forkJoinPool.execute(new BeverageMaker(coffeeMachine.getAllBeverages(), entry.getKey(), beverageProcessor));
            Thread.sleep(100);
        }
    }

}
