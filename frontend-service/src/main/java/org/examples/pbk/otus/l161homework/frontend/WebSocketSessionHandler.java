package org.examples.pbk.otus.l161homework.frontend;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketSessionHandler implements SessionHandler {
    private ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();

    public void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    public void removeSession(Session session) {
        sessions.remove(session.getId());
    }

    public void sendToSession(Session session, Object object) {
        try {
            session.getBasicRemote().sendObject(object);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    public void sendToAllSessions(Object object) {
        sessions.values().forEach(session -> {
            try {
                session.getBasicRemote().sendObject(object);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Session> getAllSessions() {
        return new ArrayList<>(sessions.values());
    }
}
