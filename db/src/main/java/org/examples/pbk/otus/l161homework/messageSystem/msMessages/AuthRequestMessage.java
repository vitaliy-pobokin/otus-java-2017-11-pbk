package org.examples.pbk.otus.l161homework.messageSystem.msMessages;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;

import java.io.Serializable;

public class AuthRequestMessage extends MsMessage implements Serializable {

    private static final long serialVersionUID = -651442352133010176L;
    private AuthMessageType type;
    private String username;
    private String password;

    public AuthRequestMessage(Address from, Address to) {
        super(from, to);
    }

    public AuthMessageType getType() {
        return type;
    }

    public void setType(AuthMessageType type) {
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
