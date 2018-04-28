package org.examples.pbk.otus.l161homework.frontend.wsMessages;

public class AuthWsMessage extends MsgToWebsocket {
    private String user;
    private boolean success;

    public AuthWsMessage(String user, boolean success) {
        this.user = user;
        this.success = success;
    }

    public String getUser() {
        return user;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "AuthWsMessage{" +
                "user='" + user + '\'' +
                ", success=" + success +
                '}';
    }
}
