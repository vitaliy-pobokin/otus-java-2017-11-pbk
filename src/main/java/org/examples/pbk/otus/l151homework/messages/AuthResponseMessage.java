package org.examples.pbk.otus.l151homework.messages;

import org.examples.pbk.otus.l151homework.messageSystem.Address;
import org.examples.pbk.otus.l151homework.messageSystem.MsMessage;

public class AuthResponseMessage extends MsMessage {

    private String username;
    private boolean success;

    public AuthResponseMessage(Address from, Address to) {
        super(from, to);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AuthResponseMessage{" +
                "username='" + username + '\'' +
                ", success=" + success +
                '}';
    }
}
