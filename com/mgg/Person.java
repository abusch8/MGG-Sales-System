package com.mgg;

import java.util.List;

public abstract class Person {

    private String personId;
    private Name name;
    private Address address;
    private List<String> emails;

    protected Person(String personId, Name name, Address address, List<String> emails) {
        this.personId = personId;
        this.name = name;
        this.address = address;
        this.emails = emails;
    }

    public String getPersonId() {
        return personId;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getEmails() {
        return emails;
    }
}
