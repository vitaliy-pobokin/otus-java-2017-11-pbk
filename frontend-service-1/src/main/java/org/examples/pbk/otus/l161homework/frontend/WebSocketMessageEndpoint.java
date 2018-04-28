package org.examples.pbk.otus.l161homework.frontend;

import com.google.inject.Inject;
import org.examples.pbk.otus.l161homework.AppContext;
import org.examples.pbk.otus.l161homework.frontend.decoders.WsMessageDecoder;
import org.examples.pbk.otus.l161homework.frontend.encoders.AuthWsMessageEncoder;
import org.examples.pbk.otus.l161homework.frontend.encoders.ChatWsMessageEncoder;
import org.examples.pbk.otus.l161homework.frontend.encoders.InfoWsMessageEncoder;
import org.examples.pbk.otus.l161homework.frontend.encoders.UsersWsMessageEncoder;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.ChatWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.LoginWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.RegisterWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.MsgFromWebsocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/messages",
        configurator = WebSocketEndpointConfigurator.class,
        encoders = {InfoWsMessageEncoder.class, ChatWsMessageEncoder.class, UsersWsMessageEncoder.class, AuthWsMessageEncoder.class},
        decoders = WsMessageDecoder.class)
public class WebSocketMessageEndpoint {

    private Logger logger = Logger.getLogger(WebSocketMessageEndpoint.class.getName());

    private WsService wsService;

    @Inject
    public WebSocketMessageEndpoint(AppContext context) {
        this.wsService = context.getFrontendService();
    }

    @OnOpen
    public void open(Session session) {
        logger.info("Connected " + session.getBasicRemote());
    }

    @OnClose
    public void close(Session session) {
        wsService.handleSessionClose(session);
        logger.info("Session closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("Error: " + error.toString());
    }

    @OnMessage
    public void handleMessage(MsgFromWebsocket message, Session session) {
        logger.log(Level.INFO, "MsgFromWebsocket received: " + message);
        if (message instanceof ChatWsMessage) {
            ChatWsMessage msg = (ChatWsMessage) message;
            wsService.handleChatWsMessage(session, msg);
        } else if (message instanceof LoginWsMessage) {
            LoginWsMessage msg = (LoginWsMessage) message;
            wsService.handleLoginWsMessage(session, msg);
        } else if (message instanceof RegisterWsMessage) {
            RegisterWsMessage msg = (RegisterWsMessage) message;
            wsService.handleRegisterWsMessage(session, msg);
        }
    }
}
