package com.mgg.entity;

import java.util.ArrayList;
import java.util.List;

public class Store {

    private final String storeCode;
    private final com.mgg.entity.Employee manager;
    private final com.mgg.entity.Address address;
    private final List<Sale> sales = new ArrayList<>();

    public Store(String storeCode, com.mgg.entity.Employee manager, com.mgg.entity.Address address) {
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

    public List<Sale> getSales() {
        return sales;
    }

    /**
     * Takes in a list of sales and identifies which sales belong to this store.
     * @param sales a list of type "Sale"
     */
    public void setSales(List<Sale> sales) {
        for (Sale sale : sales) {
            if (sale.getStore().getStoreCode().equals(this.getStoreCode())) {
                this.sales.add(sale);
            }
        }
    }
}