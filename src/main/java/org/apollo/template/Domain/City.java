package org.apollo.template.Domain;

public class City {


    public City(int zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    private int zipCode;
    private String city;

    public int getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return zipCode + " | " + city;
    }
}
