package org.study.data.operations.deletion;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class RecipeDeleteWorker {

    private static ConnectionWrapper connection;
    private static RecipeDeleteWorker recipeDeleteWorkerSingleton;

    private RecipeDeleteWorker() {}

    public static RecipeDeleteWorker getInstance(ConnectionWrapper connectionWrapper) {
        if (recipeDeleteWorkerSingleton == null) {
            recipeDeleteWorkerSingleton = new RecipeDeleteWorker();
            connection = connectionWrapper;
        }

        return recipeDeleteWorkerSingleton;
    }

    public int deleteRecipeById(
            int id
    ) throws UnexpectedException, FailedExecuteException, FailedConnectingException, FailedStatementException {

        String queryForRecipeDeletion = "DELETE FROM Recipe WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryForRecipeDeletion);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);

        return singlePreparedStatementWrapper.executeUpdate();
    }

    public int deleteRecipeByName(
            String name
    ) throws UnexpectedException, FailedStatementException, FailedExecuteException, FailedConnectingException {

        String queryForRecipeDeletion = "DELETE FROM Recipe WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryForRecipeDeletion);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setString(1, name);

        return singlePreparedStatementWrapper.executeUpdate();
    }
}
