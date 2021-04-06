package com.mgg;

public class UsedProduct extends Product {

    public UsedProduct(String code, String name, double basePrice) {
        super(code, name, basePrice);
    }

    /**
     * Calculates price of item before tax.
     * @return price
     */
    public double calculatePrice() {
        return (double) Math.round(((calculateReducedPrice()) * quantity) * 100) / 100;
    }

    /**
     * Used Products are only 80% of their normal price, so we calculate the reduced price
     * @return reduced price
     */
    public double calculateReducedPrice() {
        return Math.round((basePrice * .8)* 100.0) / 100.0;
    }

    /**
     * Calculates tax of item.
     * @return tax price
     */
    public double calculateTax() {
        return Math.round(((calculateReducedPrice() * quantity) * .0725) * 100.0) / 100.0;
    }

    /**
     * Puts the entire receipt for a sale into a string format
     * @return receipt in a formatted string
     */
    public String receiptToString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(String.format("(Used Item #%s %d@($%.2f -> $%.2f)/ea)", this.getCode(), this.getQuantity(), this.getBasePrice(), this.calculateReducedPrice()));
        formattedString.append(" ".repeat(Math.max(0, 57 - formattedString.length())));
        return String.format("%s\n\t%s$%10.2f", this.getName(), formattedString.toString(), this.calculatePrice());
    }
}