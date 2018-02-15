package org.examples.pbk.otus.l101homework.dataset;

public class PhoneDataSet extends DataSet {

    private long id;

    private String phoneNumber;

    private String numberType;

    public PhoneDataSet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", numberType='" + numberType + '\'' +
                '}';
    }
}
