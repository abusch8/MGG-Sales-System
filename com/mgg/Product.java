package com.mgg;

public abstract class Product extends Item {

    protected final double basePrice;
    protected int quantity;

    public Product(String code, String name, double basePrice) {
        super(code, name);
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
