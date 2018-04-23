package org.examples.pbk.otus.l161homework.frontend;

import org.examples.pbk.otus.l161homework.frontend.wsMessages.WsMessage;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketSessionHandler {
    private Set<Session> sessions = new CopyOnWriteArraySet<Session>();

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void sendToSession(Session session, WsMessage message) {
        try {
            session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    public void sendToAllConnectedSessions(WsMessage message) {
        sessions.stream().forEach(session -> {
            try {
                session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    public List<String> getAllConnectedUsers() {
        List<String> users = new ArrayList<>();
        for (Session session : sessions) {
            users.add((String) session.getUserProperties().get("user"));
        }
        return users;
    }
}
