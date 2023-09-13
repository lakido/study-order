package org.study.data.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabaseBuilder {
    private static final String url = "jdbc:sqlite:DataBaseSource" + File.separator + "dataBase.db";
    private final Connection connection;
    private ConnectionDatabaseBuilder() {
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ConnectionDatabaseBuilder builder() {
        return new ConnectionDatabaseBuilder();
    }

    public Connection getConnection() {
        return connection;
    }

    //why this thing doesn't work?
    static class Builder {
        public static ConnectionDatabaseBuilder build() {
            return new ConnectionDatabaseBuilder();
        }
    }

}
