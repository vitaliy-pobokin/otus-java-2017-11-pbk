package org.examples.pbk.otus.l161homework.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.examples.pbk.otus.l161homework.messageSystem.*;
import org.examples.pbk.otus.l161homework.messageSystem.msMessages.AuthMessageType;
import org.examples.pbk.otus.l161homework.messageSystem.msMessages.AuthRequestMessage;
import org.examples.pbk.otus.l161homework.messageSystem.msMessages.AuthResponseMessage;
import org.examples.pbk.otus.l161homework.messageSystem.msMessages.DbOperationRequestMessage;
import org.examples.pbk.otus.l161homework.dbService.entity.ChatMessage;
import org.examples.pbk.otus.l161homework.dbService.entity.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbServiceEndpoint implements MessageEndpoint {
    private static final int THREAD_POOL_SIZE = 1;
    private static final Logger logger = Logger.getLogger(DbServiceEndpoint.class.getName());

    private final MessageSystemContext context;
    private final Address address;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    private Socket socket;
    private SocketMessageHandler messageHandler;
    private ExecutorService executor;

    public DbServiceEndpoint(MessageSystemContext context) {
        this.context = context;
        this.address = context.getDbAddress();
        this.chatMessageService = new ChatMessageService();
        this.userService = new UserService();
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void init() {
        this.socket = createSocketConnection(
                context.getMessageSystemAddress().getInetAddress(),
                context.getMessageSystemAddress().getPort(),
                context.getDbAddress().getInetAddress(),
                context.getDbAddress().getPort());
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

    private Socket createSocketConnection(InetAddress inetAddress, int port, InetAddress localAddress, int localPort) {
        Socket socket = null;
        try {
            socket = new Socket(inetAddress, port, localAddress, localPort);
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
        if (message instanceof AuthRequestMessage) {
            handleAuthRequestMessage((AuthRequestMessage) message);
        } else if (message instanceof DbOperationRequestMessage) {
            handleDbOperationRequestMessage((DbOperationRequestMessage) message);
        } else {
            logger.log(Level.WARNING, "Cannot handle message: " + message.toString());
        }
    }

    private void handleAuthRequestMessage(AuthRequestMessage message) {
        boolean success = false;
        if (message.getType().equals(AuthMessageType.LOGIN)) {
            User user = null;
            try {
                user = userService.findByName(message.getUsername());
            } catch (Exception e) {
            }
            if (user != null && user.getPassword().equals(message.getPassword())) {
                success = true;
            }
            sendAuthResponseMessage(message.getFrom(), AuthMessageType.LOGIN, message.getUsername(), success);
        } else if (message.getType().equals(AuthMessageType.REGISTER)) {
            try {
                userService.create(new User(message.getUsername(), message.getPassword()));
                success = true;
            } catch (Exception e) {
            }
            sendAuthResponseMessage(message.getFrom(), AuthMessageType.REGISTER, message.getUsername(), success);
        }
    }

    private void handleDbOperationRequestMessage(DbOperationRequestMessage message) {
        switch (message.getEntityName()) {
            case "ChatMessage":
                switch (message.getMethodName()){
                    case "create":
                        ChatMessage chatMessage = null;
                        try {
                            chatMessage = getObjectFromJsonString(message.getSubject(), ChatMessage.class);
                        } catch (IOException e) {
                            logger.log(Level.WARNING, "Cannot parse subject:" + message.getSubject());
                        }
                        chatMessageService.create(chatMessage);
                        break;
                    default:
                        logger.log(Level.INFO, "Method [" + message.getMethodName() + "] not supported.");
                }
                break;
            default:
                logger.log(Level.INFO, "Entity [" + message.getEntityName() + "] not supported.");
        }
    }

    private void sendAuthResponseMessage(Address to, AuthMessageType type, String username, boolean success) {
        AuthResponseMessage responseMessage = new AuthResponseMessage(getAddress(), to);
        responseMessage.setType(type);
        responseMessage.setUsername(username);
        responseMessage.setSuccess(success);
        messageHandler.sendMessage(responseMessage);
    }

    private <T> T getObjectFromJsonString(String json, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(json, valueType);
    }
}
