package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.RecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class RecipeEntityExtractor {
    private final ConnectionWrapper connection = ConnectionDatabaseSingleton.getInstance().getConnection();

    public RecipeEntity extractRecipeFromDataBase(int id) {
        if (id == 0) id = 1;

        String query = "SELECT * FROM Recipe WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setInt(1, id);

            return singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();
        } catch (FailedConnectingException | UnexpectedException | FailedStatementException e) {
            throw new RuntimeException(e);
        }
    }

    public RecipeEntity extractRecipeFromDataBase(String name) {
        if (name == null || name.equals("")) throw new IllegalArgumentException("Empty String is invalid!");

        String query = "SELECT * FROM Recipe WHERE name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setString(1, name);

            return singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();
        } catch (FailedStatementException | FailedConnectingException | UnexpectedException e) {
            throw new RuntimeException(e);
        }
    }
}
