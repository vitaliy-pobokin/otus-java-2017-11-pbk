package org.examples.pbk.otus.l161homework.messageSystem;

public interface Service {
    Address getAddress();
    MessageSystemContext getContext();
    MessageHandler getMessageHandler();
}
