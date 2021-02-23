package com.mgg;

public class Store {

    private String storeCode;
    private String managerCode;
    private Address address;

    public Store(String storeCode, String managerCode, Address address) {
        this.storeCode = storeCode;
        this.managerCode = managerCode;
        this.address = address;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String toString() {
        return storeCode + "," + managerCode + "," + address.toString();
    }
}
