package org.examples.pbk.otus.l151homework.frontend.wsMessages;

public class InfoWsMessage extends WsMessage {
    private String text;

    public InfoWsMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "InfoWsMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
