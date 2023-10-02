package org.study.data.operations.deletion;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IngredientDeleteWorker {

    private final ConnectionWrapper connection;

    public IngredientDeleteWorker(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public int deleteIngredient(
            int id
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {

        String query = "DELETE FROM Ingredients WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);

        return singlePreparedStatementWrapper.executeUpdate();
    }

    public int deleteIngredient(
            String name
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {

        String queryForDeletionOfIngredientsWithName = "DELETE FROM Ingredients WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryForDeletionOfIngredientsWithName);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setString(1, name);

        return singlePreparedStatementWrapper.executeUpdate();
    }
}