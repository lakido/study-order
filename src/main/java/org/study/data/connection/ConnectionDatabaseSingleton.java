package org.study.data.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabaseSingleton {

    private static ConnectionDatabaseSingleton singletonConnection = null;
    private final String dataBaseUrl = "jdbc:sqlite:DataBaseSource" + File.separator + "dataBase.db";

    private Connection connection = null;

    //TODO extract database source (url) into final fields
    //TODO extract the content of constructor into getInstance method ?????
    private ConnectionDatabaseSingleton() { }

    public static synchronized ConnectionDatabaseSingleton getInstance() {
        if (singletonConnection == null) {


            singletonConnection = new ConnectionDatabaseSingleton();
        }

        return singletonConnection;
    }

    //remove synchronized modifier
    public Connection getConnection() {
        if (connection == null) createConnection();

        return connection;
    }

    public synchronized String getDataBaseUrl() {return dataBaseUrl;}

    private synchronized void createConnection() {
        try {

            connection = DriverManager.getConnection(dataBaseUrl);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
