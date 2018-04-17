package org.examples.pbk.otus.l151homework.frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examples.pbk.otus.l151homework.AppContext;
import org.examples.pbk.otus.l151homework.frontend.decoders.WsMessageDecoder;
import org.examples.pbk.otus.l151homework.frontend.encoders.*;
import org.examples.pbk.otus.l151homework.frontend.messages.*;
import org.examples.pbk.otus.l151homework.messageSystem.Address;
import org.examples.pbk.otus.l151homework.messageSystem.MsMessage;
import org.examples.pbk.otus.l151homework.messages.MessageBody;
import org.examples.pbk.otus.l151homework.messages.MessageToDbBody;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint(value = "/messages",
        encoders = {JoinWsMessageEncoder.class, InfoWsMessageEncoder.class, ChatWsMessageEncoder.class, UsersWsMessageEncoder.class, AuthWsMessageEncoder.class},
        decoders = WsMessageDecoder.class)
public class MessageWebSocketServer {

    private Logger logger = Logger.getLogger("WsMessage Endpoint");

    @EJB
    private AppContext appContext;

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
            sessionHandler.sendToAllConnectedSessions(new InfoWsMessage(user + " has left the chat!"));
            sessionHandler.sendToAllConnectedSessions(new UsersWsMessage(sessionHandler.getAllConnectedUsers()));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("Error: " + error.toString());
    }

    @OnMessage
    public void handleMessage(WsMessage message, Session session) {
        if (message instanceof JoinWsMessage) {
            logger.info("JoinWsMessage received: " + message);
            JoinWsMessage msg = (JoinWsMessage) message;
            session.getUserProperties().put("user", msg.getUser());
            sessionHandler.addSession(session);
            sessionHandler.sendToAllConnectedSessions(new InfoWsMessage(msg.getUser() + " has joined the chat!"));
            sessionHandler.sendToAllConnectedSessions(new UsersWsMessage(sessionHandler.getAllConnectedUsers()));
        } else if (message instanceof ChatWsMessage) {
            String user = (String) session.getUserProperties().get("user");
            ChatWsMessage msg = (ChatWsMessage) message;
            if (user == null || !user.equals(msg.getFrom())) {
                logger.info("Unknown user: " + user);
            } else {
                persistMessage(msg);
                sessionHandler.sendToAllConnectedSessions(msg);
            }
        }
    }

    private void persistMessage(ChatWsMessage msg) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String subject = mapper.writeValueAsString(msg);
            MessageBody msgBody = new MessageToDbBody("ChatWsMessage", "create", subject);
            Address from = appContext.getMessageSystemContext().getFrontendAddress();
            Address to = appContext.getMessageSystemContext().getDbAddress();
            String stringMsgBody = mapper.writeValueAsString(msgBody);
            MsMessage message = new MsMessage(from, to, stringMsgBody);
            appContext.getMessageSystem().sendMessage(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }
}
