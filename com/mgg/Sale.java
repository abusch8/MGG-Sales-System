package com.mgg;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class Sale {

    private String saleCode;
    private Store store;
    private Customer customer;
    private Employee salesperson;
    private List<Item> items;

    public Sale(String saleCode, Store store, Customer customer, Employee salesperson, List<Item> items) {
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

    public Customer getCustomer() {
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
        for(Item item : this.items) {
            if (item instanceof GiftCard) {
                subTotal += ((GiftCard) item).calculatePrice();
            } else if (item instanceof Product) {
                Product product = (Product) item;
                if (product instanceof NewProduct) {
                    subTotal += ((NewProduct) product).calculatePrice();
                } else if (product instanceof UsedProduct) {
                    subTotal += ((UsedProduct) product).calculatePrice();
                }
            } else if (item instanceof Service) {
                subTotal += ((Service) item).calculatePrice();
            } else if (item instanceof Subscription) {
                subTotal += ((Subscription) item).calculatePrice();
            }
        }
        return subTotal;
    }

    public double calculateTax() {
        double taxTotal = 0;
        for(Item item : this.items) {
            if (item instanceof GiftCard) {
                taxTotal += ((GiftCard) item).calculateTax();
            } else if (item instanceof Product) {
                Product product = (Product) item;
                if (product instanceof NewProduct) {
                    taxTotal += ((NewProduct) product).calculateTax();
                } else if (product instanceof UsedProduct) {
                    taxTotal += ((UsedProduct) product).calculateTax();
                }
            } else if (item instanceof Service) {
                taxTotal += ((Service) item).calculateTax();
            } else if (item instanceof Subscription) {
                taxTotal += 0;
            }
        }
        return taxTotal;
    }

    public double calculateDiscount() {
        double discountAmount = 0;
        if (customer instanceof PlatinumMember) {
            discountAmount = (this.calculateSubTotal() + this.calculateTax()) * .1;
        } else if (customer instanceof GoldMember) {
            discountAmount = (this.calculateSubTotal() + this.calculateTax()) * .05;
        } else if(customer.getPersonId().equals(salesperson.getPersonId())) {
            discountAmount = (this.calculateSubTotal() + this.calculateTax()) * .15;
        }
        return discountAmount;
    }

    public double calculateGrandTotal() {
        return ((this.calculateSubTotal() + this.calculateTax()) - this.calculateDiscount());
    }
}