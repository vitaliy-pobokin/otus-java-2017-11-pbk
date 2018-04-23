package org.examples.pbk.otus.l161homework.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.*;
import org.examples.pbk.otus.l161homework.messageSystem.*;
import org.examples.pbk.otus.l161homework.messageSystem.msMessages.*;

import javax.websocket.Session;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendServiceEndpoint implements MessageEndpoint {
    private static final int THREAD_POOL_SIZE = 1;
    private static final Logger logger = Logger.getLogger("FrontendServiceEndpoint");

    private WebSocketSessionHandler sessionHandler;

    private final MessageSystemContext context;
    private final Address address;

    private Socket socket;
    private SocketMessageHandler messageHandler;
    private ExecutorService executor;

    private final Map<String, Session> waitingForLogin = new ConcurrentHashMap<>();
    private final Map<String, Session> waitingForRegistration = new ConcurrentHashMap<>();

    public FrontendServiceEndpoint(MessageSystemContext context) {
        this.context = context;
        this.address = context.getFrontendAddress();
        this.sessionHandler = new WebSocketSessionHandler();
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void init() {
        this.socket = createSocketConnection(context.getMessageSystemAddress().getHost(),
                context.getMessageSystemAddress().getPort());
        this.messageHandler = new SocketMessageHandler(socket);
        messageHandler.start();

        executor.execute(() -> {
            while (true) {
                try {
                    handle(messageHandler.getMessage());
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "Error while handling message: " + e.getMessage());
                }
            }
        });
    }

    public void dispose() {
        messageHandler.shutdown();
        executor.shutdown();
        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while closing socket connection: " + e.getMessage());
        }
    }

    private Socket createSocketConnection(String host, int port) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while establishing socket connection: " + e.getMessage());
        }
        return socket;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystemContext getContext() {
        return context;
    }

    @Override
    public void handle(MsMessage message) {
        if (message instanceof AuthResponseMessage) {
            handleAuthResponseMessage((AuthResponseMessage) message);
        } else if (message instanceof DbOperationResponseMessage) {
            handleDbOperationResponseMessage((DbOperationResponseMessage) message);
        } else {
            logger.log(Level.WARNING, "Cannot handle message: " + message.toString());
        }
    }

    public void handleLoginMessageFromWs(LoginWsMessage message, Session session) {
        waitingForLogin.put(message.getUsername(), session);
        AuthRequestMessage authMsg = new AuthRequestMessage(getAddress(), getContext().getDbAddress());
        authMsg.setType(AuthMessageType.LOGIN);
        authMsg.setUsername(message.getUsername());
        authMsg.setPassword(message.getPassword());
        messageHandler.sendMessage(authMsg);
    }

    public void handleRegisterMessageFromWs(RegisterWsMessage message, Session session) {
        waitingForRegistration.put(message.getUsername(), session);
        AuthRequestMessage authMsg = new AuthRequestMessage(getAddress(), getContext().getDbAddress());
        authMsg.setType(AuthMessageType.REGISTER);
        authMsg.setUsername(message.getUsername());
        authMsg.setPassword(message.getPassword());
        messageHandler.sendMessage(authMsg);
    }

    public void handleChatMessageFromWs(ChatWsMessage message, Session session) {
        String user = (String) session.getUserProperties().get("user");
        if (user == null || !user.equals(message.getFrom())) {
            logger.info("Unknown user: " + user);
        } else {
            persistChatMessage(message);
            sessionHandler.sendToAllConnectedSessions(message);
        }
    }

    public void handleSessionClose(Session session) {
        sessionHandler.removeSession(session);
        String user = (String) session.getUserProperties().get("user");
        if (user != null) {
            sessionHandler.sendToAllConnectedSessions(new InfoWsMessage(user + " has left the chat!"));
            sessionHandler.sendToAllConnectedSessions(new UsersWsMessage(sessionHandler.getAllConnectedUsers()));
        }
    }

    private void persistChatMessage(ChatWsMessage msg) {
        try {
            DbOperationRequestMessage message = new DbOperationRequestMessage(this.getAddress(), context.getDbAddress());
            message.setEntityName("ChatMessage");
            message.setMethodName("create");
            message.setSubject(serializeToJson(msg));
            messageHandler.sendMessage(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private String serializeToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }

    private void handleAuthResponseMessage(AuthResponseMessage message) {
        if (message.getType().equals(AuthMessageType.LOGIN)) {
            handleLoginMessage(message);
        } else if (message.getType().equals(AuthMessageType.REGISTER)) {
            handleRegisterMessage(message);
        }
    }

    private void handleDbOperationResponseMessage(DbOperationResponseMessage message) {

    }

    private void handleLoginMessage(AuthResponseMessage message) {
        Session session = waitingForLogin.get(message.getUsername());
        if (message.isSuccess()) {
            authorizeUser(session, message.getUsername());
        } else {
            sendUnsuccessfulAuthenticationNotification(session, message.getUsername());
        }
        waitingForLogin.remove(message.getUsername());
    }

    private void handleRegisterMessage(AuthResponseMessage message) {
        Session session = waitingForRegistration.get(message.getUsername());
        if (message.isSuccess()) {
            authorizeUser(session, message.getUsername());
        } else {
            sendUnsuccessfulAuthenticationNotification(session, message.getUsername());
        }
        waitingForRegistration.remove(message.getUsername());
    }

    private void authorizeUser(Session session, String username) {
        session.getUserProperties().put("user", username);
        sessionHandler.addSession(session);
        sessionHandler.sendToSession(session, new AuthWsMessage(username, true));
        sessionHandler.sendToAllConnectedSessions(new InfoWsMessage(username + " has joined the chat!"));
        sessionHandler.sendToAllConnectedSessions(new UsersWsMessage(sessionHandler.getAllConnectedUsers()));
    }

    private void sendUnsuccessfulAuthenticationNotification(Session session, String username) {
        sessionHandler.sendToSession(session, new AuthWsMessage(username, false));
    }
}
