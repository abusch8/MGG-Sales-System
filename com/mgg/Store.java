package com.mgg;

public class Store {

    private String storeCode;
    private Employee manager;
    private Address address;

    public Store(String storeCode, Employee manager, Address address) {
        this.storeCode = storeCode;
        this.manager = manager;
        this.address = address;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public Employee getManagerCode() {
        return manager;
    }

    public Address getAddress() {
        return address;
    }
}
