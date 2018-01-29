package org.examples.pbk.otus.l91homework;

import org.examples.pbk.otus.l91homework.dataset.DataSet;

public class AddressDataSet extends DataSet {
    private long id;
    private String country;
    private String postalCode;
    private String city;
    private String streetAddress;

    public AddressDataSet() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                '}';
    }
}
