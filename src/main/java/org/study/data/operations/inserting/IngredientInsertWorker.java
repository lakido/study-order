package org.study.data.operations.inserting;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.IngredientEntity;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.util.List;

public class IngredientInsertWorker {
    private final ConnectionWrapper connection;

    public IngredientInsertWorker(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public int insertIngredient(
            String name,
            int calories,
            int weight,
            String recommendation
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {

        String query = "INSERT OR IGNORE INTO Ingredients (name, calories, weight, recommendation)" +
                " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setString(1, name);
        singlePreparedStatementWrapper.setInt(2, calories);
        singlePreparedStatementWrapper.setInt(3, weight);
        singlePreparedStatementWrapper.setString(4, recommendation);

        int rowsChanged = singlePreparedStatementWrapper.executeUpdate();

        singlePreparedStatementWrapper.closeStatement();

        return rowsChanged;
    }

    public int insertListOfIngredients(
            List<IngredientEntity> ingredientEntityList
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {

        String query = "INSERT OR IGNORE INTO Ingredients (name, calories, weight, recommendation)" +
                " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        int rowsChanged = 0;

        for (IngredientEntity ingredientEntity : ingredientEntityList) {
            singlePreparedStatementWrapper.setString(1, ingredientEntity.getName());
            singlePreparedStatementWrapper.setInt(2, ingredientEntity.getCalories());
            singlePreparedStatementWrapper.setInt(3, ingredientEntity.getWeight());
            singlePreparedStatementWrapper.setString(4, ingredientEntity.getRecommendation());

            rowsChanged += singlePreparedStatementWrapper.executeUpdate();
        }

        singlePreparedStatementWrapper.closeStatement();

        return rowsChanged;
    }
}
