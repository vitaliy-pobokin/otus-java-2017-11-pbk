package org.examples.pbk.otus.l101homework.dataset;

import javax.persistence.*;

@Entity
@Table(name = "Phone")
public class PhoneDataSetHibernate extends DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PhoneNo")
    private long id;

    @ManyToOne
    @JoinColumn(name = "UserNo")
    private UsersDataSet user;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "PhoneNumberType")
    private String numberType;

    public PhoneDataSetHibernate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsersDataSet getUser() {
        return user;
    }

    public void setUser(UsersDataSet user) {
        this.user = user;
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
