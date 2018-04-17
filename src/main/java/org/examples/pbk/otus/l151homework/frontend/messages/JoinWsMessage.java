package org.examples.pbk.otus.l151homework.frontend.messages;

public class JoinWsMessage extends WsMessage {
    private String user;

    public JoinWsMessage(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "JoinWsMessage{" +
                "user='" + user + '\'' +
                '}';
    }
}
