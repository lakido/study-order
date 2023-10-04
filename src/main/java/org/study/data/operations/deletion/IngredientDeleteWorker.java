package org.study.data.operations.deletion;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class IngredientDeleteWorker {

    private final ConnectionWrapper connection;

    public IngredientDeleteWorker(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public int deleteIngredientById(
            int id
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {

        String query = "DELETE FROM Ingredients WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);

        int rowsChanged = singlePreparedStatementWrapper.executeUpdate();

        singlePreparedStatementWrapper.closeStatement();

        return rowsChanged;
    }

    public int deleteIngredientByName(
            String name
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {

        String queryForDeletionOfIngredientsWithName = "DELETE FROM Ingredients WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryForDeletionOfIngredientsWithName);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setString(1, name);

        int rowsChange = singlePreparedStatementWrapper.executeUpdate();

        singlePreparedStatementWrapper.closeStatement();

        return rowsChange;
    }
}
