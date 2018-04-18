package org.examples.pbk.otus.l151homework.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "CHAT_MESSAGE_ID")
    private long id;
    @Column(name = "CHAT_MESSAGE_FROM")
    private String from;
    @Column(name = "CHAT_MESSAGE_TO")
    private String to;
    @Column(name = "CHAT_MESSAGE_TEXT")
    private String text;
    @Column(name = "CHAT_MESSAGE_DATE")
    private Instant date;

    public ChatMessage() {
    }

    public ChatMessage(String from, String to, String text) {
        this.from = from;
        this.to = to;
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
