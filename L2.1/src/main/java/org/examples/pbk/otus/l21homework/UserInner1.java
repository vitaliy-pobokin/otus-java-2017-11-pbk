package org.examples.pbk.otus.l21homework;

public class UserInner1 {
    private long id;
    private String username;
    private String password;
    private UserInner2[] usersInner2;

    public UserInner1(long id, String username, String password, UserInner2[] usersInner2) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.usersInner2 = usersInner2;
    }
}
