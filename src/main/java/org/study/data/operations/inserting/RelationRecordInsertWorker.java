package org.study.data.operations.inserting;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entities.RelationIngredientRecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationRecordInsertWorker {

    private static ConnectionWrapper connection;
    private static RelationRecordInsertWorker relationRecordInsertWorkerSingleton;

    private RelationRecordInsertWorker() {}

    public static RelationRecordInsertWorker getInstance(ConnectionWrapper connectionWrapper) {
        if (relationRecordInsertWorkerSingleton == null) {
            relationRecordInsertWorkerSingleton = new RelationRecordInsertWorker();
            connection = connectionWrapper;
        }

        return relationRecordInsertWorkerSingleton;
    }

    public int insertRelationRecipeIngredientRecord(
            int idRecipe,
            int idIngredient
    ) throws UnexpectedException, FailedConnectingException, FailedStatementException, FailedExecuteException {
        if (checkValuesForUniqueness(idRecipe, idIngredient) > 0) return 0;

        String query = "INSERT INTO Ingredients_Recipe (id_recipe, id_ingredients) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setInt(1, idRecipe);
        singlePreparedStatementWrapper.setInt(2, idIngredient);

        int rowsChanged = singlePreparedStatementWrapper.executeUpdate();

        singlePreparedStatementWrapper.closeStatement();

        return rowsChanged;
    }

    private int checkValuesForUniqueness(
            int recipeId,
            int ingredientId
    ) throws FailedConnectingException, FailedStatementException, FailedExecuteException, UnexpectedException {
        String query = "SELECT * FROM Ingredients_Recipe WHERE id_recipe = ? AND id_ingredients = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setInt(1, recipeId);
        singlePreparedStatementWrapper.setInt(2, ingredientId);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQuery();

        List<RelationIngredientRecipeEntity> ingredientRecipeEntityList = new ArrayList<>();

        try {
            if (resultSet.next()) {
                ingredientRecipeEntityList.add(RelationIngredientRecipeEntity.getRelationIngredientRecipeEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_recipe"),
                        resultSet.getInt("id_ingredients"))
                );
            }
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }

        singlePreparedStatementWrapper.closeStatement();

        return ingredientRecipeEntityList.size();
    }
}
