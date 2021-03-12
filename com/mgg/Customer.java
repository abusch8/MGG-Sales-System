package com.mgg;

import java.util.List;

public class Customer extends Person {

    public Customer(String personId, String lastName, String firstName, Address address, List<String> emails) {
        super(personId, lastName, firstName, address, emails);
    }
}
