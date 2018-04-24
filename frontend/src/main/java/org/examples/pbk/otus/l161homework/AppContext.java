package org.examples.pbk.otus.l161homework;

import org.examples.pbk.otus.l161homework.frontend.FrontendServiceEndpoint;
import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MessageSystemContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppContext {

    private static final Logger logger = Logger.getLogger(AppContext.class.getName());
    private FrontendServiceEndpoint frontendEndpoint;

    public AppContext() {
        init();
    }

    public void init() {
        Address msAddress = null;
        Address dbAddress = null;
        Address frontAddress = null;
        try {
            msAddress = new Address(InetAddress.getByName("127.0.0.1"), 5050);
            dbAddress = new Address(InetAddress.getByName("127.0.0.1"), 5051);
            frontAddress = new Address(InetAddress.getByName("127.0.0.1"), 5052);
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Error while initializing context: " + e.getMessage());
            throw new RuntimeException(e);
        }
        MessageSystemContext context = new MessageSystemContext(msAddress);
        context.setDbAddress(dbAddress);
        context.setFrontendAddress(frontAddress);
        this.frontendEndpoint = new FrontendServiceEndpoint(context);
        frontendEndpoint.init();
        logger.log(Level.INFO, "Context initialized.");
    }

    public void dispose() {
        frontendEndpoint.dispose();
        logger.log(Level.INFO, "Context destroyed.");
    }

    public FrontendServiceEndpoint getFrontendServiceEndpoint() {
        return frontendEndpoint;
    }
}
