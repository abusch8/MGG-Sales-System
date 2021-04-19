package com.mgg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Subscription extends Item {

    private final double annualFee;
    private LocalDate beginDate;
    private LocalDate endDate;

    public Subscription(String code, String name, double annualFee) {
        super(code, name);
        this.annualFee = annualFee;
    }

    public Subscription(Subscription subscription, LocalDate beginDate, LocalDate endDate) {
        this(subscription.getCode(), subscription.getName(), subscription.getAnnualFee());
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public double getAnnualFee() {
        return annualFee;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Calculates the difference in days between two dates
     * @return difference in days
     */
    public double getDayDifference() {
        return ((double) (beginDate.until(endDate, ChronoUnit.DAYS)) + 1);
    }

    /**
     * Calculates the price of the item
     * @return price (before tax)
     */
    public double calculatePrice() {
        return ((annualFee / 365) * this.getDayDifference()*100) / 100;
    }

    /**
     * There is no tax for a subscription, so we return 0
     * @return 0
     */
    public double calculateTax() {
        return 0;
    }

    /**
     * Puts the entire receipt for a sale into a string format
     * @return receipt in a formatted string
     */
    public String receiptToString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(String.format("(Subscription #%s %d days@$%.2f/yr)", this.getCode(), (int) this.getDayDifference(), this.getAnnualFee()));
        formattedString.append(" ".repeat(Math.max(0, 57 - formattedString.length())));
        return String.format("%s\n    %s$%10.2f", this.getName(), formattedString, this.calculatePrice());
    }
}
