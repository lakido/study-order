package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.RecipeEntity;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedReadException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeEntityExtractor {
    private final ConnectionWrapper connection;

    public RecipeEntityExtractor(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public RecipeEntity extractRecipeFromDatabase(
            int id
    ) throws FailedConnectingException, FailedStatementException, UnexpectedException, FailedReadException {
        RecipeEntity recipeEntity = null;

        String query = "SELECT * FROM Recipe WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();
        recipeEntity = createEntityFromResultSet(resultSet);

        singlePreparedStatementWrapper.closeStatement();

        return recipeEntity;
    }

    public RecipeEntity extractRecipeFromDatabase(
            String name
    ) throws FailedConnectingException, UnexpectedException, FailedStatementException, FailedReadException {
        RecipeEntity recipeEntity = null;

        String query = "SELECT * FROM Recipe WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setString(1, name);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();
        recipeEntity = createEntityFromResultSet(resultSet);

        singlePreparedStatementWrapper.closeStatement();

        return recipeEntity;
    }

    private RecipeEntity createEntityFromResultSet(ResultSet resultSet) throws UnexpectedException {
        RecipeEntity recipeEntity = null;

        try {
            recipeEntity = RecipeEntity.getRecipeEntity(resultSet);
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }
        return recipeEntity;
    }
}
