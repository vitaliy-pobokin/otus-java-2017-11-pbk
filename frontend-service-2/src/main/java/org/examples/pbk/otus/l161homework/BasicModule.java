package org.examples.pbk.otus.l161homework;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AppContext.class)
                .toInstance(new AppContext());
    }
}
