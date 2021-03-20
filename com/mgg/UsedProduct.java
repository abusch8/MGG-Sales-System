package com.mgg;

public class UsedProduct extends Product {

    public UsedProduct(String code, String name, double basePrice, int quantity) {
        super(code, name, basePrice, quantity);
    }

    public double calculatePrice() {
        return (double) Math.round(((calculateReducedPrice()) * quantity) * 100) / 100;
    }

    public double calculateReducedPrice() {
        return Math.round((basePrice * .8)* 100.0) / 100.0;
    }

    public double calculateTax() {
        return Math.round(((calculateReducedPrice() * quantity) * .0725) * 100.0) / 100.0;
    }

    public String receiptToString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(String.format("(Used Item #%s %d@($%.2f -> $%.2f)/ea)", this.getCode(), this.getQuantity(), this.getBasePrice(), this.calculateReducedPrice()));
        formattedString.append(" ".repeat(Math.max(0, 57 - formattedString.length())));
        return String.format("%s\n\t%s$%10.2f", this.getName(), formattedString.toString(), this.calculatePrice());
    }
}