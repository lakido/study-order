package org.study.data.exceptions;

import java.sql.SQLException;

public class FailedReadException extends SQLException {
    public FailedReadException() {
        super("Reading Failed");
    }
}
