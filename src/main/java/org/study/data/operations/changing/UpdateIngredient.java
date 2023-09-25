package org.study.data.operations.changing;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class UpdateIngredient {
    private final ConnectionWrapper connectionWrapper = ConnectionDatabaseSingleton.getInstance().getConnection();

    public int updateIngredient(String oldName,String newName, String newRecommendation, int newCalories, int newWeight) {
        String query = "UPDATE Ingredients SET name = ?, recommendation = ?, calories = ?, weight = ? WHERE name = ?";

        try {
            PreparedStatement preparedStatement = connectionWrapper.prepareStatement(query);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setString(1, newName);
            singlePreparedStatementWrapper.setString(2, newRecommendation);
            singlePreparedStatementWrapper.setInt(3, newCalories);
            singlePreparedStatementWrapper.setInt(4, newWeight);
            singlePreparedStatementWrapper.setString(5, oldName);

            return singlePreparedStatementWrapper.executeUpdate();
        } catch (UnexpectedException e) {
            throw new RuntimeException(e);
        } catch (FailedExecuteException e) {
            throw new RuntimeException(e);
        } catch (FailedStatementException e) {
            throw new RuntimeException(e);
        } catch (FailedConnectingException e) {
            throw new RuntimeException(e);
        }
    }
}
