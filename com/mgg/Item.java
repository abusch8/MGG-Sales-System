package com.mgg;

public class Item {

    private String code;
    private String type;
    private String name;
    private double price;

    public Item(String code, String type, String name, double price) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return code + "," + type + "," + name + "," + price;
    }
}
