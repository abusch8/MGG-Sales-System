package com.mgg;

public class Service extends Item {

    private double hourlyRate;

    public Service(String code, String name, double hourlyRate) {
        super(code, name);
        this.hourlyRate = hourlyRate;
    }
}
