package org.coffee.maker.utility;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(value = "machine")
public class CoffeeMachine {
    @JsonProperty("outlets")
    private final MachineOutlet outlet;
    @JsonProperty("total_items_quantity")
    private final LinkedHashMap<String, Integer> allIngredients;
    @JsonProperty("beverages")
    private final LinkedHashMap<String, Map<String, Integer>> allBeverages;

    public CoffeeMachine(@JsonProperty("outlets") MachineOutlet outlet,
                         @JsonProperty("total_items_quantity") LinkedHashMap<String, Integer> allIngredients,
                         @JsonProperty("beverages") LinkedHashMap<String, Map<String, Integer>> allBeverages) {
        this.outlet = outlet;
        this.allIngredients = allIngredients;
        this.allBeverages = allBeverages;
    }

    public MachineOutlet getOutlet() {
        return outlet;
    }

    public LinkedHashMap<String, Integer> getAllIngredients() {
        return allIngredients;
    }

    public LinkedHashMap<String, Map<String, Integer>> getAllBeverages() {
        return allBeverages;
    }

    @Override
    public String toString() {
        return "CoffeeMachine{" +
                "outlet=" + outlet +
                ", allIngredients=" + allIngredients +
                ", allBeverages=" + allBeverages +
                '}';
    }
}
