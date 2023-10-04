package org.study.data.operations.changing;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class IngredientUpdateWorker {
    private final ConnectionWrapper connectionWrapper;

    public IngredientUpdateWorker(ConnectionWrapper connectionWrapper) {
        this.connectionWrapper = connectionWrapper;
    }

    public int updateIngredient(
            int id,
            String newName,
            String newRecommendation,
            int newCalories,
            int newWeight
    ) throws FailedStatementException, UnexpectedException, FailedExecuteException, FailedConnectingException {

        String query = "UPDATE Ingredients SET name = ?, recommendation = ?, calories = ?, weight = ? WHERE id = ?";

        PreparedStatement preparedStatement = connectionWrapper.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setString(1, newName);
        singlePreparedStatementWrapper.setString(2, newRecommendation);
        singlePreparedStatementWrapper.setInt(3, newCalories);
        singlePreparedStatementWrapper.setInt(4, newWeight);
        singlePreparedStatementWrapper.setInt(5, id);

        int rowsChanged = singlePreparedStatementWrapper.executeUpdate();

        singlePreparedStatementWrapper.closeStatement();

        return rowsChanged;
    }
}
