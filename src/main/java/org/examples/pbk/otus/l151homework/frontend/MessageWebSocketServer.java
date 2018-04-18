package org.examples.pbk.otus.l151homework.frontend;

import org.examples.pbk.otus.l151homework.AppContext;
import org.examples.pbk.otus.l151homework.frontend.decoders.WsMessageDecoder;
import org.examples.pbk.otus.l151homework.frontend.encoders.*;
import org.examples.pbk.otus.l151homework.frontend.wsMessages.*;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint(value = "/wsMessages",
        encoders = {InfoWsMessageEncoder.class, ChatWsMessageEncoder.class, UsersWsMessageEncoder.class, AuthWsMessageEncoder.class},
        decoders = WsMessageDecoder.class)
public class MessageWebSocketServer {

    private Logger logger = Logger.getLogger("WsMessage Endpoint");

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
