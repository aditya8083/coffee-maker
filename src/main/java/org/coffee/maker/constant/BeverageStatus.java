package org.coffee.maker.constant;

public enum BeverageStatus {

    PREPARED("prepared"), NOT_PREPARED("not_prepared");

    private String status;

    BeverageStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
