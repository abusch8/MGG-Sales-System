package com.mgg;

import java.util.List;

public class GoldMember extends Customer {

    public GoldMember(String personId, String lastName, String firstName, Address address, List<String> emails) {
        super(personId, lastName, firstName, address, emails);
    }
}