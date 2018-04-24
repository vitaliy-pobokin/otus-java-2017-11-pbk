package org.examples.pbk.otus.l161homework.messageSystem.exceptions;

public class MessageSystemException extends RuntimeException {
    public MessageSystemException() {
    }

    public MessageSystemException(String message) {
        super(message);
    }

    public MessageSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageSystemException(Throwable cause) {
        super(cause);
    }
}
