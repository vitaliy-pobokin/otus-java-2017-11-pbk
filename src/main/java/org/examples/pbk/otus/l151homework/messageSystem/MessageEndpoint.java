package org.examples.pbk.otus.l151homework.messageSystem;

import org.examples.pbk.otus.l151homework.MessageSystemContext;

public interface MessageEndpoint {
    Address getAddress();
    void handle(MsMessage message);
    MessageSystemContext getContext();
}
