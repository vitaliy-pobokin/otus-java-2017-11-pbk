package org.examples.pbk.otus.l151homework.entity;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String from;
    private String body;

    public ChatMessage(String from, String body) {
        this.from = from;
        this.body = body;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
