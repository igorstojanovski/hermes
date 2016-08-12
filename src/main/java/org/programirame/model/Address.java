package org.programirame.model;


public class Address {
    private String city;
    private String postalCode;
    private String longAddress;
    private String country;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLongAddress() {
        return longAddress;
    }

    public void setLongAddress(String longAddress) {
        this.longAddress = longAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Address(String city, String postalCode, String longAddress, String country) {
        this.city = city;
        this.postalCode = postalCode;
        this.longAddress = longAddress;
        this.country = country;
    }

    public Address() {
    }
}
