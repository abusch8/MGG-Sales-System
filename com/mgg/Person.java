package com.mgg;

import java.util.ArrayList;

public class Person {

    private String personId;
    private String type;
    private Name name;
    private Address address;
    private ArrayList<String> emails;

    public Person(String personId, String type, Name name, Address address, ArrayList<String> emails) {
        this.personId = personId;
        this.type = type;
        this.name = name;
        this.address = address;
        this.emails = emails;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public String toString() {
        String emailString = String.join(",", emails);
        return personId + "," + type + "," + name.getLastName() + "," + name.getFirstName() + "," + address.getSTREET() + "," + address.getCITY() + "," + address.getSTATE()
                + "," + address.getZIP() + "," + address.getCOUNTRY() + "," + emailString;
    }
}
