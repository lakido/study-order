package org.study.data.operations.changing;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class RecipeUpdateWorker {
    private final ConnectionWrapper connectionWrapper;

    public RecipeUpdateWorker(ConnectionWrapper connectionWrapper) {
        this.connectionWrapper = connectionWrapper;
    }

    public int updateRecipe(
            int id,
            String newName,
            String newCategory,
            int newPopularity,
            int newAgePreferences
    ) throws FailedStatementException, FailedConnectingException, UnexpectedException, FailedExecuteException {

        String query = "UPDATE Recipe SET name = ?, category = ?, popularity = ?, age_preferences = ? WHERE id = ?";
        PreparedStatement preparedStatement = connectionWrapper.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setString(1, newName);
        singlePreparedStatementWrapper.setString(2, newCategory);
        singlePreparedStatementWrapper.setInt(3, newPopularity);
        singlePreparedStatementWrapper.setInt(4, newAgePreferences);
        singlePreparedStatementWrapper.setInt(5, id);

        int rowsChanged = singlePreparedStatementWrapper.executeUpdate();

        singlePreparedStatementWrapper.closeStatement();

        return rowsChanged;
    }
}
