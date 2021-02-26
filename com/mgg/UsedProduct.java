package com.mgg;

public class UsedProduct extends Item {

    private double basePrice;

    public UsedProduct(String code, String name, double basePrice) {
        super(code, name);
        this.basePrice = basePrice;
    }
}