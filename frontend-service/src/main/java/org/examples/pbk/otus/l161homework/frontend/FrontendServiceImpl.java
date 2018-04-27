package org.examples.pbk.otus.l161homework.frontend;

import org.examples.pbk.otus.l161homework.app.FrontendService;
import org.examples.pbk.otus.l161homework.app.MessageSystemContext;
import org.examples.pbk.otus.l161homework.entity.ChatMessage;
import org.examples.pbk.otus.l161homework.entity.User;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.*;
import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MessageHandler;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;
import org.examples.pbk.otus.l161homework.messageSystem.SocketMessageHandler;
import org.examples.pbk.otus.l161homework.msMessages.MsgAuthenticateUser;
import org.examples.pbk.otus.l161homework.msMessages.MsgPersistChatMessage;
import org.examples.pbk.otus.l161homework.msMessages.MsgRegisterUser;

import javax.websocket.Session;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendServiceImpl implements FrontendService, WsService {
    private static final String USER_SERVICE_NAME = "user-service";
    private static final String CHAT_MESSAGE_SERVICE_NAME = "chat-message-service";
    private static final int THREAD_POOL_SIZE = 1;
    private static final Logger logger = Logger.getLogger(FrontendServiceImpl.class.getName());

    private final Address address;
    private final MessageSystemContext context;
    private final ExecutorService executor;
    private final SessionHandler sessionHandler;

    private Socket socket;
    private MessageHandler messageHandler;

    private final ConcurrentMap<String, Session> waitingForLogin = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Session> waitingForRegistration = new ConcurrentHashMap<>();

    public FrontendServiceImpl(Address address, MessageSystemContext context) {
        this.address = address;
        this.context = context;
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.sessionHandler = new WebSocketSessionHandler();
    }

    @Override
    public void init() {
        try {
            this.socket = new Socket(
                    context.getMessageSystemAddress().getInetAddress(),
                    context.getMessageSystemAddress().getPort(),
                    address.getInetAddress(),
                    address.getPort());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while establishing socket connection: " + e.getMessage());
        }
        this.messageHandler = new SocketMessageHandler(socket);
        messageHandler.start();

        executor.execute(() -> {
            while (!executor.isTerminated()) {
                messageHandler.getMessage().execute(this);
            }
        });
    }

    @Override
    public void dispose() {
        messageHandler.shutdown();
        executor.shutdown();
        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while closing socket connection: " + e.getMessage());
        }
    }

    @Override
    public void makeAuthenticationRequest(String username, String password) {
        MsMessage msg = new MsgAuthenticateUser(address, context.getServiceAddress(USER_SERVICE_NAME), username, password);
        messageHandler.sendMessage(msg);
    }

    @Override
    public void handleAuthenticationResponse(String username, boolean success) {
        Session session = waitingForLogin.get(username);
        if (success) {
            authorizeUser(session, username);
        } else {
            sessionHandler.sendToSession(session, new AuthWsMessage(username, false));
        }
        waitingForLogin.remove(username);
    }

    @Override
    public void makeRegistrationRequest(User user) {
        MsMessage msg = new MsgRegisterUser(address, context.getServiceAddress(USER_SERVICE_NAME), user);
        messageHandler.sendMessage(msg);
    }

    @Override
    public void handleRegistrationResponse(User user, boolean success) {
        Session session = waitingForRegistration.get(user.getUsername());
        if (success) {
            authorizeUser(session, user.getUsername());
        } else {
            sessionHandler.sendToSession(session, new AuthWsMessage(user.getUsername(), false));
        }
        waitingForRegistration.remove(user.getUsername());
    }

    @Override
    public void handleChatMessage(ChatMessage chatMessage) {
        MsMessage msg = new MsgPersistChatMessage(address, context.getServiceAddress(CHAT_MESSAGE_SERVICE_NAME), chatMessage);
        messageHandler.sendMessage(msg);
    }

    @Override
    public void handleLoginWsMessage(Session session, LoginWsMessage message) {
        waitingForLogin.put(message.getUsername(), session);
        makeAuthenticationRequest(message.getUsername(), message.getPassword());
    }

    @Override
    public void handleRegisterWsMessage(Session session, RegisterWsMessage message) {
        waitingForRegistration.put(message.getUsername(), session);
        User user = new User(message.getUsername(), message.getPassword());
        makeRegistrationRequest(user);
    }

    @Override
    public void handleChatWsMessage(Session session, ChatWsMessage message) {
        String user = (String) session.getUserProperties().get("user");
        if (user == null || !user.equals(message.getFrom())) {
            logger.info("Unknown user: " + user);
        } else {
            ChatMessage chatMessage = new ChatMessage(message.getFrom(), message.getTo(), message.getText());
            chatMessage.setDate(message.getDate());
            handleChatMessage(chatMessage);
            sessionHandler.sendToAllSessions(message);
        }
    }

    @Override
    public void handleSessionClose(Session session) {
        sessionHandler.removeSession(session);
        String user = (String) session.getUserProperties().get("user");
        if (user != null) {
            sessionHandler.sendToAllSessions(new InfoWsMessage(user + " has left the chat!"));
            sessionHandler.sendToAllSessions(new UsersWsMessage(getAllConnectedUsers()));
        }
    }

    private void authorizeUser(Session session, String username) {
        session.getUserProperties().put("user", username);
        sessionHandler.addSession(session);
        sessionHandler.sendToSession(session, new AuthWsMessage(username, true));
        sessionHandler.sendToAllSessions(new InfoWsMessage(username + " has joined the chat!"));
        sessionHandler.sendToAllSessions(new UsersWsMessage(getAllConnectedUsers()));
    }

    private List<String> getAllConnectedUsers() {
        List<String> users = new ArrayList<>();
        List<Session> sessions = sessionHandler.getAllSessions();
        for (Session session : sessions) {
            users.add((String) session.getUserProperties().get("user"));
        }
        return users;
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
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
}
