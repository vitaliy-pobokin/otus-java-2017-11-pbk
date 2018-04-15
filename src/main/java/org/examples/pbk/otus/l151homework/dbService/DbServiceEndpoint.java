package org.examples.pbk.otus.l151homework.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examples.pbk.otus.l151homework.MessageSystemContext;
import org.examples.pbk.otus.l151homework.entity.ChatMessage;
import org.examples.pbk.otus.l151homework.messageSystem.Address;
import org.examples.pbk.otus.l151homework.messageSystem.Message;
import org.examples.pbk.otus.l151homework.messageSystem.MessageEndpoint;
import org.examples.pbk.otus.l151homework.messageSystem.exceptions.MessageEndpointException;
import org.examples.pbk.otus.l151homework.messageSystem.exceptions.MessageSystemException;
import org.examples.pbk.otus.l151homework.messages.MessageBody;
import org.examples.pbk.otus.l151homework.messages.MessageToDbBody;
import org.examples.pbk.otus.l151homework.messages.decoders.MessageBodyDecoder;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbServiceEndpoint implements MessageEndpoint {

    private static final Logger logger = Logger.getLogger("DbServiceEndpoint");

    private final MessageSystemContext context;
    private final Address address;
    private ChatMessageService chatMessageService;

    public DbServiceEndpoint(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
        this.chatMessageService = new ChatMessageService();
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
    public void handle(Message message) {
        MessageBodyDecoder msgBodyDecoder = new MessageBodyDecoder();
        MessageBody msgBody = msgBodyDecoder.decode(message.getBody());
        if (msgBody instanceof MessageToDbBody) {
            try {
                executeMessageMethod((MessageToDbBody) msgBody);
            } catch (MessageEndpointException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
    }

    private void executeMessageMethod(MessageToDbBody msgBody) throws MessageEndpointException {
        switch (msgBody.getEntity()) {
            case "ChatMessage":
                switch (msgBody.getMethod()) {
                    case "create":
                        ChatMessage chatMessage = null;
                        try {
                            chatMessage = getObjectFromJsonString(msgBody.getSubject(), ChatMessage.class);
                        } catch (IOException e) {
                            throw new MessageEndpointException("Cannot parse subject:" + msgBody.getSubject());
                        }
                        chatMessageService.create(chatMessage);
//                        sendMessageToFrontend();
                        break;
                }
        }
    }

    private <T> T getObjectFromJsonString(String json, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        T result = mapper.readValue(json, valueType);
        return result;
    }
}
