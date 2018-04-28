package org.examples.pbk.otus.l161homework;

import com.google.inject.Singleton;
import org.examples.pbk.otus.l161homework.app.MessageSystemContext;
import org.examples.pbk.otus.l161homework.frontend.FrontendServiceImpl;
import org.examples.pbk.otus.l161homework.messageSystem.Address;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class AppContext {
    private static final Logger logger = Logger.getLogger(AppContext.class.getName());

    private FrontendServiceImpl frontendService;

    public AppContext() {
        init();
    }

    public void init() {
        Address msAddress = null;
        Address userServiceAddress = null;
        Address chatMessageServiceAddress = null;
        Address frontendServiceAddress = null;
        try {
            msAddress = new Address(InetAddress.getByName("127.0.0.1"), 5050);
            userServiceAddress = new Address(InetAddress.getByName("127.0.0.1"), 5051);
            chatMessageServiceAddress = new Address(InetAddress.getByName("127.0.0.1"), 5052);
            frontendServiceAddress = new Address(InetAddress.getByName("127.0.0.1"), 5054);
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Error while initializing context: " + e.getMessage());
            throw new RuntimeException(e);
        }
        MessageSystemContext context = new MessageSystemContext(msAddress);
        context.registerService("user-service", userServiceAddress);
        context.registerService("chat-message-service", chatMessageServiceAddress);
        context.registerService("frontend-service-2", frontendServiceAddress);
        frontendService = new FrontendServiceImpl(frontendServiceAddress, context);
        frontendService.init();
        logger.log(Level.INFO, "Context initialized.");
    }

    public void dispose() {
        frontendService.dispose();
        logger.log(Level.INFO, "Context destroyed.");
    }

    public FrontendServiceImpl getFrontendService() {
        return frontendService;
    }
}
