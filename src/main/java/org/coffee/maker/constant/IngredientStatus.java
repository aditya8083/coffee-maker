package org.coffee.maker.constant;

public enum IngredientStatus {

    AVAILABLE("available"), UNAVAILABLE("unavailable"), INSUFFICIENT("insufficient");

    private String status;

    IngredientStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
