package org.examples.pbk.otus.l161homework.frontend;

import org.examples.pbk.otus.l161homework.frontend.wsMessages.ChatWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.LoginWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.RegisterWsMessage;

import javax.websocket.Session;

public interface WsService {

    void handleLoginWsMessage(Session session, LoginWsMessage message);

    void handleRegisterWsMessage(Session session, RegisterWsMessage message);

    void handleChatWsMessage(Session session, ChatWsMessage message);

    void handleSessionClose(Session session);
}
