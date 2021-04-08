package com.mgg;

public class GiftCard extends Item {

    private double basePrice;

    public GiftCard(String code, String name) {
        super(code, name);
    }

    public GiftCard(GiftCard giftCard, double basePrice) {
        this(giftCard.getCode(), giftCard.getName());
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Calculates the price of the item before tax
     * @return price
     */
    public double calculatePrice() {
        return this.basePrice;
    }

    /**
     * Calculates the tax of the item
     * @return tax price
     */
    public double calculateTax() {
        return (double) Math.round((this.basePrice * .0725) * 100) / 100;
    }

    /**
     * Puts the entire receipt for a sale into a string format
     * @return receipt in a formatted string
     */
    public String receiptToString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(String.format("(Gift Card #%s)", this.getCode()));
        formattedString.append(" ".repeat(Math.max(0, 57 - formattedString.length())));
        return String.format("%s\n\t%s$%10.2f", this.getName(), formattedString.toString(), this.calculatePrice());
    }
}
