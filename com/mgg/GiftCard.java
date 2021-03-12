package com.mgg;

public class GiftCard extends Item {

    private double basePrice;

    public GiftCard(String code, String name, double basePrice) {
        super(code, name);
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double calculatePrice() {
        return this.basePrice;
    }

    public double calculateTax() {
        return (double) Math.round((this.basePrice * .0725) * 100) / 100;
    }
}
