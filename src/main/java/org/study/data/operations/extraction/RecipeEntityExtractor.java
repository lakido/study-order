package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entities.IngredientEntity;
import org.study.data.entities.RecipeEntity;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedReadException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeEntityExtractor {
    private static ConnectionWrapper connection;
    private static RecipeEntityExtractor recipeEntityExtractorSingleton;

    private RecipeEntityExtractor() {}

    public static RecipeEntityExtractor getInstance(ConnectionWrapper connectionWrapper) {
        if (recipeEntityExtractorSingleton == null) {
            recipeEntityExtractorSingleton = new RecipeEntityExtractor();
            connection = connectionWrapper;
        }

        return recipeEntityExtractorSingleton;
    }

    public RecipeEntity extractRecipeFromDatabaseById(
            int id
    ) throws FailedConnectingException, FailedStatementException, UnexpectedException, FailedReadException {

        String query = "SELECT * FROM Recipe WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();
        RecipeEntity recipeEntity = createEntityFromResultSet(resultSet);

        singlePreparedStatementWrapper.closeStatement();

        return recipeEntity;
    }

    public RecipeEntity extractRecipeFromDatabaseByName(
            String name
    ) throws FailedConnectingException, UnexpectedException, FailedStatementException, FailedReadException {
        String query = "SELECT * FROM Recipe WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setString(1, name);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();
        RecipeEntity recipeEntity = createEntityFromResultSet(resultSet);

        singlePreparedStatementWrapper.closeStatement();

        return recipeEntity;
    }

    public List<RecipeEntity> extractRecipeListOfFirstRecords(
            int limit
    ) throws FailedConnectingException, FailedStatementException, UnexpectedException, FailedReadException {
        String query = "SELECT * FROM Recipe LIMIT ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, limit);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();

        return new ArrayList<>(getRecipeEntityList(resultSet));
    }

    private RecipeEntity createEntityFromResultSet(ResultSet resultSet) throws UnexpectedException {
        RecipeEntity recipeEntity;

        try {
            recipeEntity = RecipeEntity.getRecipeEntity(resultSet);
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }
        return recipeEntity;
    }

    private List<RecipeEntity> getRecipeEntityList(ResultSet resultSet) throws UnexpectedException {
        List<RecipeEntity> recipeEntityList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                recipeEntityList.add(RecipeEntity.getRecipeEntity(resultSet));
            }
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }

        return recipeEntityList;
    }
}
