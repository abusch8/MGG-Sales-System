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

    public void setSTREET(String STREET) {
        this.STREET = STREET;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String toString() {
        return STREET + "," + CITY + "," + STATE + "," + ZIP + "," + COUNTRY;
    }
}
