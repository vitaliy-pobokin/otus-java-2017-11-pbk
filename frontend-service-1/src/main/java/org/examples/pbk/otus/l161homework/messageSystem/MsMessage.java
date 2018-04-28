package org.examples.pbk.otus.l161homework.messageSystem;

import java.io.Serializable;

public abstract class MsMessage implements Serializable {
    private static final long serialVersionUID = -5845845079433720259L;
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

    public abstract void execute(Service service);
}
