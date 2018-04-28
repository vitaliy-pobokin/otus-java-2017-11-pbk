package org.examples.pbk.otus.l161homework.msMessages;

import org.examples.pbk.otus.l161homework.app.MsgToUserService;
import org.examples.pbk.otus.l161homework.app.UserService;
import org.examples.pbk.otus.l161homework.entity.User;
import org.examples.pbk.otus.l161homework.messageSystem.Address;

import java.io.Serializable;

public class MsgRegisterUser extends MsgToUserService implements Serializable {

    private static final long serialVersionUID = -1579179642778018998L;

    private User user;

    public MsgRegisterUser(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void execute(UserService userService) {
        boolean success = false;
        userService.create(user);
        if (user.getId() > 0) {
            success = true;
        }
        userService.getMessageHandler().sendMessage(
                new MsgRegisterUserResponse(getTo(), getFrom(), user, success)
        );
    }
}
