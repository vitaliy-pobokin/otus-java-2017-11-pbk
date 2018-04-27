package org.examples.pbk.otus.l161homework.frontend.wsMessages;

public class LoginWsMessage extends MsgFromWebsocket {
    private String username;
    private String password;

    public LoginWsMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginWsMessage{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
