package org.study.data.exceptions;

import java.sql.SQLException;

public class FailedModifyException extends SQLException {
    public FailedModifyException() {
        super("Modifying Failed");
    }
}
