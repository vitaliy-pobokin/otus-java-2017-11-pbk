package org.examples.pbk.otus.l151homework.frontend.messages;

import java.util.List;

public class UsersMessage extends Message {
    private List<String> users;

    public UsersMessage(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "UsersMessage{" +
                "users=" + users +
                '}';
    }
}
