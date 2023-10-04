package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.IngredientEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientEntityExtractor {
    private final ConnectionWrapper connection;

    public IngredientEntityExtractor(ConnectionWrapper connection) {
        this.connection = connection;
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

    private IngredientEntity createIngredientEntity(ResultSet resultSet) throws UnexpectedException {
        IngredientEntity ingredientEntity = null;

        try {
            ingredientEntity = IngredientEntity.getIngredientEntity(resultSet);
            return ingredientEntity;

        } catch (SQLException exception) {
            throw new UnexpectedException();
        }
    }
}
