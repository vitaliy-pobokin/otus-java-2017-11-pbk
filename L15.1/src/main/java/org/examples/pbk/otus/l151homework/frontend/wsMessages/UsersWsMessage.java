package org.examples.pbk.otus.l151homework.frontend.wsMessages;

import java.util.List;

public class UsersWsMessage extends WsMessage {
    private List<String> users;

    public UsersWsMessage(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "UsersWsMessage{" +
                "users=" + users +
                '}';
    }
}
