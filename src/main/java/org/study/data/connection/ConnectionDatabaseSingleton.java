package org.study.data.connection;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabaseSingleton {

    private static ConnectionDatabaseSingleton singletonConnection = null;
    private final String dataBaseName = "dataBase.db";

    private ConnectionWrapper connection = null;

    private ConnectionDatabaseSingleton() { }

    public static synchronized ConnectionDatabaseSingleton getInstance() {
        if (singletonConnection == null) {

            singletonConnection = new ConnectionDatabaseSingleton();
        }

        return singletonConnection;
    }

    public ConnectionWrapper getConnection() {
        if (connection == null) createConnection();

        return connection;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    private synchronized void createConnection() {
        String dataBaseLocation = "jdbc:sqlite:DataBaseSource";

        try {
            connection = new ConnectionWrapper(DriverManager.getConnection(dataBaseLocation + File.separator + dataBaseName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
