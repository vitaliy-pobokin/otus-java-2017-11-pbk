package org.examples.pbk.otus.l161homework.frontend;

import javax.websocket.Session;
import java.util.List;

public interface SessionHandler {

    void addSession(Session session);

    void removeSession(Session session);

    void sendToSession(Session session, Object object);

    void sendToAllSessions(Object object);

    List<Session> getAllSessions();
}
