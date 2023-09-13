package org.study.data.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabaseSingleton {
    private static ConnectionDatabaseSingleton singletonConnection = null;
    private Connection connection = null;

    private ConnectionDatabaseSingleton() {
        try {
            String url = "jdbc:sqlite:DataBaseSource" + File.separator + "dataBase.db";

            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionDatabaseSingleton getInstance() {
        if (singletonConnection == null) {
            singletonConnection = new ConnectionDatabaseSingleton();
        }

        return singletonConnection;
    }

    public synchronized Connection getConnection() {
        return connection;
    }
}
