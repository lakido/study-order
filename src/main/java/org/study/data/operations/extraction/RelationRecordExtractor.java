package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entities.RelationIngredientRecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationRecordExtractor {
    private static ConnectionWrapper connection;
    private static RelationRecordExtractor relationRecordExtractorSingleton;

    public RelationRecordExtractor() {}

    public static RelationRecordExtractor getInstance(ConnectionWrapper connectionWrapper) {
        if (relationRecordExtractorSingleton == null) {
            relationRecordExtractorSingleton = new RelationRecordExtractor();
            connection = connectionWrapper;
        }

        return relationRecordExtractorSingleton;
    }

    public RelationIngredientRecipeEntity extractRelationByIngredientIdAndRecipeId(
            int recipeId,
            int ingredientId
    ) throws UnexpectedException, FailedConnectingException, FailedStatementException, FailedReadException {

        String query = "SELECT * FROM Ingredients_Recipe WHERE id_recipe = ? AND id_ingredients = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, recipeId);
        singlePreparedStatementWrapper.setInt(2, ingredientId);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetRelationIngredientRecipeEntity();

        RelationIngredientRecipeEntity relationIngredientRecipeEntity = extractRelationIngredientRecipeEntity(resultSet);

        singlePreparedStatementWrapper.closeStatement();

        return relationIngredientRecipeEntity;
    }

    public List<RelationIngredientRecipeEntity> extractRelationToListByRecipe(
            int recipeId
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        List<RelationIngredientRecipeEntity> relationIngredientRecipeEntityList = new ArrayList<>();

        String query = "SELECT * FROM Ingredients_Recipe WHERE id_recipe = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, recipeId);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQuery();

        try {
            while (resultSet.next()) {
                relationIngredientRecipeEntityList.add(new RelationIngredientRecipeEntity(
                        resultSet.getInt("id_recipe"),
                        resultSet.getInt("id_ingredients")
                ));
            }
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }

        singlePreparedStatementWrapper.closeStatement();

        return relationIngredientRecipeEntityList;
    }

    public List<RelationIngredientRecipeEntity> extractRelationToListByIngredient(
            int ingredientId
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        List<RelationIngredientRecipeEntity> relationIngredientRecipeEntityList = new ArrayList<>();

        String query = "SELECT * FROM Ingredients_Recipe WHERE id_ingredients = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, ingredientId);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQuery();

        try {
            while (resultSet.next()) {
                relationIngredientRecipeEntityList.add(new RelationIngredientRecipeEntity(
                        resultSet.getInt("id_recipe"),
                        resultSet.getInt("id_ingredients")
                ));
            }
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }

        singlePreparedStatementWrapper.closeStatement();

        return relationIngredientRecipeEntityList;
    }

    private RelationIngredientRecipeEntity extractRelationIngredientRecipeEntity(ResultSet resultSet) throws UnexpectedException {
        RelationIngredientRecipeEntity relationIngredientRecipeEntity;

        try {
            relationIngredientRecipeEntity = RelationIngredientRecipeEntity.getRelationIngredientRecipeEntity(resultSet);
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }
        return relationIngredientRecipeEntity;
    }
}
