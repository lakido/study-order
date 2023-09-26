package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.entity.RecipeEntity;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedReadException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class RecipeEntityExtractor {
    private final ConnectionWrapper connection;

    public RecipeEntityExtractor(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public RecipeEntity extractRecipeFromDataBase(
            int id
    ) throws FailedConnectingException, FailedStatementException, UnexpectedException, FailedReadException {

        String query = "SELECT * FROM Recipe WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);

        return singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();
    }

    public RecipeEntity extractRecipeFromDataBase(
            String name
    ) throws FailedConnectingException, UnexpectedException, FailedStatementException, FailedReadException {

        String query = "SELECT * FROM Recipe WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setString(1, name);

        return singlePreparedStatementWrapper.executeQueryToGetRecipeEntity();
    }
}
