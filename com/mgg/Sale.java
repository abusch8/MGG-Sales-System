package com.mgg;

import java.util.List;

public class Sale {

    private String saleCode;
    private Store store;
    private Person customer;
    private Employee salesperson;
    private List<Item> items;

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
        return Math.round(subTotal * 100.0) / 100.0;
    }

    public double calculateTax() {
        double taxTotal = 0;
        for (Item item : this.items) {
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
        return Math.round(taxTotal *  100.0)/ 100.0;
    }

    public double calculateDiscount() {
        //System.out.printf("%s %s\n", customer.getFirstName(), customer.getLastName());
        double discountAmount = 0;
        if (customer instanceof Customer) {
            if (customer instanceof PlatinumMember) {
                discountAmount = (this.calculateSubTotal() + this.calculateTax()) * .1;
            } else if (customer instanceof GoldMember) {
                discountAmount = (this.calculateSubTotal() + this.calculateTax()) * .05;
            }
        } else if (customer instanceof Employee) {
            discountAmount = (this.calculateSubTotal() + this.calculateTax()) * .15;
        }
        return Math.round(discountAmount * 100.0) / 100.0;
    }

    public double calculateGrandTotal() {
        return (this.calculateSubTotal() + this.calculateTax()) - this.calculateDiscount();
    }
}
