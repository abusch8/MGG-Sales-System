package com.mgg;

public class UsedProduct extends Product {

    public UsedProduct(String code, String name, double basePrice, int quantity) {
        super(code, name, basePrice, quantity);
    }

    public double calculatePrice() {
        return (double) Math.round(((getReducedPrice()) * quantity) * 100) / 100;
    }

    public double getReducedPrice() {
        return Math.round((basePrice * .8)* 100.0) / 100.0;
    }

    public double calculateTax() {
        return Math.round(((getReducedPrice() * quantity) * .0725) * 100.0) / 100.0;
    }
}