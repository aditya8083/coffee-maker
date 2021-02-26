package org.coffee.maker.service;

import org.coffee.maker.constant.BeverageStatus;
import org.coffee.maker.constant.IngredientStatus;
import org.coffee.maker.exception.BaseException;

import java.util.LinkedHashMap;
import java.util.Map;

public class BeverageMaker implements Runnable {

    private LinkedHashMap<String, Map<String, Integer>> allBeverages;
    private String beverageName;

    public BeverageMaker(LinkedHashMap<String, Map<String, Integer>> allBeverages, String beverageName) {
        this.allBeverages = allBeverages;
        this.beverageName = beverageName;
    }

    @Override
    public void run() {
        if (allBeverages.containsKey(beverageName)) {
            makeBeverage();
        } else {
            throw new BaseException("Beverage" + beverageName + " Not found");
        }
    }

    private synchronized void makeBeverage() {
        Map<BeverageStatus, Map<String, IngredientStatus>> beverageStatus = BeverageProcessor.getBeverage(allBeverages.get(beverageName));
        if (beverageStatus.size() == 0) {
            synchronized (System.out) {
                System.out.println(beverageName + " is prepared");
            }
        } else if (beverageStatus.containsKey(BeverageStatus.NOT_PREPARED)) {
            if (beverageStatus.get(BeverageStatus.NOT_PREPARED).containsValue(IngredientStatus.INSUFFICIENT)) {
                synchronized (System.out) {
                    System.out.println(beverageName + " cannot be prepared because " +
                            beverageStatus.get(BeverageStatus.NOT_PREPARED).entrySet().iterator().next().getKey() + " is not sufficient");
                }

            } else if (beverageStatus.get(BeverageStatus.NOT_PREPARED).containsValue(IngredientStatus.UNAVAILABLE)) {
                synchronized (System.out) {
                    System.out.println(beverageName + " cannot be prepared because " +
                            beverageStatus.get(BeverageStatus.NOT_PREPARED).entrySet().iterator().next().getKey() + " is not available");
                }
            }
        }
    }
}
