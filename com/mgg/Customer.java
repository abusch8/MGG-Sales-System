package com.mgg;

import java.util.List;

public class Customer extends Person {

    private static final double discountAmount = 0;

    public Customer(String personId, String lastName, String firstName, Address address, List<String> emails) {
        super(personId, lastName, firstName, address, emails);
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}