package com.mgg;

import java.util.List;

public class Employee extends Person {

    public Employee(String personId, Name name, Address address, List<String> emails) {
        super(personId, name, address, emails);
    }
}
