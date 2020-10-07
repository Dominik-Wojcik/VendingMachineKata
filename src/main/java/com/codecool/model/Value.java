package com.codecool.model;

public enum Value {
    NICKEL(0.05F),
    DIME(0.10F),
    QUARTER(0.25F),
    NOT_WORTHY(0);

    private final float value;

    Value(float value) {

        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
