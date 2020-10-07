package com.codecool.model;

public enum Product {
    COLA(1.0F),
    CHIPS(0.5F),
    CANDY(0.65F),
    NOTHING(-1.0F);


    private final float price;

    Product(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
}
