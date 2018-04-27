package org.examples.pbk.otus.l161homework.frontend.wsMessages;

public class InfoWsMessage extends MsgToWebsocket {
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
