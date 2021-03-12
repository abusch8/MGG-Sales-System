package com.mgg;

import java.util.List;

public class Employee extends Person {

    public Employee(String personId, String lastName, String firstName, Address address, List<String> emails) {
        super(personId, lastName, firstName, address, emails);
    }
}
