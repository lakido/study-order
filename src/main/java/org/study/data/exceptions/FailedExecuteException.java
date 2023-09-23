package org.study.data.exceptions;

import java.sql.SQLException;

public class FailedExecuteException extends SQLException {
    public FailedExecuteException() {
        super("Failed Execute");
    }
}
