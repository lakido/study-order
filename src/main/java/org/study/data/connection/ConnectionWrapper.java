package org.study.data.connection;

import org.study.data.exceptions.FailedConnectingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionWrapper {

    private final Connection connection;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement prepareStatement(String sql) throws FailedConnectingException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new FailedConnectingException();
        }
    }
}
