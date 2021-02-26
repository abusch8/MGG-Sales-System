package com.mgg;

public class Address {

    private String STREET;
    private String CITY;
    private String STATE;
    private String ZIP;
    private String COUNTRY;

    public Address(String STREET, String CITY, String STATE, String ZIP, String COUNTRY) {
        this.STREET = STREET;
        this.CITY = CITY;
        this.STATE = STATE;
        this.ZIP = ZIP;
        this.COUNTRY = COUNTRY;
    }

    public String getSTREET() {
        return STREET;
    }

    public String getCITY() {
        return CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public String getZIP() {
        return ZIP;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }
}
