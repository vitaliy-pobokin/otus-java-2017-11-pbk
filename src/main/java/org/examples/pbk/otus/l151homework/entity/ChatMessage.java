package org.examples.pbk.otus.l151homework.entity;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String from;
    private String text;

    public ChatMessage() {
    }

    public ChatMessage(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String body) {
        this.text = body;
    }

    @Override
    public String toString() {
        return "ChatWsMessage{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
