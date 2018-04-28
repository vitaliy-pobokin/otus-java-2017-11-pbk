package org.examples.pbk.otus.l161homework.frontend.wsMessages;

import org.examples.pbk.otus.l161homework.app.FrontendService;
import org.examples.pbk.otus.l161homework.entity.ChatMessage;

import java.time.Instant;

public class ChatWsMessage extends MsgFromWebsocket {
    private String from;
    private String to;
    private String text;
    private Instant date;

    public ChatWsMessage(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.date = Instant.now();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public Instant getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ChatWsMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
