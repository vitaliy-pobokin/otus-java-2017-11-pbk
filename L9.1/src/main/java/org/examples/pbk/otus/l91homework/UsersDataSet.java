package org.examples.pbk.otus.l91homework;

import org.examples.pbk.otus.l91homework.dataset.DataSet;

public class UsersDataSet extends DataSet {
    private long id;
    private String name;
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

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
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
