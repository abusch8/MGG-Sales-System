package com.mgg.entity;

import java.util.List;

public class PlatinumMember extends Person {

    private static final double discountAmount = 0.1;

    public PlatinumMember(String personId, String lastName, String firstName, Address address, List<String> emails) {
        super(personId, lastName, firstName, address, emails);
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}