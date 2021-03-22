package com.mgg;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Person {

    private static final double discountAmount = 0.15;
    private final List<Sale> sales = new ArrayList<>();

    public Employee(String personId, String lastName, String firstName, Address address, List<String> emails) {
        super(personId, lastName, firstName, address, emails);
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        for (Sale sale : sales) {
            if (sale.getSalesperson().getPersonId().equals(this.getPersonId())) {
                this.sales.add(sale);
            }
        }
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}