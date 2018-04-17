package org.examples.pbk.otus.l151homework.frontend.messages;

public class AuthWsMessage {
    private String username;
    private boolean success;

    public AuthWsMessage(String username, boolean success) {
        this.username = username;
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "AuthWsMessage{" +
                "username='" + username + '\'' +
                ", success=" + success +
                '}';
    }
}
