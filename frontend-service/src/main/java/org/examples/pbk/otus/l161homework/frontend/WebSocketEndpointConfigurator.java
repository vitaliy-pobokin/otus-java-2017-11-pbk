package org.examples.pbk.otus.l161homework.frontend;

import org.examples.pbk.otus.l161homework.ServletContextListener;

import javax.websocket.server.ServerEndpointConfig;

public class WebSocketEndpointConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public <T> T getEndpointInstance(Class<T> clazz)
            throws InstantiationException {
        return ServletContextListener.injector.getInstance(clazz);
    }
}
