package org.examples.pbk.otus.l161homework;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.examples.pbk.otus.l161homework.frontend.WebSocketMessageEndpoint;

import java.net.URL;
import java.util.Objects;

public class FrontendServiceRunner {
    public static void main(String[] args) throws Exception {
        Server server = new Server(3001);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addEventListener(new ServletContextListener());
        server.setHandler(context);

        ServerContainer wsContainer = WebSocketServerContainerInitializer.configureContext(context);
        wsContainer.addEndpoint(WebSocketMessageEndpoint.class);

        URL urlStatics = Thread.currentThread().getContextClassLoader().getResource("static/index.html");
        Objects.requireNonNull(urlStatics,"Unable to find index.html in classpath");
        String urlBase = urlStatics.toExternalForm().replaceFirst("/[^/]*$","/");
        ServletHolder defHolder = new ServletHolder("default",new DefaultServlet());
        defHolder.setInitParameter("resourceBase",urlBase);
        defHolder.setInitParameter("dirAllowed","false");
        context.addServlet(defHolder,"/");

        server.start();
        server.join();
    }
}
