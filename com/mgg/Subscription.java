package com.mgg;

public class Subscription extends Item {

    private double annualFee;

    public Subscription(String code, String name, double annualFee) {
        super(code, name);
        this.annualFee = annualFee;
    }
}
