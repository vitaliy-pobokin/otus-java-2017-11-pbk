package org.examples.pbk.otus.l21homework;

public class User {
    private long id;
    private String username;
    private String password;
    private UserInner1 userInner1;

    public User(long id, String username, String password, UserInner1 userInner1) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userInner1 = userInner1;
    }

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
