package org.examples.pbk.otus.l161homework.messageSystem;

public interface MessageHandler {
    MsMessage getMessage();
    void sendMessage(MsMessage message);
    void start();
    void shutdown();
}
