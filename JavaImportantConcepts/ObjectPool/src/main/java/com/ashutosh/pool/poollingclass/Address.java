package com.ashutosh.pool.poollingclass;

import java.util.List;

public class Address {
    String fullAddress;
    String city;
    String state;
    String country;
    List<Number> phoneNumbers;

    /**
     *
     * @param fullAddress
     * @param city
     * @param state
     * @param country
     * @param phoneNumbers
     */
    public Address(String fullAddress, String city, String state, String country, List<Number> phoneNumbers) {
        this.fullAddress = fullAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phoneNumbers = phoneNumbers;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Number> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<Number> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "Address{" +
                "fullAddress='" + fullAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}
