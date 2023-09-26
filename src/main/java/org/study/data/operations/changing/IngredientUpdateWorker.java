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
            String oldName,
            String newName,
            String newRecommendation,
            int newCalories,
            int newWeight
    ) throws FailedStatementException, UnexpectedException, FailedExecuteException, FailedConnectingException {

        String query = "UPDATE Ingredients SET name = ?, recommendation = ?, calories = ?, weight = ? WHERE name = ?";

        PreparedStatement preparedStatement = connectionWrapper.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setString(1, newName);
        singlePreparedStatementWrapper.setString(2, newRecommendation);
        singlePreparedStatementWrapper.setInt(3, newCalories);
        singlePreparedStatementWrapper.setInt(4, newWeight);
        singlePreparedStatementWrapper.setString(5, oldName);

        return singlePreparedStatementWrapper.executeUpdate();
    }
}
