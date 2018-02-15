package org.examples.pbk.otus.l101homework.myorm;

import org.examples.pbk.otus.l101homework.dataset.DataSet;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

public class OrmConfiguration {
    List<Class<? extends DataSet>> persistenceClasses = new ArrayList<>();
    Driver driver;
    String connectionUrl;
    String connectionUsername;
    String connectionPassword;

    public void addPersistenceClass(Class<? extends DataSet> persistenceClass) {
        this.persistenceClasses.add(persistenceClass);
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void setConnectionUsername(String connectionUsername) {
        this.connectionUsername = connectionUsername;
    }

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }
}
