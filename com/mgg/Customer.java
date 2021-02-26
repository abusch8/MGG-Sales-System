package com.mgg;

import java.util.List;

public class Customer extends Person {

    public Customer(String personId, Name name, Address address, List<String> emails) {
        super(personId, name, address, emails);
    }
}
