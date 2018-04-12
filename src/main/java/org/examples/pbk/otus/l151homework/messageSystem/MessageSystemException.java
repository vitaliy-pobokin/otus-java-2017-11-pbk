package org.examples.pbk.otus.l151homework.messageSystem;

public class MessageSystemException extends Exception {
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
