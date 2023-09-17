package org.study.data.exceptions;

import java.sql.SQLException;

public class FailedDeleteException extends SQLException {
    public FailedDeleteException() {
        super("Deletion Failed");
    }
}
