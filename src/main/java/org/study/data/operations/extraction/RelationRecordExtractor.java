package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.RelationIngredientRecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO this class returns only one row of database. but what should i do, if i have multiply rows with the same values in id_recipe and id_ingredient
//TODO need to add two methods to get entity of relation record. one method should accept id_recipe, the other should accept id_ingredient
public class RelationRecordExtractor {
    private final ConnectionWrapper connection;

    public RelationRecordExtractor(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public RelationIngredientRecipeEntity extractRelationRecipeIngredientRecord(
            int idRecipe,
            int idIngredient
    ) throws UnexpectedException, FailedConnectingException, FailedStatementException, FailedReadException {
        RelationIngredientRecipeEntity relationIngredientRecipeEntity;

        String query = "SELECT * FROM Ingredients_Recipe WHERE id_recipe = ? AND id_ingredients = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, idRecipe);
        singlePreparedStatementWrapper.setInt(2, idIngredient);

        ResultSet resultSet = singlePreparedStatementWrapper.executeQueryToGetRelationIngredientRecipeEntity();

        relationIngredientRecipeEntity = extractRelationIngredientRecipeEntity(resultSet);

        singlePreparedStatementWrapper.closeStatement();

        return relationIngredientRecipeEntity;
    }

    public List<RelationIngredientRecipeEntity> extractRelationRecipeIngredientInRecordsByRecipeIdInList(
            int idRecipe
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        List<RelationIngredientRecipeEntity> relationIngredientRecipeEntityList = new ArrayList<>();

        String query = "SELECT * FROM Ingredients_Recipe WHERE id_recipe = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, idRecipe);

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

    public ResultSet extractRelationRecipeIngredientRecordsInResultSetByIngredientId(
            int idIngredient
    ) throws FailedStatementException, FailedExecuteException, FailedConnectingException {
        String query = "SELECT * FROM Ingredients_Recipe WHERE id_ingredients = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, idIngredient);

        return singlePreparedStatementWrapper.executeQuery();
    }

    private RelationIngredientRecipeEntity extractRelationIngredientRecipeEntity(ResultSet resultSet) throws UnexpectedException {
        RelationIngredientRecipeEntity relationIngredientRecipeEntity = null;

        try {
            relationIngredientRecipeEntity = RelationIngredientRecipeEntity.getRelationIngredientRecipeEntity(resultSet);
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }
        return relationIngredientRecipeEntity;
    }
}
