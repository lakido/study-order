package org.study.data.connection;

import org.study.data.exceptions.FailedConnectingException;
import org.study.data.init.CheckDatabase;
import org.study.data.init.CheckFolderForDataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDatabaseSingleton {

    private static ConnectionDatabaseSingleton singletonConnection = null;
    private final String dataBaseName = "dataBase.db";
    private final String dataBaseLocation = "jdbc:sqlite:DataBaseSource";

    private ConnectionWrapper connection = null;

    private ConnectionDatabaseSingleton() { }

    public static synchronized ConnectionDatabaseSingleton getInstance() {
        if (singletonConnection == null) {

            singletonConnection = new ConnectionDatabaseSingleton();
        }

        return singletonConnection;
    }

    public ConnectionWrapper getConnection() throws FailedConnectingException {
        if (connection == null) createConnection();

        return connection;
    }

    private synchronized void createConnection() throws FailedConnectingException {
        init();
        try {
            connection = new ConnectionWrapper(DriverManager.getConnection(dataBaseLocation + File.separator + dataBaseName));
        } catch (SQLException e) {
            throw new FailedConnectingException();
        }
    }

    private void init() throws FailedConnectingException{
        CheckFolderForDataBase.checkFolder();
        CheckDatabase.checkDatabaseStatus();
    }
}
