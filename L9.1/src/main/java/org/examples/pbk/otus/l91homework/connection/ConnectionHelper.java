package org.examples.pbk.otus.l91homework.connection;

import oracle.jdbc.driver.OracleDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new OracleDriver());
            return DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl",
                    "SYS AS SYSDBA",
                    "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
