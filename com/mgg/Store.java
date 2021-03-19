package com.mgg;

public class Store {

    private final String storeCode;
    private final Employee manager;
    private final Address address;

    public Store(String storeCode, Employee manager, Address address) {
        this.storeCode = storeCode;
        this.manager = manager;
        this.address = address;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public Employee getManager() {
        return manager;
    }

    public Address getAddress() {
        return address;
    }
}
