package com.mgg;

import java.util.List;

/**
 * Class that stores all the necessary information for keeping track of a customer/employee.
 */
public abstract class Person {

    private String personId;
    private String lastName;
    private String firstName;
    private Address address;
    private List<String> emails;

    protected Person(String personId, String lastName, String firstName, Address address, List<String> emails) {
        this.personId = personId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.emails = emails;
    }

    public String getPersonId() {
        return personId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getEmails() {
        return emails;
    }

    public String emailListToString() {
        StringBuilder emailListToString = new StringBuilder();
        for(int i = 0; i < emails.size(); i++) {
            emailListToString.append(emails.get(i));
            if(i + 1 < emails.size()) {
                emailListToString.append(", ");
            }
        }
        return String.format("[%s]", emailListToString);
    }
}