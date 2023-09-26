package org.study.data.operations.inserting;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.IngredientEntity;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class InsertIngredientWorker {
    private final ConnectionWrapper connection;

    public InsertIngredientWorker(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public int insertIngredient(
            IngredientEntity entity
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {

        String query = "INSERT OR IGNORE INTO Ingredients (name, calories, weight, recommendation)" +
                " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setString(1, entity.getName());
        singlePreparedStatementWrapper.setInt(2, entity.getCalories());
        singlePreparedStatementWrapper.setInt(3, entity.getWeight());
        singlePreparedStatementWrapper.setString(4, entity.getRecommendation());

        return singlePreparedStatementWrapper.executeUpdate();
    }
}
