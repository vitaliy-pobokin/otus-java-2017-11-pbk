package org.examples.pbk.otus.l101homework.dataset;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class UsersDataSet extends DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserNo")
    private long id;

    @Column(name = "UserName")
    private String name;

    @Column(name = "UserAge")
    private int age;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "AddrNo")
    private AddressDataSet address;

    @OneToMany (fetch = FetchType.EAGER,
            mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneDataSetHibernate> phones;

    @Transient
    private PhoneDataSet phone;

    public UsersDataSet() {

    }

    public UsersDataSet(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSetHibernate> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSetHibernate> phones) {
        this.phones = phones;
    }

    public PhoneDataSet getPhone() {
        return phone;
    }

    public void setPhone(PhoneDataSet phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                (phones != null ? (", phones=" + phones) : "") +
                (phone != null ? (", phone=" + phone) : "") +
                '}';
    }
}
