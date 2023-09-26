package org.study.data.operations.inserting;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.IngredientEntity;
import org.study.data.entity.RecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;
import org.study.data.operations.extraction.IngredientEntityExtractor;
import org.study.data.operations.extraction.RecipeEntityExtractor;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class InsertRecipeWorker {
    private final ConnectionWrapper connection;

    public InsertRecipeWorker(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public int insertRecipe(
            RecipeEntity recipeEntity, List<IngredientEntity> ingredientEntityList
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException, FailedReadException {
        String query = "INSERT OR IGNORE INTO Recipe (name, category, popularity, age_preferences)" +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setString(1, recipeEntity.getName());
        singlePreparedStatementWrapper.setString(2, recipeEntity.getCategory());
        singlePreparedStatementWrapper.setInt(3, recipeEntity.getPopularity());
        singlePreparedStatementWrapper.setInt(4, recipeEntity.getAgePreferences());

        insertListOfIngredients(ingredientEntityList);

        singlePreparedStatementWrapper.executeUpdate();

        createRecordInRelationTable(getRecipeId(recipeEntity), getListOfIngredientsId(ingredientEntityList));

        return 0;
    }

    private int insertListOfIngredients(
            List<IngredientEntity> ingredientEntityList
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {

        InsertIngredientWorker insertIngredientWorker = new InsertIngredientWorker(connection);

        int count = 0;

        for (IngredientEntity entity : ingredientEntityList) {
            if (insertIngredientWorker.insertIngredient(entity) == 1) {
                count++;
            }
        }

        return count;
    }

    private List<Integer> getListOfIngredientsId(
            List<IngredientEntity> ingredients
    ) throws UnexpectedException, FailedStatementException, FailedConnectingException, FailedReadException {

        List<Integer> resultList = new ArrayList<>();

        IngredientEntityExtractor ingredientEntityExtractor = new IngredientEntityExtractor(connection);

        for (IngredientEntity entity : ingredients) {
            resultList.add(ingredientEntityExtractor.extractIngredientFromDatabase(entity.getName()).getId());
        }

        return resultList;
    }

    private int getRecipeId(
            RecipeEntity recipeEntity
    ) throws UnexpectedException, FailedStatementException, FailedConnectingException, FailedReadException {

        RecipeEntityExtractor recipeEntityExtractor = new RecipeEntityExtractor(connection);

        return recipeEntityExtractor.extractRecipeFromDataBase(recipeEntity.getName()).getId();
    }

    private int createRecordInRelationTable(
            int recipeId, List<Integer> listWithRecipesId
    ) throws FailedConnectingException, FailedStatementException, UnexpectedException, FailedExecuteException {

        String query = "INSERT INTO Ingredients_Recipe (id_recipe, id_ingredients) VALUES (?, ?)";
        int count = 0;

        for (int value : listWithRecipesId) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setInt(1, recipeId);
            singlePreparedStatementWrapper.setInt(2, value);

            count += singlePreparedStatementWrapper.executeUpdate();
        }

        return count;
    }
}
