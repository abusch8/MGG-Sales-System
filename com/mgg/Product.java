package com.mgg;

public abstract class Product extends Item {

    protected double basePrice;
    protected int quantity;

    public Product(String code, String name, double basePrice, int quantity) {
        super(code, name);
        this.basePrice = basePrice;
        this.quantity = quantity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double calculateTax() {
        return (double) Math.round((basePrice * quantity) * .0725 * 100) / 100;
    }
}
