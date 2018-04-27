package org.examples.pbk.otus.l161homework.app;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;
import org.examples.pbk.otus.l161homework.messageSystem.Service;

public abstract class MsgToFrontendService extends MsMessage {

    public MsgToFrontendService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void execute(Service service) {
        if (service instanceof FrontendService) {
            execute((FrontendService) service);
        }
    }

    public abstract void execute(FrontendService frontendService);
}
