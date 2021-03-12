package com.mgg;

public class UsedProduct extends Product {

    public UsedProduct(String code, String name, double basePrice, int quantity) {
        super(code, name, basePrice, quantity);
    }

    public double calculatePrice() {
        return (double) Math.round(((basePrice * .8) * quantity) * 100) / 100;
    }

    public double getReducedPrice() {
        return (basePrice * .8);
    }
}