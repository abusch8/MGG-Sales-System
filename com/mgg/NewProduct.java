package com.mgg;

public class NewProduct extends Product {

    public NewProduct(String code, String name, double basePrice, int quantity) {
        super(code, name, basePrice, quantity);
    }

    public double calculatePrice() {
        return (double) Math.round(basePrice * quantity * 100) / 100;
    }

    public double calculateTax() {
        return (double) Math.round(((basePrice * quantity) * .0725) * 100) / 100;
    }
}
