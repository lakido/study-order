package org.study.data.operations.inserting;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;
import org.study.data.operations.extraction.RecipeEntityExtractor;

import java.sql.PreparedStatement;

public class RecipeInsertWorker {
    private static ConnectionWrapper connection;
    private static RecipeInsertWorker recipeInsertWorkerSingleton;

    private RecipeInsertWorker() {}

    public static RecipeInsertWorker getInstance(ConnectionWrapper connectionWrapper) {
        if (recipeInsertWorkerSingleton == null) {
            recipeInsertWorkerSingleton = new RecipeInsertWorker();
            connection = connectionWrapper;
        }

        return recipeInsertWorkerSingleton;
    }

    public int insertRecipe(
            String name,
            String category,
            int popularity,
            int agePreferences
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        String query = "INSERT OR IGNORE INTO Recipe (name, category, popularity, age_preferences)" +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setString(1, name);
        singlePreparedStatementWrapper.setString(2, category);
        singlePreparedStatementWrapper.setInt(3, popularity);
        singlePreparedStatementWrapper.setInt(4, agePreferences);

        int rowsChanged = singlePreparedStatementWrapper.executeUpdate();

        singlePreparedStatementWrapper.closeStatement();

        return rowsChanged;
    }

    private int getRecipeId(
            String name
    ) throws UnexpectedException, FailedStatementException, FailedConnectingException, FailedReadException {

        RecipeEntityExtractor recipeEntityExtractor = RecipeEntityExtractor.getInstance(connection);

        return recipeEntityExtractor.extractRecipeFromDatabaseByName(name).getId();
    }
}
