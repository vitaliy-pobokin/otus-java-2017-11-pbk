package org.examples.pbk.otus.l161homework.messageSystem;

import org.examples.pbk.otus.l161homework.app.MessageSystemContext;

public interface MessageEndpoint {
    Address getAddress();
    MessageSystemContext getContext();
    MessageHandler getMessageHandler();
}
