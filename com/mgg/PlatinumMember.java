package com.mgg;

import java.util.List;

public class PlatinumMember extends Customer {

    public PlatinumMember(String personId, String lastName, String firstName, Address address, List<String> emails) {
        super(personId, lastName, firstName, address, emails);
    }
}
