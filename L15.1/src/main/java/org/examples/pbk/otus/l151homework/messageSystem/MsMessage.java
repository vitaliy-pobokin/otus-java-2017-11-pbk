package org.examples.pbk.otus.l151homework.messageSystem;

public abstract class MsMessage {
    private Address from;
    private Address to;

    public MsMessage(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }
}
