package org.examples.pbk.otus.l161homework;

import org.examples.pbk.otus.l161homework.app.MessageSystemContext;
import org.examples.pbk.otus.l161homework.app.UserService;
import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.userService.SessionFactoryProvider;
import org.examples.pbk.otus.l161homework.userService.UserServiceImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceRunner {
    private static final Logger logger = Logger.getLogger(UserServiceRunner.class.getName());

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
        UserService userService = new UserServiceImpl(userServiceAddress, context);
        SessionFactoryProvider.init();
        userService.init();
        logger.log(Level.INFO, "User-service started at:" + userService.getAddress());
    }
}
