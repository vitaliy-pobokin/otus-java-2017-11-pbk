package org.examples.pbk.otus.l161homework.frontend.wsMessages;

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
        return "RegisterWsMessage{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
