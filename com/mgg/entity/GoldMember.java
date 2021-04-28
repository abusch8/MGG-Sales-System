package com.mgg.entity;

import java.util.List;

public class GoldMember extends Person {

    private static final double discountAmount = 0.05;

    public GoldMember(String personId, String lastName, String firstName, Address address, List<String> emails) {
        super(personId, lastName, firstName, address, emails);
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}