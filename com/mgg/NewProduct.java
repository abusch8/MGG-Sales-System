package com.mgg;

public class NewProduct extends Product {

    public NewProduct(String code, String name, double basePrice, int quantity) {
        super(code, name, basePrice, quantity);
    }

    public double calculatePrice() {
        return (double) Math.round((basePrice * quantity) * 100) / 100;
    }

    public double calculateTax() {
        return (double) Math.round(((basePrice * quantity) * .0725) * 100) / 100;
    }

    public String receiptToString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(String.format("(New Item #%s %d@$%.2f/ea)", this.getCode(), this.getQuantity(), this.getBasePrice()));
        formattedString.append(" ".repeat(Math.max(0, 57 - formattedString.length())));
        return String.format("%s\n\t%s$%10.2f", this.getName(), formattedString.toString(), this.calculatePrice());
    }
}
