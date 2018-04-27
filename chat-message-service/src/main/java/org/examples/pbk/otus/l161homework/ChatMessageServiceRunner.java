package org.examples.pbk.otus.l161homework;

import org.examples.pbk.otus.l161homework.app.ChatMessageService;
import org.examples.pbk.otus.l161homework.app.MessageSystemContext;
import org.examples.pbk.otus.l161homework.chatMessageService.ChatMessageServiceImpl;
import org.examples.pbk.otus.l161homework.chatMessageService.SessionFactoryProvider;
import org.examples.pbk.otus.l161homework.messageSystem.Address;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatMessageServiceRunner {
    private static final Logger logger = Logger.getLogger(ChatMessageServiceRunner.class.getName());

    public static void main(String[] args) {
        Address msAddress = null;
        Address userServiceAddress = null;
        Address chatMessageServiceAddress = null;
        Address frontendServiceAddress = null;
        try {
            msAddress = new Address(InetAddress.getByName("127.0.0.1"), 5050);
            userServiceAddress = new Address(InetAddress.getByName("127.0.0.1"), 5051);
            chatMessageServiceAddress = new Address(InetAddress.getByName("127.0.0.1"), 5052);
            frontendServiceAddress = new Address(InetAddress.getByName("127.0.0.1"), 5053);
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Error while initializing context: " + e.getMessage());
            throw new RuntimeException(e);
        }
        MessageSystemContext context = new MessageSystemContext(msAddress);
        context.registerService("user-service", userServiceAddress);
        context.registerService("chat-message-service", chatMessageServiceAddress);
        context.registerService("frontend-service", frontendServiceAddress);
        ChatMessageService chatMessageService = new ChatMessageServiceImpl(chatMessageServiceAddress, context);
        SessionFactoryProvider.init();
        chatMessageService.init();
        logger.log(Level.INFO, "Chat-message-service started at:" + chatMessageService.getAddress());
    }
}
