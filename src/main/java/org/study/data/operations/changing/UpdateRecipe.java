package org.study.data.operations.changing;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class UpdateRecipe {
    private final ConnectionWrapper connectionWrapper = ConnectionDatabaseSingleton.getInstance().getConnection();

    public int updateRecipe(String oldName,String newName, String newCategory, int newPopularity, int newAgePreferences) {
        String query = "UPDATE Recipe SET name = ?, category = ?, popularity = ?, age_preferences = ? WHERE name = ?";

        try {
            PreparedStatement preparedStatement = connectionWrapper.prepareStatement(query);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setString(1, newName);
            singlePreparedStatementWrapper.setString(2, newCategory);
            singlePreparedStatementWrapper.setInt(3, newPopularity);
            singlePreparedStatementWrapper.setInt(4, newAgePreferences);
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
