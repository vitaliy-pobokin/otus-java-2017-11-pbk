package org.examples.pbk.otus.l161homework;

import org.examples.pbk.otus.l161homework.dbService.DbServiceEndpoint;
import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MessageSystemContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class AppContext implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(AppContext.class.getName());
    private DbServiceEndpoint dbEndpoint;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Address msAddress = new Address("127.0.0.1", 5050);
        Address dbAddress = new Address("127.0.0.1", 5051);
        Address frontAddress = new Address("127.0.0.1", 5052);
        MessageSystemContext context = new MessageSystemContext(msAddress);
        context.setDbAddress(dbAddress);
        context.setFrontendAddress(frontAddress);
        this.dbEndpoint = new DbServiceEndpoint(context);
        dbEndpoint.init();
        logger.log(Level.INFO, "Context initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dbEndpoint.dispose();
        logger.log(Level.INFO, "Context destroyed.");
    }
}
