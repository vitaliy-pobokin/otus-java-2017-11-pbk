package org.examples.pbk.otus.l151homework;

import org.examples.pbk.otus.l151homework.dbService.DbServiceEndpoint;
import org.examples.pbk.otus.l151homework.dbService.SessionFactoryProvider;
import org.examples.pbk.otus.l151homework.frontend.FrontendServiceEndpoint;
import org.examples.pbk.otus.l151homework.messageSystem.Address;
import org.examples.pbk.otus.l151homework.messageSystem.MessageSystem;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class AppContext {

    private MessageSystem messageSystem;
    private MessageSystemContext messageSystemContext;
    private DbServiceEndpoint dbServiceEndpoint;
    private FrontendServiceEndpoint frontendServiceEndpoint;

    @PostConstruct
    public void init() {
        this.messageSystem = new MessageSystem();
        this.messageSystemContext = new MessageSystemContext(messageSystem);
        Address dbAddress = new Address("DbEndpoint");
        Address frontendAddress = new Address("FrontendEndpoint");
        messageSystemContext.setDbAddress(dbAddress);
        messageSystemContext.setFrontendAddress(frontendAddress);
        this.dbServiceEndpoint = new DbServiceEndpoint(messageSystemContext);
        this.frontendServiceEndpoint = new FrontendServiceEndpoint(messageSystemContext);
        SessionFactoryProvider.init();
    }

    @PreDestroy
    public void dispose() {
        SessionFactoryProvider.dispose();
        messageSystem.dispose();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public MessageSystemContext getMessageSystemContext() {
        return messageSystemContext;
    }

    public DbServiceEndpoint getDbServiceEndpoint() {
        return dbServiceEndpoint;
    }

    public FrontendServiceEndpoint getFrontendServiceEndpoint() {
        return frontendServiceEndpoint;
    }
}
