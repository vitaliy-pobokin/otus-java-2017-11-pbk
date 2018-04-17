package org.examples.pbk.otus.l151homework.frontend.messages;

public class RegisterWsMessage extends WsMessage {
    private String username;
    private String password;

    public RegisterWsMessage(String username, String password) {
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
