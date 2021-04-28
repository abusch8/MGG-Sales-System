package com.mgg.entity;

/**
 * Class that contains all of the necessary information for storing an item.
 */
public abstract class Item {

    private final String code;
    private final String name;

    protected Item(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public abstract double calculatePrice();

    public abstract double calculateTax();

    public abstract String receiptToString();
}