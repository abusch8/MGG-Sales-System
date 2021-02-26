package com.mgg;

public class NewProduct extends Item{

    double basePrice;

    public NewProduct(String code, String name, double basePrice) {
        super(code, name);
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
