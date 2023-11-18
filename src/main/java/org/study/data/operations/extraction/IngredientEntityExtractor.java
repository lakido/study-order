package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entities.IngredientEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientEntityExtractor {
    private static ConnectionWrapper connection;
    private static IngredientEntityExtractor ingredientEntityExtractorSingleton;

    private IngredientEntityExtractor() {}

    public static IngredientEntityExtractor getInstance(ConnectionWrapper connectionWrapper) {
        if (ingredientEntityExtractorSingleton == null) {
            ingredientEntityExtractorSingleton = new IngredientEntityExtractor();
            connection = connectionWrapper;
        }

        return ingredientEntityExtractorSingleton;
    }

    public IngredientEntity extractIngredientFromDatabaseById(
            int id
    ) throws UnexpectedException, FailedStatementException, FailedConnectingException, FailedReadException {

        String query = "SELECT * FROM Ingredients WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetIngredientsEntity();

        IngredientEntity ingredientEntity = createIngredientEntity(resultSet);

        singlePreparedStatementWrapper.closeStatement();

        return ingredientEntity;
    }

    public IngredientEntity extractIngredientFromDatabaseByName(
            String name
    ) throws FailedStatementException, FailedConnectingException, UnexpectedException, FailedReadException {
        String query = "SELECT * FROM Ingredients WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setString(1, name);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetIngredientsEntity();

        IngredientEntity ingredientEntity = createIngredientEntity(resultSet);

        singlePreparedStatementWrapper.closeStatement();

        return ingredientEntity;
    }

    public List<IngredientEntity> extractIngredientListOfFirstRecords(
            int limit
    ) throws FailedConnectingException, FailedStatementException, UnexpectedException, FailedReadException {
        String query = "SELECT * FROM Ingredients LIMIT ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, limit);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetIngredientsEntity();

        return new ArrayList<>(getIngredientEntityList(resultSet));
    }

    public List<IngredientEntity> extractIngredientListByRecipeId(
            int recipeId
    ) throws FailedConnectingException, FailedStatementException, UnexpectedException, FailedReadException {
        String query = """
                SELECT Ingredients.*
                FROM Ingredients\s
                RIGHT JOIN Ingredients_Recipe ON Ingredients.id = Ingredients_Recipe.id_ingredients
                WHERE Ingredients_Recipe.id_recipe = (?)""";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, recipeId);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetIngredientsEntity();

        return new ArrayList<>(getIngredientEntityList(resultSet));
    }

    public List<IngredientEntity> extractIngredientListByRecipeName(
            String recipeName
    ) throws FailedConnectingException, FailedStatementException, UnexpectedException, FailedReadException {
        String query = """
                SELECT Ingredients.*
                FROM Ingredients\s
                RIGHT JOIN Ingredients_Recipe ON Ingredients.id = Ingredients_Recipe.id_ingredients
                WHERE Ingredients_Recipe.id_recipe = (SELECT id FROM Recipe WHERE Recipe.name = "?")""";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setString(1, recipeName);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetIngredientsEntity();

        return new ArrayList<>(getIngredientEntityList(resultSet));
    }

    public int extractNextAvailableIdForIngredient() throws FailedConnectingException, FailedExecuteException, UnexpectedException {
        String query = "SELECT MAX(id) from Ingredients;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQuery();

        int result;

        try {
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new UnexpectedException();
        }

        return result + 1;
    }

    private IngredientEntity createIngredientEntity(ResultSet resultSet) throws UnexpectedException {
        IngredientEntity ingredientEntity;

        try {
            ingredientEntity = IngredientEntity.getIngredientEntity(resultSet);
            return ingredientEntity;

        } catch (SQLException exception) {
            throw new UnexpectedException();
        }
    }

    private List<IngredientEntity> getIngredientEntityList(ResultSet resultSet) throws UnexpectedException {
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                ingredientEntityList.add(IngredientEntity.getIngredientEntity(resultSet));
            }
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }

        return ingredientEntityList;
    }
}
