package com.mgg;

/**
 * Class that contains all of the necessary information for storing an item.
 */
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