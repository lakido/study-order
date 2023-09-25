package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.IngredientEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class IngredientEntityExtractor {
    private final ConnectionWrapper connection = ConnectionDatabaseSingleton.getInstance().getConnection();

    public IngredientEntity extractIngredientFromDatabase(int id) throws UnexpectedException, FailedStatementException, FailedConnectingException {
        if (id == 0) id = 1;

        String query = "SELECT * FROM Ingredients WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setInt(1, id);

            return singlePreparedStatementWrapper.executeQueryToGetIngredientsEntity();
        } catch (FailedConnectingException exception) {
            throw exception;
        } catch (FailedStatementException exception) {
            throw exception;
        } catch (UnexpectedException exception) {
            throw exception;
        }
    }

    public IngredientEntity extractIngredientFromDatabase(String name) {
        if (name == null || name.equals("")) throw new IllegalArgumentException("Empty String is invalid!");

        String query = "SELECT * FROM Ingredients WHERE name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setString(1, name);

            return singlePreparedStatementWrapper.executeQueryToGetIngredientsEntity();
        } catch (FailedConnectingException exception) {
            throw new RuntimeException(exception);
        } catch (UnexpectedException exception) {
            throw new RuntimeException(exception);
        } catch (FailedStatementException exception) {
            throw new RuntimeException(exception);
        }
    }
}
