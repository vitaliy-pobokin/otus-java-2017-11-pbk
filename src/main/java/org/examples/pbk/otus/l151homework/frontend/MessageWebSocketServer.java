package org.examples.pbk.otus.l151homework.frontend;

import org.examples.pbk.otus.l151homework.frontend.decoders.MessageDecoder;
import org.examples.pbk.otus.l151homework.frontend.encoders.ChatMessageEncoder;
import org.examples.pbk.otus.l151homework.frontend.encoders.InfoMessageEncoder;
import org.examples.pbk.otus.l151homework.frontend.encoders.JoinMessageEncoder;
import org.examples.pbk.otus.l151homework.frontend.encoders.UsersMessageEncoder;
import org.examples.pbk.otus.l151homework.frontend.messages.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint(value = "/messages",
        encoders = {JoinMessageEncoder.class, InfoMessageEncoder.class, ChatMessageEncoder.class, UsersMessageEncoder.class},
        decoders = MessageDecoder.class)
public class MessageWebSocketServer {

    private Logger logger = Logger.getLogger("Message Endpoint");

    @Inject
    private WebSocketSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        logger.info("Connected " + session.getBasicRemote());
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
        String user = (String) session.getUserProperties().get("user");
        if (user != null) {
            sessionHandler.sendToAllConnectedSessions(new InfoMessage(user + " has left the chat!"));
            sessionHandler.sendToAllConnectedSessions(new UsersMessage(sessionHandler.getAllConnectedUsers()));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("Error: " + error.toString());
    }

    @OnMessage
    public void handleMessage(Message message, Session session) {
        if (message instanceof JoinMessage) {
            logger.info("JoinMessage received: " + message);
            JoinMessage msg = (JoinMessage) message;
            session.getUserProperties().put("user", msg.getUser());
            sessionHandler.addSession(session);
            sessionHandler.sendToAllConnectedSessions(new InfoMessage(msg.getUser() + " has joined the chat!"));
            sessionHandler.sendToAllConnectedSessions(new UsersMessage(sessionHandler.getAllConnectedUsers()));
        } else if (message instanceof ChatMessage) {
            String user = (String) session.getUserProperties().get("user");
            ChatMessage msg = (ChatMessage) message;
            if (user == null || !user.equals(msg.getFrom())) {
                logger.info("Unknown user: " + user);
            } else {
                sessionHandler.sendToAllConnectedSessions(msg);
            }
        }
    }
}
