package org.study.data.exceptions;

import java.sql.SQLException;

public class FailedStatementException extends SQLException {
    public FailedStatementException() {
        super("Statement Failed");
    }
}
