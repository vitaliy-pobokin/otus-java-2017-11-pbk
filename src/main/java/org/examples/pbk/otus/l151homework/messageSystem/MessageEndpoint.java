package org.examples.pbk.otus.l151homework.messageSystem;

public interface MessageEndpoint {
    Address getAddress();
    void handle(Message message);
}
