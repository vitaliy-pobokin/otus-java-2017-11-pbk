package org.examples.pbk.otus.l161homework;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class ServletContextListener extends GuiceServletContextListener {

    public static Injector injector;

    @Override
    protected Injector getInjector() {
        injector = Guice.createInjector(new BasicModule());
        return injector;
    }
}
