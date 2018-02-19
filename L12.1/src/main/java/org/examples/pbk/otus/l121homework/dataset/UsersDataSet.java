package org.examples.pbk.otus.l121homework.dataset;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
