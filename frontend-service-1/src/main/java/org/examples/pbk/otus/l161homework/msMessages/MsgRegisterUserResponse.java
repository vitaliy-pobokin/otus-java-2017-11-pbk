package org.examples.pbk.otus.l161homework.msMessages;

import org.examples.pbk.otus.l161homework.app.FrontendService;
import org.examples.pbk.otus.l161homework.app.MsgToFrontendService;
import org.examples.pbk.otus.l161homework.entity.User;
import org.examples.pbk.otus.l161homework.messageSystem.Address;

import java.io.Serializable;

public class MsgRegisterUserResponse extends MsgToFrontendService implements Serializable {

    private static final long serialVersionUID = -3923707540599728950L;

    private User user;
    private boolean success;

    public MsgRegisterUserResponse(Address from, Address to, User user, boolean success) {
        super(from, to);
        this.user = user;
        this.success = success;
    }

    @Override
    public void execute(FrontendService frontendService) {
        frontendService.handleRegistrationResponse(user, success);
    }
}
