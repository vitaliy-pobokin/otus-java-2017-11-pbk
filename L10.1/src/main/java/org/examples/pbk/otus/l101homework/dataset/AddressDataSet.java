package org.examples.pbk.otus.l101homework.dataset;

import org.examples.pbk.otus.l101homework.dataset.DataSet;

import javax.persistence.*;

@Entity
@Table (name = "Address")
public class AddressDataSet extends DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AddrNo")
    private long id;

    @Column(name = "AddrCountry")
    private String country;

    @Column(name = "AddrPostalCode")
    private String postalCode;

    @Column(name = "AddrCity")
    private String city;

    @Column(name = "AddrStreet")
    private String street;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
