package org.examples.pbk.otus.l151homework.messageSystem.msMessages;

import org.examples.pbk.otus.l151homework.messageSystem.Address;
import org.examples.pbk.otus.l151homework.messageSystem.MsMessage;

public class AuthRequestMessage extends MsMessage {

    private String type;
    private String username;
    private String password;

    public AuthRequestMessage(Address from, Address to) {
        super(from, to);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "AuthRequestMessage{" +
                "type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
