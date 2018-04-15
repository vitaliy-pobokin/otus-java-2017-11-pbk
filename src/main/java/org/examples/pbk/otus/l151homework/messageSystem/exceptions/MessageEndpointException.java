package org.examples.pbk.otus.l151homework.messageSystem.exceptions;

public class MessageEndpointException extends Exception {
    public MessageEndpointException() {
    }

    public MessageEndpointException(String message) {
        super(message);
    }

    public MessageEndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageEndpointException(Throwable cause) {
        super(cause);
    }
}
