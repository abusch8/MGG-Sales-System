package com.mgg.entity;

import java.util.List;

/**
 * Main class for handling all of the variables that are involved with a sale.
 */
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

    /**
     * Calculates the subtotal of all the items(all of the items prices before taxes)
     *
     * @return Sub total amount
     */
    public double calculateSubTotal() {
        double subTotal = 0;
        for (Item item : this.items) {
            subTotal += item.calculatePrice();
        }
        return Math.round(subTotal * 100.0) / 100.0;
    }

    /**
     * Calculates the total tax of a sale. (Adds all of the item taxes togethers
     *
     * @return Total tax amount of sale
     */
    public double calculateTax() {
        double taxTotal = 0;
        for (Item item : this.items) {
            taxTotal += item.calculateTax();
        }
        return Math.round(taxTotal * 100.0) / 100.0;
    }

    /**
     * Calculates the discount amount of the sale.
     *
     * @return discount amount
     */
    public double calculateDiscount() {
        double discountAmount = (this.calculateSubTotal() + this.calculateTax()) * customer.getDiscountAmount();
        return Math.round(discountAmount * 100.0) / 100.0;
    }

    /**
     * Calculates the total after adding the subtotal and tax, and subtracting the discount(if applicable).
     *
     * @return grand total of the sale
     */
    public double calculateGrandTotal() {
        return (this.calculateSubTotal() + this.calculateTax()) - this.calculateDiscount();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getSaleCode());
        sb.append(" ".repeat(Math.max(0, 11 - sb.length())));
        sb.append(this.getStore().getStoreCode());
        sb.append(" ".repeat(Math.max(0, 22 - sb.length())));
        sb.append(this.getCustomer().getLastName() + "," + this.getCustomer().getFirstName());
        sb.append(" ".repeat(Math.max(0, 43 - sb.length())));
        sb.append(this.getSalesperson().getLastName() + "," + this.getSalesperson().getFirstName());
        sb.append(" ".repeat(Math.max(0, 64 - sb.length())));
        sb.append(String.format("$%10.2f", this.calculateGrandTotal()));
        return sb.toString();
    }
}
