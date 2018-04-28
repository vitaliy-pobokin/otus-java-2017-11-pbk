package org.examples.pbk.otus.l161homework.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CHAT_USER")
public class User implements Serializable {
    private static final long serialVersionUID = -8335882590502405159L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "CHAT_USER_ID")
    private long id;
    @Column(name = "CHAT_USER_USERNAME", unique = true, nullable = false)
    private String username;
    @Column(name = "CHAT_USER_PASSWORD", nullable = false)
    private String password;

    public User() {
    }

    public User(String username, String password) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
