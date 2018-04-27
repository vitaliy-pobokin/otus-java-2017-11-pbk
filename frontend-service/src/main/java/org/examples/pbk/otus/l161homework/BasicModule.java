package org.examples.pbk.otus.l161homework;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import org.examples.pbk.otus.l161homework.app.FrontendService;
import org.examples.pbk.otus.l161homework.frontend.*;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AppContext.class)
                .toInstance(new AppContext());
    }

    /*@Provides
    @Inject
    WsService provideWsService(AppContext context) {
        return context.getFrontendService();
    }

    @Provides
    @Inject
    FrontendService provideFrontendService(AppContext context) {
        return context.getFrontendService();
    }*/
}
