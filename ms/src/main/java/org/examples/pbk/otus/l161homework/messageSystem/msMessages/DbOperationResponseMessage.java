package org.examples.pbk.otus.l161homework.messageSystem.msMessages;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;

import java.io.Serializable;

public class DbOperationResponseMessage extends MsMessage implements Serializable {

    private static final long serialVersionUID = 2023896651938045094L;
    private String entityName;
    private String methodName;
    private String result;

    public DbOperationResponseMessage(Address from, Address to) {
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DbOperationResponseMessage{" +
                "entityName='" + entityName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
