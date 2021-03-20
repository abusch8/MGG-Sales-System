package com.mgg;

public class GiftCard extends Item {

    private final double basePrice;

    public GiftCard(String code, String name, double basePrice) {
        super(code, name);
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double calculatePrice() {
        return this.basePrice;
    }

    public double calculateTax() {
        return (double) Math.round((this.basePrice * .0725) * 100) / 100;
    }

    public String receiptToString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(String.format("(Gift Card #%s)", this.getCode()));
        formattedString.append(" ".repeat(Math.max(0, 57 - formattedString.length())));
        return String.format("%s\n\t%s$%10.2f", this.getName(), formattedString.toString(), this.calculatePrice());
    }
}
