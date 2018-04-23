package org.examples.pbk.otus.l161homework.messageSystem.msMessages;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;

public class AuthResponseMessage extends MsMessage {

    private AuthMessageType type;
    private String username;
    private boolean success;

    public AuthResponseMessage(Address from, Address to) {
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AuthResponseMessage{" +
                "type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", success=" + success +
                '}';
    }
}
