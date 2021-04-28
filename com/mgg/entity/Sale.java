package com.mgg.entity;

import java.util.List;

/**
 * Main class for handling all of the variables that are involved with a sale.
 */
public class Sale {

    private final String saleCode;
    private final com.mgg.entity.Store store;
    private final com.mgg.entity.Person customer;
    private final com.mgg.entity.Employee salesperson;
    private final List<com.mgg.entity.Item> items;

    public Sale(String saleCode, com.mgg.entity.Store store, com.mgg.entity.Person customer, com.mgg.entity.Employee salesperson, List<com.mgg.entity.Item> items) {
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

    public List<com.mgg.entity.Item> getItems() {
        return items;
    }

    /**
     * Calculates the subtotal of all the items(all of the items prices before taxes)
     * @return Sub total amount
     */
    public double calculateSubTotal() {
        double subTotal = 0;
        for (com.mgg.entity.Item item : this.items) {
            subTotal += item.calculatePrice();
        }
        return Math.round(subTotal * 100.0) / 100.0;
    }

    /**
     * Calculates the total tax of a sale. (Adds all of the item taxes togethers
     * @return Total tax amount of sale
     */
    public double calculateTax() {
        double taxTotal = 0;
        for (Item item : this.items) {
            taxTotal += item.calculateTax();
        }
        return Math.round(taxTotal *  100.0) / 100.0;
    }

    /**
     * Calculates the discount amount of the sale.
     * @return discount amount
     */
    public double calculateDiscount() {
        double discountAmount = (this.calculateSubTotal() + this.calculateTax()) * customer.getDiscountAmount();
        return Math.round(discountAmount * 100.0) / 100.0;
    }

    /**
     * Calculates the total after adding the subtotal and tax, and subtracting the discount(if applicable).
     * @return grand total of the sale
     */
    public double calculateGrandTotal() {
        return (this.calculateSubTotal() + this.calculateTax()) - this.calculateDiscount();
    }
}
