package org.examples.pbk.otus.l151homework.messages;

public class MessageToDbBody extends MessageBody {

    private String entity;
    private String method;
    private String subject;

    public MessageToDbBody(String entity, String method, String subject) {
        this.entity = entity;
        this.method = method;
        this.subject = subject;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
