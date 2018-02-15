package org.examples.pbk.otus.l101homework.myorm;

import org.examples.pbk.otus.l101homework.dataset.DataSet;
import org.examples.pbk.otus.l101homework.myorm.executor.BaseExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyOrm {

    private Connection dbConnection;
    private Map<Class<? extends DataSet>, TableInfo> dbTables;

    public MyOrm(OrmConfiguration configuration) {
        this.dbConnection = setupConnection(configuration);
        this.dbTables = getTableInfoForRegisteredClasses(configuration);
        createDbTables();
        registerDropTablesAndCloseConnectionThread();
        //registerCloseConnectionThread();
    }

    public OrmEntityManager getOrmEntityManager() {
        return new OrmEntityManager(dbConnection, dbTables);
    }

    Set<Class<? extends DataSet>> getRegisteredClasses() {
        return dbTables.keySet();
    }

    private Connection setupConnection(OrmConfiguration configuration) {
        try {
            DriverManager.registerDriver(configuration.driver);
            return DriverManager.getConnection(
                    configuration.connectionUrl,
                    configuration.connectionUsername,
                    configuration.connectionPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Class<? extends DataSet>, TableInfo> getTableInfoForRegisteredClasses(OrmConfiguration configuration) {
        Map<Class<? extends DataSet>, TableInfo> dbTables = new HashMap<>();
        configuration.persistenceClasses.forEach(c -> dbTables.put(c, TableInfo.getTableInfo(c)));
        return dbTables;
    }

    private void createDbTables() {
        BaseExecutor executor = new BaseExecutor(dbConnection);
        for (TableInfo tableInfo : dbTables.values()) {
            try {
                executor.execUpdate(tableInfo.getSqlStatements().getCreateTableSQL());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void registerDropTablesAndCloseConnectionThread() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            BaseExecutor executor = new BaseExecutor(dbConnection);
            try {
                for (TableInfo tableInfo : dbTables.values()) {
                    executor.execUpdate(tableInfo.getSqlStatements().getDropTableSQL());
                }
                dbConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private void registerCloseConnectionThread() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
