package org.examples.pbk.otus.l161homework.messageSystem;

public interface MessageEndpoint {
    Address getAddress();
    void handle(MsMessage message);
    MessageSystemContext getContext();
}
