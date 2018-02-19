package org.examples.pbk.otus.l121homework;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.examples.pbk.otus.l121homework.cache.CacheEngineImpl;
import org.examples.pbk.otus.l121homework.dataset.UsersDataSet;
import org.examples.pbk.otus.l121homework.servlet.AdminServlet;
import org.examples.pbk.otus.l121homework.servlet.LoginServlet;

public class Main {
    public static void main(String[] args) throws Exception {

        CacheEngineImpl<Long, UsersDataSet> cacheEngine = new CacheEngineImpl<>(20000, 10000);
        new RandomDbTask(cacheEngine).start(1000, 1000);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("static");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(new ServletHolder(new AdminServlet(cacheEngine)), "/admin");

        Server server = new Server(3000);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
