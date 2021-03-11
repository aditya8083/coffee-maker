package org.coffee.maker.service;

import org.coffee.maker.exception.BaseException;

import java.util.LinkedHashMap;
import java.util.Map;

public class BeverageMaker implements Runnable {

    private LinkedHashMap<String, Map<String, Integer>> allBeverages;
    private String beverageName;
    private BeverageProcessor beverageProcessor;

    public BeverageMaker(LinkedHashMap<String, Map<String, Integer>> allBeverages, String beverageName, BeverageProcessor beverageProcessor) {
        this.allBeverages = allBeverages;
        this.beverageName = beverageName;
        this.beverageProcessor = beverageProcessor;
    }

    @Override
    public void run() {
        synchronized (beverageProcessor) {
            if (allBeverages.containsKey(beverageName)) {
                    beverageProcessor.makeBeverage(allBeverages, beverageName);

            } else {
                throw new BaseException("Beverage" + beverageName + " Not found");
            }
        }
    }

}
