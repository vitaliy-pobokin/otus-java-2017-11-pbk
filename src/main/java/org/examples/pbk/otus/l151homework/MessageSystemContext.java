package org.examples.pbk.otus.l151homework;

import org.examples.pbk.otus.l151homework.messageSystem.Address;
import org.examples.pbk.otus.l151homework.messageSystem.MessageSystem;

public class MessageSystemContext {

    private final MessageSystem messageSystem;
    private Address dbAddress;
    private Address frontendAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }

    public Address getFrontendAddress() {
        return frontendAddress;
    }

    public void setFrontendAddress(Address frontendAddress) {
        this.frontendAddress = frontendAddress;
    }
}
