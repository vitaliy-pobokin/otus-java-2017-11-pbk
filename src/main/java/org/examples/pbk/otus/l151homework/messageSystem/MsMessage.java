package org.examples.pbk.otus.l151homework.messageSystem;

public class MsMessage {
    private Address from;
    private Address to;
    private String body;

    public MsMessage(Address from, Address to, String body) {
        this.from = from;
        this.to = to;
        this.body = body;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
