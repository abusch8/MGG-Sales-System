package com.mgg.entity;

public abstract class Product extends Item {

    protected final double basePrice;
    protected int quantity;

    public Product(String code, String name, double basePrice) {
        super(code, name);
        this.basePrice = basePrice;
    }

    public Product(Product product, int quantity) {
        this(product.getCode(), product.getName(), product.getBasePrice());
        this.quantity = quantity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
