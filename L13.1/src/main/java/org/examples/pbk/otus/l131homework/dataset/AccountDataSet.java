package org.examples.pbk.otus.l131homework.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class AccountDataSet extends DataSet {

    @Id
    @Column(name = "AccNo")
    private long id;

    @Column(name = "AccUsername")
    private String username;

    @Column(name = "AccPassword")
    private String password;

    public AccountDataSet() {

    }

    public AccountDataSet(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
