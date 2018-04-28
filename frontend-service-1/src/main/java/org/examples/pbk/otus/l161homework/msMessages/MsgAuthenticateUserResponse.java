package org.examples.pbk.otus.l161homework.msMessages;

import org.examples.pbk.otus.l161homework.app.FrontendService;
import org.examples.pbk.otus.l161homework.app.MsgToFrontendService;
import org.examples.pbk.otus.l161homework.messageSystem.Address;

import java.io.Serializable;

public class MsgAuthenticateUserResponse extends MsgToFrontendService implements Serializable {

    private static final long serialVersionUID = 1781426031866486046L;

    private String username;
    private boolean success;

    public MsgAuthenticateUserResponse(Address from, Address to, String username, boolean success) {
        super(from, to);
        this.username = username;
        this.success = success;
    }

    @Override
    public void execute(FrontendService frontendService) {
        frontendService.handleAuthenticationResponse(username, success);
    }
}
