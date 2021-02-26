package com.mgg;

public abstract class Item {

    private String code;
    private String name;

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
}