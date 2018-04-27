package org.examples.pbk.otus.l161homework.app;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;
import org.examples.pbk.otus.l161homework.messageSystem.Service;

public abstract class MsgToUserService extends MsMessage {

    public MsgToUserService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void execute(Service service) {
        if (service instanceof UserService) {
            execute((UserService) service);
        }
    }

    public abstract void execute(UserService userService);
}
