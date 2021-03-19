package com.mgg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Subscription extends Item {

    private final double annualFee;
    private final LocalDate beginDate;
    private final LocalDate endDate;

    public Subscription(String code, String name, double annualFee, LocalDate beginDate, LocalDate endDate) {
        super(code, name);
        this.annualFee = annualFee;
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

    public double getDayDifference() {
        return ((double) (beginDate.until(endDate, ChronoUnit.DAYS)) + 1);
    }

    public double calculatePrice() {
        return ((annualFee / 365) * this.getDayDifference()*100) / 100;
    }
}
