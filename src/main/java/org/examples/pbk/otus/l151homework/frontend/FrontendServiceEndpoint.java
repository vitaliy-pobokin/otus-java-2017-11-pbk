package org.examples.pbk.otus.l151homework.frontend;

import org.examples.pbk.otus.l151homework.MessageSystemContext;
import org.examples.pbk.otus.l151homework.messageSystem.Address;
import org.examples.pbk.otus.l151homework.messageSystem.MsMessage;
import org.examples.pbk.otus.l151homework.messageSystem.MessageEndpoint;
import org.examples.pbk.otus.l151homework.messageSystem.exceptions.MessageSystemException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendServiceEndpoint implements MessageEndpoint {

    private static final Logger logger = Logger.getLogger("FrontendServiceEndpoint");

    private final MessageSystemContext context;
    private final Address address;

    public FrontendServiceEndpoint(MessageSystemContext context) {
        this.context = context;
        this.address = context.getFrontendAddress();
        init();
    }

    private void init() {
        try {
            context.getMessageSystem().registerEndpoint(this);
        } catch (MessageSystemException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystemContext getContext() {
        return context;
    }

    @Override
    public void handle(MsMessage message) {

    }
}
