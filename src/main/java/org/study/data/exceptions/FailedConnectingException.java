package org.study.data.exceptions;

import java.sql.SQLException;

public class FailedConnectingException extends SQLException {
    public FailedConnectingException() {
        super("Connection Failed");
    }
}
