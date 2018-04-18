package org.examples.pbk.otus.l151homework.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.examples.pbk.otus.l151homework.MessageSystemContext;
import org.examples.pbk.otus.l151homework.dbService.entity.ChatMessage;
import org.examples.pbk.otus.l151homework.dbService.entity.User;
import org.examples.pbk.otus.l151homework.messageSystem.Address;
import org.examples.pbk.otus.l151homework.messageSystem.MsMessage;
import org.examples.pbk.otus.l151homework.messageSystem.MessageEndpoint;
import org.examples.pbk.otus.l151homework.messageSystem.exceptions.MessageSystemException;
import org.examples.pbk.otus.l151homework.messageSystem.msMessages.AuthRequestMessage;
import org.examples.pbk.otus.l151homework.messageSystem.msMessages.AuthResponseMessage;
import org.examples.pbk.otus.l151homework.messageSystem.msMessages.DbOperationRequestMessage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbServiceEndpoint implements MessageEndpoint {

    private static final Logger logger = Logger.getLogger("DbServiceEndpoint");

    private final MessageSystemContext context;
    private final Address address;
    private ChatMessageService chatMessageService;
    private UserService userService;

    public DbServiceEndpoint(MessageSystemContext context) {
        this.context = context;
        this.address = context.getDbAddress();
        this.chatMessageService = new ChatMessageService();
        this.userService = new UserService();
        init();
    }

    private void init() {
        try {
            context.getMessageSystem().registerEndpoint(this);
        } catch (MessageSystemException e) {
            logger.log(Level.SEVERE, e.toString());
        }
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
        if (message.getType().equals("login")) {
            User user = null;
            try {
                user = userService.findByName(message.getUsername());
            } catch (Exception e) {
            }
            if (user != null && user.getPassword().equals(message.getPassword())) {
                success = true;
            }
            sendAuthResponseMessage(message.getFrom(), "login", message.getUsername(), success);
        } else if (message.getType().equals("register")) {
            String username = message.getUsername();
            String password = message.getPassword();
            if (username != null && password != null) {
                try {
                    userService.create(new User(username, password));
                    success = true;
                } catch (Exception e) {
                }
            }
            sendAuthResponseMessage(message.getFrom(), "register", message.getUsername(), success);
        } else {
            logger.log(Level.WARNING, "Not supported message type: " + message.getType());
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

    private void sendAuthResponseMessage(Address to, String type, String username, boolean success) {
        AuthResponseMessage responseMessage = new AuthResponseMessage(getAddress(), to);
        responseMessage.setType(type);
        responseMessage.setUsername(username);
        responseMessage.setSuccess(success);
        try {
            getContext().getMessageSystem().sendMessage(responseMessage);
        } catch (MessageSystemException e) {
            logger.log(Level.SEVERE, "MessageSystemException: " + e.toString());
        }
    }

    private <T> T getObjectFromJsonString(String json, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        T result = mapper.readValue(json, valueType);
        return result;
    }
}
