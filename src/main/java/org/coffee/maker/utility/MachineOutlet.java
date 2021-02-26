package org.coffee.maker.utility;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MachineOutlet {

    @JsonProperty("count_n")
    int totalOutlets;

    public MachineOutlet(@JsonProperty("count_n")int totalOutlets) {
        this.totalOutlets = totalOutlets;
    }

    public int getTotalOutlets() {
        return totalOutlets;
    }
}
