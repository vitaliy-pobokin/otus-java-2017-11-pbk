package org.examples.pbk.otus.l161homework.chatMessageService;

import org.hibernate.SessionFactory;

import javax.persistence.Persistence;

public class SessionFactoryProvider {

    private static final String PERSISTENCE_UNIT_NAME = "persistence";

    private static SessionFactory sessionFactory;

    public static void init() {
        sessionFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).unwrap(SessionFactory.class);
    }

    public static void dispose() {
        sessionFactory.close();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
