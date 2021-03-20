package com.mgg;

import java.util.List;

public class Sale {

    private final String saleCode;
    private final Store store;
    private final Person customer;
    private final Employee salesperson;
    private final List<Item> items;

    public Sale(String saleCode, Store store, Person customer, Employee salesperson, List<Item> items) {
        this.saleCode = saleCode;
        this.store = store;
        this.customer = customer;
        this.salesperson = salesperson;
        this.items = items;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public Store getStore() {
        return store;
    }

    public Person getCustomer() {
        return customer;
    }

    public Employee getSalesperson() {
        return salesperson;
    }

    public List<Item> getItems() {
        return items;
    }

    public double calculateSubTotal() {
        double subTotal = 0;
        for (Item item : this.items) {
            subTotal += item.calculatePrice();
        }
        return Math.round(subTotal * 100.0) / 100.0;
    }

    public double calculateTax() {
        double taxTotal = 0;
        for (Item item : this.items) {
            taxTotal += item.calculateTax();
        }
        return Math.round(taxTotal *  100.0) / 100.0;
    }

    public double calculateDiscount() {
        double discountAmount = (this.calculateSubTotal() + this.calculateTax()) * customer.getDiscountAmount();
        return Math.round(discountAmount * 100.0) / 100.0;
    }

    public double calculateGrandTotal() {
        return (this.calculateSubTotal() + this.calculateTax()) - this.calculateDiscount();
    }
}
