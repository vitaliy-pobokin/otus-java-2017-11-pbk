package org.examples.pbk.otus.l161homework.messageSystem;

public class MessageSystemContext {

    private Address messageSystemAddress;
    private Address dbAddress;
    private Address frontendAddress;

    public MessageSystemContext(Address messageSystemAddress) {
        this.messageSystemAddress = messageSystemAddress;
    }

    public Address getMessageSystemAddress() {
        return messageSystemAddress;
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
