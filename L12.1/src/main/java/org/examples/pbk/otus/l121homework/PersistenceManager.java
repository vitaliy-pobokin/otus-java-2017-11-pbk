package org.examples.pbk.otus.l121homework;

import org.examples.pbk.otus.l121homework.dataset.AccountDataSet;
import org.examples.pbk.otus.l121homework.dataset.UsersDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class PersistenceManager {
    private static PersistenceManager instance;

    private SessionFactory sessionFactory;

    public static synchronized PersistenceManager getInstance() {
        if (instance == null) {
            instance = new PersistenceManager();
        }
        return instance;
    }

    private PersistenceManager() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UsersDataSet.class);
        configuration.addAnnotatedClass(AccountDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        configuration.setProperty("hibernate.connection.driver_class", "oracle.jdbc.OracleDriver");
        configuration.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@127.0.0.1:1521:XE");
        configuration.setProperty("hibernate.connection.username", "SYS AS SYSDBA");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void close() {
        sessionFactory.close();
    }
}
