package org.examples.pbk.otus.l161homework.frontend;

import org.examples.pbk.otus.l161homework.AppContext;
import org.examples.pbk.otus.l161homework.frontend.decoders.WsMessageDecoder;
import org.examples.pbk.otus.l161homework.frontend.encoders.AuthWsMessageEncoder;
import org.examples.pbk.otus.l161homework.frontend.encoders.ChatWsMessageEncoder;
import org.examples.pbk.otus.l161homework.frontend.encoders.InfoWsMessageEncoder;
import org.examples.pbk.otus.l161homework.frontend.encoders.UsersWsMessageEncoder;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.ChatWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.LoginWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.RegisterWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.WsMessage;

import javax.ejb.EJB;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ServerEndpoint(value = "/messages",
        encoders = {InfoWsMessageEncoder.class, ChatWsMessageEncoder.class, UsersWsMessageEncoder.class, AuthWsMessageEncoder.class},
        decoders = WsMessageDecoder.class)
public class WebSocketMessageEndpoint {

    private Logger logger = Logger.getLogger("WsMessageEndpoint");

    @EJB
    private AppContext appContext;

    @OnOpen
    public void open(Session session) {
        logger.info("Connected " + session.getBasicRemote());
    }

    @OnClose
    public void close(Session session) {
        appContext.getFrontendServiceEndpoint().handleSessionClose(session);
        logger.info("Session closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("Error: " + error.toString());
    }

    @OnMessage
    public void handleMessage(WsMessage message, Session session) {
        if (message instanceof ChatWsMessage) {
            logger.info("ChatWsMessage received: " + message);
            ChatWsMessage msg = (ChatWsMessage) message;
            appContext.getFrontendServiceEndpoint().handleChatMessageFromWs(msg, session);
        } else if (message instanceof LoginWsMessage) {
            LoginWsMessage msg = (LoginWsMessage) message;
            appContext.getFrontendServiceEndpoint().handleLoginMessageFromWs(msg, session);
        } else if (message instanceof RegisterWsMessage) {
            RegisterWsMessage msg = (RegisterWsMessage) message;
            appContext.getFrontendServiceEndpoint().handleRegisterMessageFromWs(msg ,session);
        }
    }
}
