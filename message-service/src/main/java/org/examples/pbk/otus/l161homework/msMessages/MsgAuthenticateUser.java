package org.examples.pbk.otus.l161homework.msMessages;

import org.examples.pbk.otus.l161homework.app.MsgToUserService;
import org.examples.pbk.otus.l161homework.app.UserService;
import org.examples.pbk.otus.l161homework.entity.User;
import org.examples.pbk.otus.l161homework.messageSystem.Address;

import java.io.Serializable;

public class MsgAuthenticateUser extends MsgToUserService implements Serializable {
    
    private static final long serialVersionUID = -7018468155037692754L;

    private String username;
    private String password;

    public MsgAuthenticateUser(Address from, Address to, String username, String password) {
        super(from, to);
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute(UserService userService) {
        boolean success = false;
        User user = userService.findByName(username);
        if (user != null && user.getPassword().equals(password)) {
            success = true;
        }
        userService.getMessageHandler().sendMessage(
                new MsgAuthenticateUserResponse(getTo(), getFrom(), username, success));
    }
}
