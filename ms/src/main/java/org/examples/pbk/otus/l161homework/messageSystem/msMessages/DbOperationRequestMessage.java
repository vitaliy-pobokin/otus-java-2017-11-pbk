package org.examples.pbk.otus.l161homework.messageSystem.msMessages;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;

import java.io.Serializable;

public class DbOperationRequestMessage extends MsMessage implements Serializable {

    private static final long serialVersionUID = -7232227976668937479L;
    private String entityName;
    private String methodName;
    private String subject;

    public DbOperationRequestMessage(Address from, Address to) {
        super(from, to);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "DbOperationRequestMessage{" +
                "entityName='" + entityName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
