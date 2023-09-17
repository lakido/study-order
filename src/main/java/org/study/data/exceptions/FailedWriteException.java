package org.study.data.exceptions;

import java.sql.SQLException;

public class FailedWriteException extends SQLException {
    public FailedWriteException() {
        super("Writing Failed");
    }
}
